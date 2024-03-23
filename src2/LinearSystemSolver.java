import MM.dev.Algorithms.gaussSeidel;
import MM.dev.Algorithms.jacobi;
import java.util.Arrays;
import java.util.Random;


public class LinearSystemSolver {
    public static int [] matrix_sizes={100, 400, 500, 700, 1000, 1500 ,2000};
    static int maxIterations = 1000;
    static double tolerance = 1e-6;

    @FunctionalInterface
    public interface Solver {
    void solve(double[][] A, double[] b, int maxIterations, double tolerance);
}

public static double[][] generateRandomMatrix(int n) {
    Random rand = new Random();
    double[][] matrix = new double[n][n];

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            matrix[i][j] = rand.nextDouble();
        }
        double rowSum = 0.0;
        for (int j = 0; j < n; j++) {
            if (i != j) {
                rowSum += Math.abs(matrix[i][j]);
            }
        }
        matrix[i][i] = rowSum*(rand.nextDouble()+1); 
    }
    return matrix;
}

public static double[] generateRandomVector(int n) {
    Random rand = new Random();
    double[] vector = new double[n];

    for (int i = 0; i < n; i++) {
        vector[i] = rand.nextDouble();
    }
    return vector;
}

public static double[] test_method(Solver solver) {
    double[][] A;
    double[] B;
    double[] runTimes=new double[5];
    double[] avgTimes=new double[7];

    for (int i= 0; i <7 ; i++) {
        A = generateRandomMatrix(matrix_sizes[i]);
        B = generateRandomVector(matrix_sizes[i]);

        for (int j = 0; j < 5; j++) {
            long startTime = System.nanoTime();

            solver.solve(A, B, maxIterations, tolerance);
            
            long endTime = System.nanoTime();
            runTimes[j] = (endTime - startTime) / 1e6;  // Convert to milliseconds
        }
        avgTimes[i] = Arrays.stream(runTimes).average().orElse(0.0);
    }
    return avgTimes;
}

    public static void main(String[] args) {
       double[] avgTimesJacobi= test_method(jacobi::jacobiSolver);; //testing Jacobi
       double[] avgTimesGaussSeidel =test_method(gaussSeidel::gaussSeidelSolver); // testing gauss seidel
        Plotter.chart(avgTimesJacobi,avgTimesGaussSeidel,2);
        Plotter.Table(avgTimesJacobi,avgTimesGaussSeidel);
    }
}
