package Solvers.Row;

import Core.FloydResult;
import Core.Graph;
import Core.IFloydSolver;
import Core.NegativeCycleChecker;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FloydParallelRow implements IFloydSolver {
    int countThreads;
    int avgCountRows;

    public FloydParallelRow(int countThreads, int avgCountRows) {
        this.countThreads = countThreads;
        this.avgCountRows = avgCountRows;
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

        ExecutorService threadPool = Executors.newFixedThreadPool(countThreads);

        int countTasksOnIteration = countVertex / avgCountRows;
        int extraRows = countVertex % avgCountRows;

        ArrayList<Future<Object>> futures = new ArrayList<>();

        for (int k = 0; k < countVertex; k++) {
            for (int i = 0; i < countTasksOnIteration; i++) {
                int countRows = i == countTasksOnIteration - 1 ? avgCountRows + extraRows : avgCountRows;
                int rowOffset = i * avgCountRows;
                futures.add(threadPool.submit(new FloydParallelRowTask(dist, prev, k, rowOffset, (rowOffset + countRows) - 1)));
            }

            for (Future<Object> future: futures) {
                try {
                    future.get();
                } catch (Exception e) {

                }
            }

            futures.clear();
        }

        threadPool.shutdown();

        return new FloydResult(dist, prev, NegativeCycleChecker.isNegativeCycle(dist));
    }
}
