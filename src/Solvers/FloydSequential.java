package Solvers;

import Core.*;

public class FloydSequential implements IFloydSolver {
    public FloydResult getMinDistances(Graph graph) {
        int countVertex = graph.getCountVertex();

        int[][] dist = new int[countVertex][countVertex];
        int[][] prev = new int[countVertex][countVertex];

        for (int i = 0; i < countVertex; i++) {
            for (int j = 0; j < countVertex; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                    prev[i][j] = Integer.MAX_VALUE;
                    continue;
                }

                int weight = graph.getWeight(i, j);
                dist[i][j] = weight;

                if (weight != Integer.MAX_VALUE)
                    prev[i][j] = i;
                else
                    prev[i][j] = -1;
            }
        }

        for (int k = 0; k < countVertex; k++) {
            for (int i = 0; i < countVertex; i++) {
                for (int j = 0; j < countVertex; j++) {
                    int distIK = dist[i][k];
                    int distKJ =  dist[k][j];

                    if (distIK == Integer.MAX_VALUE || distKJ == Integer.MAX_VALUE) {
                        continue;
                    }
                    if (dist[i][j] > distIK + distKJ) {
                        dist[i][j] = distIK + distKJ;
                        prev[i][j] = prev[k][j];
                    }
                }
            }
        }

        return new FloydResult(dist, prev, NegativeCycleChecker.isNegativeCycle(dist));
    }
}
