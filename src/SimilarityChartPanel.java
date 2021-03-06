import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class SimilarityChartPanel extends JPanel {
    private ChartPanel chartPanel = null;

    public SimilarityChartPanel() {
        this.setLayout(new BorderLayout());
    }

    public void loadSimilarityChart(ArrayList<Double> values) {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

        for (int i = 0 ; i < values.size(); i++) {
            dataSet.addValue(values.get(i), "similarity", Integer.toString(i));
        }

        JFreeChart chart = ChartFactory.createLineChart("Difference", "", "", dataSet);
        chart.removeLegend();

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        ValueAxis range = plot.getRangeAxis();
        range.setVisible(false);

        if (chartPanel == null) {
            chartPanel = new ChartPanel(chart);
            this.add(chartPanel, BorderLayout.CENTER);
            this.repaint();
            this.validate();
        }
        else {
            chartPanel.setChart(chart);
        }
    }
}
