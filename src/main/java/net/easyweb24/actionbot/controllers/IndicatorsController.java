package net.easyweb24.actionbot.controllers;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.easyweb24.actionbot.components.utils.DBToBarSeries;
import net.easyweb24.actionbot.entity.Indicators;
import net.easyweb24.actionbot.indicators.ADX;
import net.easyweb24.actionbot.indicators.BOLLINGER;
import net.easyweb24.actionbot.indicators.CLOSE_PRICE;
import net.easyweb24.actionbot.indicators.MACD;
import net.easyweb24.actionbot.indicators.MONEYFLOW;
import net.easyweb24.actionbot.indicators.RSI;
import net.easyweb24.actionbot.indicators.SMA;
import net.easyweb24.actionbot.indicators.STOCHASTIC;
import net.easyweb24.actionbot.indicators.STOCHASTIC_SLOW;
import net.easyweb24.actionbot.repository.IndicatorsRepository;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.ta4j.core.BarSeries;
import ta4jexamples.loaders.CsvBarsLoader;

/**
 *
 * @author zbigniewwilgosz
 */
@RestController
@RequestMapping("/indicator")
public class IndicatorsController {

    @Autowired
    DBToBarSeries dbToBarSeries;

    @Autowired
    IndicatorsService indicatorsService;

    @ResponseBody
    @GetMapping("/rsi/{symbol}")
    public ResponseEntity<Map<String, List>> RSI(@PathVariable(name = "symbol") String symbol) {

        //response.setContentType("image/png");
        //OutputStream outputStream = response.getOutputStream();
        BarSeries series = dbToBarSeries.getBars(symbol);
        Map<String, List> map = new HashMap<String, List>();
        RSI rsi = indicatorsService.getRsi(series);
        map.put("R", rsi.getRsiValues());
        map.put("dates", rsi.getDates());
        map = indicatorsService.addTopBottom(map, rsi);
        //rsi.drawToOutputStrem(outputStream, 1200, 200);
        return ResponseEntity.ok().body(map);
    }

    @ResponseBody
    @GetMapping("/sma/{symbol}")
    public ResponseEntity<Map<String, List>> SMA(@PathVariable(name = "symbol") String symbol) {

        BarSeries series = dbToBarSeries.getBars(symbol);
        Map<String, List> map = new HashMap<String, List>();
        SMA sma = indicatorsService.getSMA(series);
        map.put("SMA", sma.getMacdsmaValues());
        map.put("dates", sma.getDates());
        map = indicatorsService.addTopBottom(map, sma);
        return ResponseEntity.ok().body(map);
    }

    @ResponseBody
    @GetMapping("/macd/{symbol}")
    public ResponseEntity<Map<String, List>> MACD(@PathVariable(name = "symbol") String symbol) {

        BarSeries series = dbToBarSeries.getBars(symbol);
        Map<String, List> map = new HashMap<String, List>();
        MACD macd = indicatorsService.getMACD(series);
        map.put("S", macd.getShortTermEmaValues());
        map.put("L", macd.getLongTermEmaValues());
        map.put("macd", macd.getMacdValues());
        map.put("dates", macd.getDates());
        map = indicatorsService.addTopBottom(map, macd);
        return ResponseEntity.ok().body(map);
    }

    @ResponseBody
    @GetMapping("/stochastic/{symbol}")
    public ResponseEntity<Map<String, List>> Stochastic(@PathVariable(name = "symbol") String symbol) {

        BarSeries series = dbToBarSeries.getBars(symbol);
        Map<String, List> map = new HashMap<String, List>();
        STOCHASTIC_SLOW stochastic = indicatorsService.getStochasticS(series);
        map.put("K", stochastic.getStochasticOscillKValues());
        map.put("D", stochastic.getStochasticOscillDValues());
        map.put("dates", stochastic.getDates());
        map = indicatorsService.addTopBottom(map, stochastic);
        return ResponseEntity.ok().body(map);
    }

    @ResponseBody
    @GetMapping("/adx/{symbol}")
    public ResponseEntity<Map<String, List>> Adx(@PathVariable(name = "symbol") String symbol) {

        BarSeries series = dbToBarSeries.getBars(symbol);
        Map<String, List> map = new HashMap<String, List>();
        ADX adx = indicatorsService.getAdx(series);
        map.put("ADX", adx.getAdxIndicatorValues());
        map.put("DMIN", adx.getMinusDIIndicatorValues());
        map.put("DPLUS", adx.getPlusDIIndicatorValues());
        map.put("SMA", adx.getSmaIndicatorValues());
        map.put("dates", adx.getDates());
        map = indicatorsService.addTopBottom(map, adx);
        return ResponseEntity.ok().body(map);
    }

    @ResponseBody
    @GetMapping("/moneyflow/{symbol}")
    public ResponseEntity<Map<String, List>> MoneyFlow(@PathVariable(name = "symbol") String symbol) {

        BarSeries series = dbToBarSeries.getBars(symbol);
        Map<String, List> map = new HashMap<String, List>();
        MONEYFLOW mfl = indicatorsService.getMoneyFlow(series);
        map.put("MF", mfl.getMfiValues());
        map.put("dates", mfl.getDates());
        map = indicatorsService.addTopBottom(map, mfl);

        return ResponseEntity.ok().body(map);
    }

    @ResponseBody
    @GetMapping("/bollinger/{symbol}")
    public ResponseEntity<Map<String, List>> BollingerBand(@PathVariable(name = "symbol") String symbol) {

        BarSeries series = dbToBarSeries.getBars(symbol);
        Map<String, List> map = new HashMap<String, List>();
        BOLLINGER bllg = indicatorsService.getBollinger(series);
        map.put("BU", bllg.getUpBBandValues());
        map.put("BL", bllg.getLowBBandValues());
        map.put("BM", bllg.getMiddleBBandValues());
        map.put("BP", bllg.getClosePriceValues());
        map.put("dates", bllg.getDates());
        map = indicatorsService.addTopBottom(map, bllg);
        return ResponseEntity.ok().body(map);
    }

    @ResponseBody
    @GetMapping("/price/{symbol}")
    public ResponseEntity<Map<String, List>> ClosePrice(@PathVariable(name = "symbol") String symbol) {

        BarSeries series = dbToBarSeries.getBars(symbol);
        Map<String, List> map = new HashMap<String, List>();
        CLOSE_PRICE price = indicatorsService.getClosePrice(series);
        map.put("price", price.getClosePriceValues());
        map.put("dates", price.getDates());
        return ResponseEntity.ok().body(map);
    }

    @ResponseBody
    @GetMapping("/candles/{symbol}")
    public ResponseEntity<Map<String, List>> Candles(@PathVariable(name = "symbol") String symbol) {
        BarSeries series = dbToBarSeries.getBars(symbol);
        Map<String, List> map = new HashMap<String, List>();
        map.put("CND", indicatorsService.getCandles(series));
        return ResponseEntity.ok().body(map);
    }

    @ResponseBody
    @PutMapping("/updateindicator")
    public ResponseEntity<Map<String, List>> updateIndicator(ServletRequest request, Indicators ind ) {
        
        String symbol = request.getParameter("symbol");
        indicatorsService.findByAbbreviationAndSave(ind);
        
        switch (ind.getAbbreviation()) {
            case "RSI":
                return RSI(symbol);
            case "STOCHASTIC_SLOW":
                return Stochastic(symbol);
            case "SMA":
                return SMA(symbol);
            case "MACD":
                return MACD(symbol);
            case "BOLLINGER":
                return BollingerBand(symbol);
            case "MONEYFLOW":
                return MoneyFlow(symbol);
            case "ADX":
                return Adx(symbol);
        }
        return ResponseEntity.ok().body(new HashMap<String, List>());
    }

    @ResponseBody
    @GetMapping("/candle")
    public void CandleStick(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
    
    private Indicators setData(Indicators data){
        return indicatorsService.findByAbbreviationAndSave(data);
    }
}
