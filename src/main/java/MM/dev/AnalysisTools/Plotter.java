package MM.dev.AnalysisTools;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.JFrame;
public class Plotter {
    public static void plotResults(double[] avgTimesJacobi, double[] avgTimesGaussSeidel,int[] matrixSizes) {

        XYSeries seriesJacobi = new XYSeries("Jacobi");
        XYSeries seriesGaussSeidel = new XYSeries("Gauss-Seidel");

        for (int i = 0; i < avgTimesJacobi.length; i++) {
            seriesJacobi.add(matrixSizes[i], avgTimesJacobi[i]);
            seriesGaussSeidel.add(matrixSizes[i], avgTimesGaussSeidel[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesJacobi);
        dataset.addSeries(seriesGaussSeidel);

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
}