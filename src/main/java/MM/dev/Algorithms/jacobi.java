package MM.dev.Algorithms;

import java.util.Arrays;

 public class jacobi {
 
    public static void jacobiSolver(double[][] A, double[] b, int maxIterations, double tolerance) {
    int n = A.length;
    double[] x = new double[n];
    double[] xNew = new double[n];
    Arrays.fill(x, 0); 

    for (int k = 0; k < maxIterations; k++) {
        for (int i = 0; i < n; i++) {
            double sum = b[i];
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    sum -= A[i][j] * x[j];
                }
            }
            xNew[i] = sum / A[i][i];
        }

        double maxDiff = 0;
        for (int i = 0; i < n; i++) {
            double diff = Math.abs(xNew[i] - x[i]);
            if (diff > maxDiff) {
                maxDiff = diff;
            }
        }
        if (maxDiff < tolerance) {
            return;
        }

        System.arraycopy(xNew, 0, x, 0, n);
    }

    }
 }