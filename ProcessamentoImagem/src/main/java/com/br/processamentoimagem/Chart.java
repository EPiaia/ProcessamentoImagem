package com.br.processamentoimagem;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import javax.imageio.ImageIO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Eduardo Piaia
 */
public class Chart implements Serializable {

    public static BufferedImage getHistogramImage(Map<Integer, Integer> histogram) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Integer key : histogram.keySet()) {
            Integer value = histogram.get(key);
            if (value != null && value > 0) {
                dataset.addValue(value, "Frequency", key);
            }
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Histogram",
                "Values", "Frequency",
                dataset, PlotOrientation.VERTICAL,
                true, true, false);

        File chart = new File("/tmp/histogram.png");

        try {
            ChartUtils.saveChartAsPNG(chart, barChart, 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage img = new BufferedImage(600, 400, BufferedImage.TYPE_INT_ARGB);
        try {
            img = ImageIO.read(chart);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }

}
