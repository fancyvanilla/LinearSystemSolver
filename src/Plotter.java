import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;

public class Plotter {
    public static int [] matrixSizes={100, 400, 500, 700, 1000, 1500 ,2000};

    public static double[] calculateMovingAverage(double[] input, int windowSize) {
        double[] result = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            int start = Math.max(0, i - windowSize + 1);
            double sum = 0.0;
            for (int j = start; j <= i; j++) {
                sum += input[j];
            }
            result[i] = sum / (i - start + 1);
        }
        return result;
    }

    private static XYSeriesCollection getXySeriesCollection(double[] avgTimesJacobi, double[] avgTimesGaussSeidel, int windowSize) {
        double[] avgTimesJacobiSmoothed = calculateMovingAverage(avgTimesJacobi, windowSize);
        double[] avgTimesGaussSeidelSmoothed = calculateMovingAverage(avgTimesGaussSeidel, windowSize);

        XYSeries seriesJacobi = new XYSeries("Jacobi Smoothed");
        XYSeries seriesGaussSeidel = new XYSeries("Gauss-Seidel Smoothed");

        for (int i = 0; i < avgTimesJacobi.length; i++) {
            seriesJacobi.add(matrixSizes[i], avgTimesJacobiSmoothed[i]);
            seriesGaussSeidel.add(matrixSizes[i], avgTimesGaussSeidelSmoothed[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesJacobi);
        dataset.addSeries(seriesGaussSeidel);
        return dataset;
    }

    public static void chart(double[] avgTimesJacobi, double[] avgTimesGaussSeidel,int windowSize) {

        XYSeriesCollection dataset = getXySeriesCollection(avgTimesJacobi, avgTimesGaussSeidel, windowSize);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Comparison of Jacobi and Gauss-Seidel",
                "Matrix Size",
                "Average Time (ms)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel panel = new ChartPanel(chart);
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }


    public static void Table(double[] avgTimesJacobi, double[] avgTimesGaussSeidel) {

        String[] columnNames = {"Matrix Size", "Jacobi", "Gauss-Seidel"};

        Object[][] data = new Object[avgTimesJacobi.length][3];
        for (int i = 0; i < matrixSizes.length; i++) {
            data[i][0] = matrixSizes[i]; 
            data[i][1] = avgTimesJacobi[i];
            data[i][2] = avgTimesGaussSeidel[i];
        }

        JTable table = new JTable(data, columnNames);

        JFrame frame = new JFrame("Average Execution Times (ms)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new JScrollPane(table));

        frame.pack();
        frame.setVisible(true);
    }
}