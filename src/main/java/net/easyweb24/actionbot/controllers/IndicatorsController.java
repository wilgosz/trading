/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.controllers;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.easyweb24.actionbot.utils.BarsBuilder;
import net.easyweb24.actionbot.utils.TradingChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.indicators.MFIIndicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.StochasticOscillatorDIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsLowerIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.TypicalPriceIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.num.Num;
import ta4jexamples.loaders.CsvBarsLoader;

/**
 *
 * @author zbigniewwilgosz
 */
@Controller
@RequestMapping("/indicator")
public class IndicatorsController {
    
    
    
    @ResponseBody
    @GetMapping("/rsi")
    public void RSI(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        BarSeries series = CsvBarsLoader.loadAppleIncSeries(request);
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        
        RSIIndicator rsi = new RSIIndicator(closePrice, 14);
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(BarsBuilder.buildChartBarSeries(series, rsi, "RSI"));
        
        TradingChart chart = new TradingChart();
        chart.drawChart(dataset, 70, 30, "RSI", Boolean.FALSE).createImage(outputStream, 1200, 200);
        //return outputStream;
        
    }
    
    @ResponseBody
    @GetMapping("/sma")
    public void SMA(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        BarSeries series = CsvBarsLoader.loadAppleIncSeries(request);
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        
        SMAIndicator shortSma = new SMAIndicator(closePrice, 5);
        SMAIndicator longSma = new SMAIndicator(closePrice, 30);
        
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(BarsBuilder.buildChartBarSeries(series, shortSma, "short SMA"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(series, longSma, "long SMA"));
        
        TradingChart chart = new TradingChart();
        chart.drawChart(dataset, "SMA", Boolean.FALSE).createImage(outputStream, 1200, 200);
        //return outputStream;
        
    }
    @ResponseBody
    @GetMapping("/macd")
    public void MACD(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        BarSeries series = CsvBarsLoader.loadAppleIncSeries(request);
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        
        EMAIndicator shortTermEma = new EMAIndicator(closePrice, 9);
        EMAIndicator longTermEma = new EMAIndicator(closePrice, 26);
        MACDIndicator macd = new MACDIndicator(closePrice, 9, 26);
        
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        
        
        dataset.addSeries(BarsBuilder.buildChartBarSeries(series, shortTermEma, "short"));
        
        dataset.addSeries(BarsBuilder.buildChartBarSeries(series, longTermEma, "long"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(series, closePrice, "price"));
        //dataset.addSeries(BarsBuilder.buildChartBarSeries(series, macd, "macd"));
        
        TradingChart chart = new TradingChart();
        chart.drawChart(dataset, "MACD", Boolean.FALSE).createImage(outputStream, 1200, 200);
        //return outputStream;
    }
    
    @ResponseBody
    @GetMapping("/stochastic")
    public void Stochastic(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        BarSeries series = CsvBarsLoader.loadAppleIncSeries(request);
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        
        
        StochasticOscillatorKIndicator stochasticOscillK = new StochasticOscillatorKIndicator(series, 14);
        StochasticOscillatorDIndicator stochasticOscillD = new StochasticOscillatorDIndicator(stochasticOscillK);
        MACDIndicator macd = new MACDIndicator(closePrice, 9, 26);
        
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        
        
        dataset.addSeries(BarsBuilder.buildChartBarSeries(series, stochasticOscillK, "K"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(series, stochasticOscillD, "D"));
        //dataset.addSeries(BarsBuilder.buildChartBarSeries(series, macd, "macd"));
        
        TradingChart chart = new TradingChart();
        chart.drawChart(dataset, 80, 20, "Stochastic", Boolean.FALSE).createImage(outputStream, 1200, 200);
        //return outputStream;
    }
    
    @ResponseBody
    @GetMapping("/moneyflow")
    public void MoneyFlow(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        BarSeries series = CsvBarsLoader.loadAppleIncSeries(request);
        
        TypicalPriceIndicator typicalPrice = new TypicalPriceIndicator(series);
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        MFIIndicator mfi = new MFIIndicator(series, typicalPrice, 14);
        
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        
        
        dataset.addSeries(BarsBuilder.buildChartBarSeries(series, mfi, "Flow"));
        //dataset.addSeries(BarsBuilder.buildChartBarSeries(series, macd, "macd"));
        
        TradingChart chart = new TradingChart();
        chart.drawChart(dataset, 80, 20, "MoneyFlow", Boolean.FALSE).createImage(outputStream, 1200, 200);
        //return outputStream;
    }
    
    @ResponseBody
    @GetMapping("/bollinger")
    public void BollingerBand(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        BarSeries series = CsvBarsLoader.loadAppleIncSeries(request);
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        
        EMAIndicator avg14 = new EMAIndicator(closePrice, 14);
        StandardDeviationIndicator sd14 = new StandardDeviationIndicator(closePrice, 14);
        BollingerBandsMiddleIndicator middleBBand = new BollingerBandsMiddleIndicator(avg14);
        BollingerBandsLowerIndicator lowBBand = new BollingerBandsLowerIndicator(middleBBand, sd14);
        BollingerBandsUpperIndicator upBBand = new BollingerBandsUpperIndicator(middleBBand, sd14);
        
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        
        //dataset.addSeries(BarsBuilder.buildChartBarSeries(series, middleBBand, "middle"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(series, lowBBand, "low"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(series, upBBand, "up"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(series, closePrice, "price"));
        TradingChart chart = new TradingChart();
        chart.drawChart(dataset, "BollingerBand", Boolean.FALSE).createImage(outputStream, 1200, 200);
        //return outputStream;
        
    }
    
    
    private void collection(HttpServletRequest request){
        
        BarSeries series = CsvBarsLoader.loadAppleIncSeries(request);
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
        
        dataset.addSeries(BarsBuilder.buildChartBarSeries(series, rsi, "RSI"));
        
        //dataset.addSeries(buildChartBarSeries(series, shortTermEma, "MACD short"));
        //dataset.addSeries(buildChartBarSeries(series, longTermEma, "MACD long"));
        //dataset.addSeries(buildChartBarSeries(series, macd, "MACD"));
    }
    
}
