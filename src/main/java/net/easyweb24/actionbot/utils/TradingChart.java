/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeriesCollection;

/**
 *
 * @author zbigniewwilgosz
 */
public class TradingChart {
    
    private JFreeChart chart;
    
    public TradingChart drawChart(TimeSeriesCollection dataset,String ylabel, Boolean legend){
        chart = ChartFactory.createTimeSeriesChart(null, // title
                null, // x-axis label
                ylabel, // y-axis label
                dataset, // data
                legend, // create legend?
                false, // generate tooltips?
                false // generate URLs?
        );
        chart.setBorderVisible(false);
        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));
        
        return this;
    }
    
    public TradingChart drawChart(TimeSeriesCollection dataset, int markerTop, int markerBottom, String ylabel, Boolean legend){
        
        drawChart(dataset, ylabel, legend);
        XYPlot plot = (XYPlot) chart.getPlot();
        
        ValueMarker marker = new ValueMarker(markerTop);  // position is the value on the axis
        marker.setPaint(Color.black);
        plot.addRangeMarker(marker);
        
        ValueMarker marker2 = new ValueMarker(markerBottom);  // position is the value on the axis
        marker2.setPaint(Color.black);
        plot.addRangeMarker(marker2);
        
        return this;
    }
    
    public void createImage(OutputStream outputStream, int width, int height) throws IOException{
        BufferedImage image = chart.createBufferedImage(width, height, BufferedImage.TYPE_INT_RGB, null);
        ImageIO.write(image, "PNG", outputStream);
    }
}
