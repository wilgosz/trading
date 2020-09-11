package net.easyweb24.actionbot.controllers;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.easyweb24.actionbot.components.utils.DBToBarSeries;
import net.easyweb24.actionbot.indicators.BOLLINGER;
import net.easyweb24.actionbot.indicators.MACD;
import net.easyweb24.actionbot.indicators.MONEYFLOW;
import net.easyweb24.actionbot.indicators.RSI;
import net.easyweb24.actionbot.indicators.SMA;
import net.easyweb24.actionbot.indicators.STOCHASTIC;
import net.easyweb24.actionbot.indicators.STOCHASTIC_SLOW;
import net.easyweb24.actionbot.service.IndicatorsService;
import net.easyweb24.actionbot.utils.BarsBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.OHLCDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ta4j.core.BarSeries;
import ta4jexamples.loaders.CsvBarsLoader;

/**
 *
 * @author zbigniewwilgosz
 */
@Controller
@RequestMapping("/indicator")
public class IndicatorsController {
    
    @Autowired
    DBToBarSeries dbToBarSeries;
    
    @Autowired
    IndicatorsService indicatorsService;
    
    @ResponseBody
    @GetMapping("/rsi")
    public ResponseEntity<Map<String, List>> RSI(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        //response.setContentType("image/png");
        //OutputStream outputStream = response.getOutputStream();
        BarSeries series = dbToBarSeries.getBars("AMZN");
        RSI rsi = indicatorsService.getRsi(series);
        Map<String, List>  map = new HashMap<String, List>();
        map.put("R", rsi.getRsiValues());
        map.put("dates", rsi.getDates());
        //rsi.drawToOutputStrem(outputStream, 1200, 200);
        return ResponseEntity.ok().body(map);
    }
    
    @ResponseBody
    @GetMapping("/sma")
    public void SMA(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        BarSeries series = dbToBarSeries.getBars("AMZN");
        SMA sma = indicatorsService.getSMA(series);
        sma.drawToOutputStrem(outputStream);
        
    }
    @ResponseBody
    @GetMapping("/macd")
    public ResponseEntity<Map<String, List>> MACD(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        //response.setContentType("image/png");
        //OutputStream outputStream = response.getOutputStream();
        BarSeries series = dbToBarSeries.getBars("AMZN");
        MACD macd = indicatorsService.getMACD(series);
        //macd.drawToOutputStrem(outputStream);
        Map<String, List>  map = new HashMap<String, List>();
        map.put("S", macd.getShortTermEmaValues());
        map.put("L", macd.getLongTermEmaValues());
        map.put("macd", macd.getMacdValues());
        map.put("dates", macd.getDates());
        return ResponseEntity.ok().body(map);
    }
    
    @ResponseBody
    @GetMapping("/stochastic")
    public ResponseEntity<Map<String, List>> Stochastic(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //response.setContentType("image/png");
        //OutputStream outputStream = response.getOutputStream();
        BarSeries series = dbToBarSeries.getBars("AMZN");
        STOCHASTIC_SLOW stochastic = indicatorsService.getStochasticS(series);
        //stochastic.drawToOutputStrem(outputStream, 2400, 200);
        Map<String, List>  map = new HashMap<String, List>();
        map.put("K", stochastic.getStochasticOscillKValues());
        map.put("D", stochastic.getStochasticOscillDValues());
        map.put("dates", stochastic.getDates());
        return ResponseEntity.ok().body(map);
    }
    
    @ResponseBody
    @GetMapping("/moneyflow")
    public void MoneyFlow(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        BarSeries series = dbToBarSeries.getBars("AMZN");
        MONEYFLOW mfl = indicatorsService.getMoneyFlow(series);
        mfl.drawToOutputStrem(outputStream, 1200, 200);
    }
    
    @ResponseBody
    @GetMapping("/bollinger")
    public ResponseEntity<Map<String, List>> BollingerBand(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        //response.setContentType("image/png");
        //OutputStream outputStream = response.getOutputStream();
        BarSeries series = dbToBarSeries.getBars("AMZN");
        BOLLINGER bllg = indicatorsService.getBollinger(series);
        Map<String, List>  map = new HashMap<String, List>();
        map.put("BU", bllg.getUpBBandValues());
        map.put("BL", bllg.getLowBBandValues());
        map.put("BM", bllg.getMiddleBBandValues());
        map.put("BP", bllg.getClosePriceValues());
        map.put("CND", indicatorsService.getCandles(series));
        map.put("dates", bllg.getDates());
        //bllg.drawToOutputStrem(outputStream, 1200, 400);
        return ResponseEntity.ok().body(map);
    }
    
    @ResponseBody
    @GetMapping("/candles")
    public ResponseEntity<Map<String, List>>  Candles(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BarSeries series = dbToBarSeries.getBars("AMZN");
        Map<String, List>  map = new HashMap<String, List>();
        BOLLINGER bllg = indicatorsService.getBollinger(series);
        map.put("BU", bllg.getUpBBandValues());
        map.put("BL", bllg.getLowBBandValues());
        map.put("BM", bllg.getMiddleBBandValues());
        map.put("BP", bllg.getClosePriceValues());
        map.put("CND", indicatorsService.getCandles(series));
        return ResponseEntity.ok().body(map);
    }
    
    @ResponseBody
    @GetMapping("/candle")
    public void CandleStick(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        BarSeries series = CsvBarsLoader.loadAppleIncSeries(request);

        /*
         * Creating the OHLC dataset
         */
        OHLCDataset ohlcDataset = BarsBuilder.createOHLCDataset(series, "AAPL");

        /*
         * Creating the additional dataset
         */
        TimeSeriesCollection xyDataset = BarsBuilder.createAdditionalDataset(series, "AAPL");

        /*
         * Creating the chart
         */
        JFreeChart chart = ChartFactory.createCandlestickChart(null, null, "OHLC", ohlcDataset, false);
        // Candlestick rendering
        CandlestickRenderer renderer = new CandlestickRenderer();
        //renderer.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_SMALLEST);
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(renderer);
        // Additional dataset
        int index = 1;
        /*plot.setDataset(index, xyDataset);
        plot.mapDatasetToRangeAxis(index, 0);
        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);
        renderer2.setSeriesPaint(index, Color.gray);
        plot.setRenderer(index, renderer2);*/
        // Misc
        plot.setRangeGridlinePaint(Color.WHITE);
        //plot.setBackgroundPaint(Color.white);
        NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
        numberAxis.setAutoRangeIncludesZero(false);
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        
        BufferedImage image = chart.createBufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB, null);
        ImageIO.write(image, "PNG", outputStream);
        //return outputStream;
    }
}
