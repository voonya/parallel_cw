package Core;

import java.util.*;

import Solvers.FloydSequential;


public class Graph {
    private int[][] weightsMatrix;

    public Graph(int countVertex) {
        this.weightsMatrix = new int[countVertex][countVertex];
        for (int[] row : weightsMatrix)
            Arrays.fill(row, Integer.MAX_VALUE);
    }

    public int[][] getWeightsMatrix() {
        return weightsMatrix;
    }

    public void addWeight(int from, int to, int weight) {
        weightsMatrix[from][to] = weight;
    }


    public int getWeight(int from, int to) {
        return weightsMatrix[from][to];
    }

    public int getCountVertex() {
        return weightsMatrix.length;
    }

    static public Graph generateGraph(int countVertex, int avgEdges, int disp, int negBound, int avgNeg, int dispNeg, int avgPos, int dispPos) {
        Graph graph;

        Random rand = new Random();
        FloydSequential solver = new FloydSequential();
        FloydResult floydResult;

        do {
            graph = new Graph(countVertex);
            for (int i = 0; i < countVertex; i++) {
                int currentCountEdges = rand.nextInt(2 * disp + 1) - disp + avgEdges;
                for (int j = 0; j < currentCountEdges; j++) {
                    int to = rand.nextInt(countVertex);

                    while (to == i || graph.getWeight(i, to) != Integer.MAX_VALUE) {
                        to = rand.nextInt(countVertex);
                    }

                    int weight;

                    if (rand.nextInt(negBound) == 0) {
                        weight = rand.nextInt(2 * dispNeg + 1) + avgNeg - dispNeg;
                    } else {
                        weight = rand.nextInt(2 * dispPos + 1) - dispPos + avgPos;

                    }

                    graph.addWeight(i, to, weight);
                }
            }
            floydResult = solver.getMinDistances(graph);
        } while (floydResult.isNegativeCycle);


        return graph;
    }

    public void printStatistic() {
        int countEdges = 0;

        for (int i = 0; i < weightsMatrix.length; i++) {
            for (int j = 0; j < weightsMatrix.length; j++) {
                if (weightsMatrix[i][j] != Integer.MAX_VALUE) {
                    countEdges++;
                }
            }
        }

        System.out.printf("%-20s:%7d\n", "COUNT VERTEX", weightsMatrix.length);
        System.out.printf("%-20s:%7d\n", "COUNT EDGES", countEdges);
        System.out.printf("%-20s:%7.2f\n", "AVG EDGES", (countEdges * 1.0 / weightsMatrix.length));
    }
}
