package Solvers.Row;

import java.util.concurrent.Callable;

public class FloydParallelRowTask implements Callable<Object> {
    int k, rowStart, rowEnd;
    int[][] dist, prev;

    public FloydParallelRowTask(int[][] dist, int[][] prev, int k, int rowStart, int rowEnd) {
        this.k = k;
        this.dist = dist;
        this.rowStart = rowStart;
        this.rowEnd = rowEnd;
        this.prev = prev;
    }

    @Override
    public Object call() {
        int countVertex = dist.length;

        for (int i = rowStart; i <= rowEnd; i++) {
            for (int j = 0; j < countVertex; j++) {
                int distKJ = dist[k][j];
                int distIK = dist[i][k];

                int a = distIK + distKJ;

                if (distIK != Integer.MAX_VALUE && distKJ != Integer.MAX_VALUE && dist[i][j] > a) {
                    dist[i][j] = a;
                    prev[i][j] = prev[k][j];
                }
            }
        }

        return null;
    }
}

