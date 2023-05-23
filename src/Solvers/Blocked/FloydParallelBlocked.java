package Solvers.Blocked;

import Core.FloydResult;
import Core.Graph;
import Core.IFloydSolver;
import Core.NegativeCycleChecker;

import java.util.ArrayList;
import java.util.concurrent.*;

public class FloydParallelBlocked implements IFloydSolver {
    int countThreads, blockSize;

    public FloydParallelBlocked(int countThreads, int blockSize) {
        this.countThreads = countThreads;
        this.blockSize = blockSize;
    }

    public FloydResult getMinDistances(Graph graph) {
        int countVertex = graph.getCountVertex();

        int[][] dist = new int[countVertex][countVertex];
        int[][] prev = new int[countVertex][countVertex];

        for (int i = 0; i < countVertex; i++) {
            for (int j = 0; j < countVertex; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                    prev[i][j] = Integer.MAX_VALUE;
                } else {
                    int weight = graph.getWeight(i, j);
                    dist[i][j] = graph.getWeight(i, j);

                    if (weight != Integer.MAX_VALUE)
                        prev[i][j] = i;
                    else
                        prev[i][j] = -1;
                }

            }
        }

        int countBlocks = (int) Math.ceil((double) countVertex / blockSize);
        int[][][][] distBlocks = splitMatrixToBlock(dist, blockSize, countBlocks);
        int[][][][] prevBlocks = splitMatrixToBlock(prev, blockSize, countBlocks);

        ExecutorService threadPool = Executors.newFixedThreadPool(countThreads);

        ArrayList<Future<Object>> futures = new ArrayList<>();
        Future<Object> dependentTask;


        for (int k = 0; k < countBlocks; k++) {
            try {
                // Dependent phase
                dependentTask = threadPool.submit(new FloydParallelBlockedTask(distBlocks[k][k], distBlocks[k][k], distBlocks[k][k], prevBlocks[k][k], prevBlocks[k][k]));

                dependentTask.get();

                // Partially dependent phase
                for (int j = 0; j < countBlocks; j++) {
                    if (j != k) {
                        futures.add(threadPool.submit(new FloydParallelBlockedTask(distBlocks[k][j], distBlocks[k][k], distBlocks[k][j], prevBlocks[k][j], prevBlocks[k][j])));
                        futures.add(threadPool.submit(new FloydParallelBlockedTask(distBlocks[j][k], distBlocks[j][k], distBlocks[k][k], prevBlocks[j][k], prevBlocks[k][k])));
                    }
                }

                for (Future<Object> future : futures) {
                    future.get();
                }

                futures.clear();

                // Independent phase
                for (int i = 0; i < countBlocks; i++) {
                    if (i != k) {
                        for (int j = 0; j < countBlocks; j++) {
                            if (j != k) {
                                futures.add(threadPool.submit(new FloydParallelBlockedTask(distBlocks[i][j], distBlocks[i][k], distBlocks[k][j], prevBlocks[i][j], prevBlocks[k][j])));
                            }
                        }
                    }
                }

                for (Future<Object> future : futures) {
                    future.get();
                }

                futures.clear();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        threadPool.shutdown();
        dist = unionBlocks(distBlocks, countVertex);
        prev = unionBlocks(prevBlocks, countVertex);

        return new FloydResult(dist, prev, NegativeCycleChecker.isNegativeCycle(dist));
    }

    int[][][][] splitMatrixToBlock(int[][] distMatrix, int blockSize, int countBlocks) {
        int[][][][] blocks = new int[countBlocks][countBlocks][blockSize][blockSize];
        int countVertex = distMatrix.length;

        for (int i = 0; i < countBlocks; i++) {
            for (int j = 0; j < countBlocks; j++) {
                int[][] matrixValue = new int[blockSize][blockSize];
                for (int x = 0; x < blockSize; x++) {
                    for (int y = 0; y < blockSize; y++) {
                        int rowIndex = i * blockSize + x;
                        int columnIndex = j * blockSize + y;
                        boolean isOutOfRange = rowIndex >= countVertex || columnIndex >= countVertex;
                        matrixValue[x][y] = isOutOfRange ? Integer.MAX_VALUE : distMatrix[i * blockSize + x][j * blockSize + y];
                    }
                }
                blocks[i][j] = matrixValue;
            }
        }

        return blocks;
    }

    private int[][] unionBlocks(int[][][][] blocks, int countVertex) {
        int blockSize = blocks[0][0].length;
        int countBlocks = blocks.length;
        int size = blockSize * countBlocks;
        int[][] res = new int[countVertex][countVertex];

        for (int i = 0; i < countBlocks; i++) {
            for (int j = 0; j < countBlocks; j++) {
                for (int x = 0; x < blockSize; x++) {
                    for (int y = 0; y < blockSize; y++) {
                        int rowIndex = i * blockSize + x;
                        int columnIndex = j * blockSize + y;
                        boolean isOutOfRange = rowIndex >= countVertex || columnIndex >= countVertex;
                        if (!isOutOfRange) {
                            res[i * blockSize + x][j * blockSize + y] = blocks[i][j][x][y];
                        }

                    }
                }
            }
        }

        return res;
    }
}
