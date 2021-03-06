import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class FrameHistogramPanel extends JPanel {
    class InternalChartPanel extends JPanel {
        private ChartPanel chartPanel = null;
        public ArrayList < int[] > allValues;

        public InternalChartPanel() {
            this.setLayout (new BorderLayout());
        }

        public void showChartForFrame (int frameIndex) {

           int[] values = allValues.get(frameIndex);
            DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

            for (int i = 0 ; i < values.length; i++) {
                dataSet.addValue(values[i], "similarity", Integer.toString(i));
            }

            JFreeChart chart = ChartFactory.createBarChart("", "", "Count", dataSet);
            chart.removeLegend();

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

    private JSlider slider = new JSlider(JSlider.HORIZONTAL);
    public JPanel centerPanel = new JPanel();

    private InternalChartPanel bottomRightPanel = new InternalChartPanel();
    private InternalChartPanel bottomLeftPanel = new InternalChartPanel();
    private InternalChartPanel topRightPanel = new InternalChartPanel();
    private InternalChartPanel topLeftPanel = new InternalChartPanel();

    public InternalChartPanel[] charts = {topLeftPanel};

    public FrameHistogramPanel() {
        //this.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());
        this.add(slider, BorderLayout.NORTH);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                for (InternalChartPanel  chart : charts) {
                    chart.showChartForFrame(slider.getValue());
                }
            }
        });

        this.centerPanel.setLayout(new GridLayout(1, 1));
        this.add(centerPanel, BorderLayout.CENTER);


        this.centerPanel.add(topLeftPanel);
//        centerPanel.add(topRightPanel);
//        centerPanel.add(bottomLeftPanel);
//        centerPanel.add(bottomRightPanel);
    }

    public void loadHistogram (ArrayList<int[]> topLeft) {
        topLeftPanel.allValues = topLeft;
//        topRightPanel.allValues = topRight;
//        bottomRightPanel.allValues = bottomRight;
//        bottomLeftPanel.allValues = bottomLeft;

       topLeftPanel.showChartForFrame(0);
        slider.setMaximum(topLeft.size());
    }
}
