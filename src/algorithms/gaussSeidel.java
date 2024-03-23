package MM.dev.Algorithms;
import java.util.Arrays;

public class gaussSeidel {

    public static void gaussSeidelSolver(double[][] A, double[] b, int maxIterations, double tolerance) {
        int n = A.length;
        double[] x = new double[n];
        Arrays.fill(x, 0); 
        for (int k = 0; k < maxIterations; k++) {
            double maxDiff = 0;
            for (int i = 0; i < n; i++) {
                double sum = b[i];
                double diag = A[i][i];
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        sum -= A[i][j] * x[j];
                    }
                }
                double xNew = sum / diag;

                double diff = Math.abs(xNew - x[i]);
                if (diff > maxDiff) {
                    maxDiff = diff;
                }

                x[i] = xNew;
            }

            if (maxDiff < tolerance) {
                return;
            }
        }

    }

}
