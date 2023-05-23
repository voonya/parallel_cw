package Solvers.Blocked;

import java.util.concurrent.Callable;

public class FloydParallelBlockedTask implements Callable<Object> {
    int[][] C, A, B, Cprev, Bprev;

    public FloydParallelBlockedTask(int[][] C, int[][] A, int[][] B, int[][] Cprev, int[][] Bprev) {
        this.C = C;
        this.A = A;
        this.B = B;
        this.Cprev = Cprev;
        this.Bprev = Bprev;
    }

    @Override
    public Object call() {
        int blockSize = A.length;
        for (int k = 0; k < blockSize; k++) {
            for (int j = 0; j < blockSize; j++) {
                for (int i = 0; i < blockSize; i++) {
                    int valueIK = A[i][k];
                    int valueKJ = B[k][j];

                    if (valueIK != Integer.MAX_VALUE && valueKJ != Integer.MAX_VALUE && C[i][j] > valueIK + valueKJ) {
                        C[i][j] = valueIK + valueKJ;
                        Cprev[i][j] = Bprev[k][j];
                    }
                }
            }
        }

        return null;
    }
}
