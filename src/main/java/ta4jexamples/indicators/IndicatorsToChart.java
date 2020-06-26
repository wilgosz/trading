/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 Marc de Verdelhan, 2017-2019 Ta4j Organization & respective
 * authors (see AUTHORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package ta4jexamples.indicators;

import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsLowerIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.num.Num;
import ta4jexamples.loaders.CsvBarsLoader;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.jfree.chart.plot.ValueMarker;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import ta4jexamples.loaders.CsvTradesLoader;

/**
 * This class builds a graphical chart showing values from indicators.
 */
public class IndicatorsToChart {

    /**
     * Builds a JFreeChart time series from a Ta4j bar series and an indicator.
     *
     * @param barSeries the ta4j bar series
     * @param indicator the indicator
     * @param name the name of the chart time series
     * @return the JFreeChart time series
     */
    private static org.jfree.data.time.TimeSeries buildChartBarSeries(BarSeries barSeries, Indicator<Num> indicator,
            String name) {
        org.jfree.data.time.TimeSeries chartTimeSeries = new org.jfree.data.time.TimeSeries(name);
        for (int i = 0; i < barSeries.getBarCount(); i++) {
            Bar bar = barSeries.getBar(i);
            chartTimeSeries.add(new Day(Date.from(bar.getEndTime().toInstant())), indicator.getValue(i).doubleValue());
        }
        return chartTimeSeries;
    }

    /**
     * Displays a chart in a frame.
     *
     * @param chart the chart to be displayed
     */
    private static void displayChart(JFreeChart chart) {
        // Chart panel
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new java.awt.Dimension(1200, 500));
        // Application frame
        ApplicationFrame frame = new ApplicationFrame("Ta4j example - Indicators to chart");
        frame.setContentPane(panel);
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        /*
         * Getting bar series
         */
        BarSeries series = CsvTradesLoader.loadBitstampSeries();
        //BarSeries series = CsvBarsLoader.loadAppleIncSeries();

        /*
         * Creating indicators
         */
        // Close price
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        EMAIndicator avg14 = new EMAIndicator(closePrice, 14);
        StandardDeviationIndicator sd14 = new StandardDeviationIndicator(closePrice, 14);

        // Bollinger bands
        BollingerBandsMiddleIndicator middleBBand = new BollingerBandsMiddleIndicator(avg14);
        BollingerBandsLowerIndicator lowBBand = new BollingerBandsLowerIndicator(middleBBand, sd14);
        BollingerBandsUpperIndicator upBBand = new BollingerBandsUpperIndicator(middleBBand, sd14);

        SMAIndicator shortSma = new SMAIndicator(closePrice, 9);
        SMAIndicator longSma = new SMAIndicator(closePrice, 26);
        
        
        RSIIndicator rsi = new RSIIndicator(closePrice, 14);
        
        
        EMAIndicator shortTermEma = new EMAIndicator(closePrice, 9);
        EMAIndicator longTermEma = new EMAIndicator(closePrice, 26);
        MACDIndicator macd = new MACDIndicator(closePrice, 9, 26);

        /*
         * Building chart dataset
         */
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        //dataset.addSeries(buildChartBarSeries(series, closePrice, "Apple Inc. (AAPL) - NASDAQ GS"));
        //dataset.addSeries(buildChartBarSeries(series, lowBBand, "Low Bollinger Band"));
        //dataset.addSeries(buildChartBarSeries(series, upBBand, "High Bollinger Band"));
        
        //dataset.addSeries(buildChartBarSeries(series, shortSma, "SMA short"));
        //dataset.addSeries(buildChartBarSeries(series, longSma, "SMA long"));
        
        dataset.addSeries(buildChartBarSeries(series, rsi, "RSI"));
        
        //dataset.addSeries(buildChartBarSeries(series, shortTermEma, "MACD short"));
        //dataset.addSeries(buildChartBarSeries(series, longTermEma, "MACD long"));
        //dataset.addSeries(buildChartBarSeries(series, macd, "MACD"));
        
        
        
        /*
         * Creating the chart
         */
        JFreeChart chart = ChartFactory.createTimeSeriesChart("Apple Inc. 2013 Close Prices", // title
                "Date", // x-axis label
                "Price Per Unit", // y-axis label
                dataset, // data
                true, // create legend?
                true, // generate tooltips?
                false // generate URLs?
        );
        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));
        
        ValueMarker marker = new ValueMarker(30);  // position is the value on the axis
        marker.setPaint(Color.black);
        plot.addRangeMarker(marker);
        
        ValueMarker marker2 = new ValueMarker(70);  // position is the value on the axis
        marker2.setPaint(Color.black);
        plot.addRangeMarker(marker2);
        /*
         * Displaying the chart
         */
        displayChart(chart);
    }

}
