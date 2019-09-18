package edu.coursera.parallel;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import static edu.rice.pcdp.PCDP.forall2d;
import static edu.rice.pcdp.PCDP.forseq2d;

/**
 * Wrapper class for implementing matrix multiply efficiently in parallel.
 */
public final class MatrixMultiply {
    /**
     * Default constructor.
     */
    private MatrixMultiply() {
    }

    /**
     * Perform a two-dimensional matrix multiply (A x B = C) sequentially.
     *
     * @param A An input matrix with dimensions NxN
     * @param B An input matrix with dimensions NxN
     * @param C The output matrix
     * @param N Size of each dimension of the input matrices
     */
    public static void seqMatrixMultiply(final double[][] A, final double[][] B,
            final double[][] C, final int N) {
        forseq2d(0, N - 1, 0, N - 1, (i, j) -> {
            C[i][j] = 0.0;
            for (int k = 0; k < N; k++) {
                C[i][j] += A[i][k] * B[k][j];
            }
        });
    }

    /**
     * Perform a two-dimensional matrix multiply (A x B = C) in parallel.
     *
     * @param A An input matrix with dimensions NxN
     * @param B An input matrix with dimensions NxN
     * @param C The output matrix
     * @param N Size of each dimension of the input matrices
     */
    public static void parMatrixMultiply(final double[][] A, final double[][] B,
            final double[][] C, final int N) {

        if (N < 6) {
            seqMatrixMultiply(A, B, C, N);
        }else {
            int taskNumber = 16;
            int batch = N / taskNumber;
            MyMatrixMultiply[] multiplies = new MyMatrixMultiply[taskNumber];
            for (int iter = 0; iter < taskNumber; iter++) {
                multiplies[iter] = new MyMatrixMultiply(A,B,C,N,iter * batch, ((iter + 1) * batch));
            }
            for (int iter = 1; iter < taskNumber; iter++) {
                multiplies[iter].fork();
            }
            multiplies[0].compute();
            for (int iter = 1; iter < taskNumber; iter++) {
                multiplies[iter].join();
            }
        }
    }


    private static class MyMatrixMultiply extends RecursiveAction {

        private final double[][] A;
        private final double[][] B;
        private double[][] refC;
        private final int N;
        private final int startIndexInclusive;
        private final int endIndexExclusive;

        public MyMatrixMultiply(double[][] a, double[][] b, double[][] refC, int n, int startIndexInclusive, int endIndexExclusive) {
            A = a;
            B = b;
            this.refC = refC;
            N = n;
            this.startIndexInclusive = startIndexInclusive;
            this.endIndexExclusive = endIndexExclusive;
        }

        @Override
        protected void compute() {
            for (int i = startIndexInclusive; i < endIndexExclusive; i++) {
                for (int j = 0; j < N; j++) {
                    refC[i][j] = 0.0;
                    for (int k = 0; k < N; k++) {
                        refC[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
        }
    }
}
