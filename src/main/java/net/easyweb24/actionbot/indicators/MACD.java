/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.indicators;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import net.easyweb24.actionbot.utils.BarsBuilder;
import net.easyweb24.actionbot.utils.TradingChart;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.stereotype.Component;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.AbstractIndicator;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.Num;


public class MACD extends AbstractMPIndicators{
    
    private EMAIndicator shortTermEma;
    private EMAIndicator longTermEma;
    private MACDIndicator macd;
    
    public MACD(BarSeries series,int period_long, int period_short){
        super(series, period_long, period_short);
    }
    
    /**
     *
     * @param series
     * @param period_long
     * @param period_short
     */
    @Override
    protected void init(BarSeries series, int period_long, int period_short ) {
        
        this.shortTermEma = new EMAIndicator(getClosePrice(), period_short);
        this.longTermEma = new EMAIndicator(getClosePrice(), period_long);
        this.macd = new MACDIndicator(getClosePrice(), period_short, period_long);
    }

    /**
     * @return the shortTermEma
     */
    public EMAIndicator getShortTermEma() {
        return shortTermEma;
    }
    
    public List<Double> getShortTermEmaValues() {
        return this.getValues(shortTermEma);
    }

    /**
     * @return the longTermEma
     */
    public EMAIndicator getLongTermEma() {
        return longTermEma;
    }
    
    public List<Double> getLongTermEmaValues() {
        return this.getValues(longTermEma);
    }

    /**
     * @return the macd
     */
    public MACDIndicator getMacd() {
        return macd;
    }
    
    public List<Double> getMacdValues() {
        return this.getValues(macd);
    }
    
    /**
     *
     * @param dataset
     * @return
     */
    @Override
    protected TimeSeriesCollection addToDrawingSeries(TimeSeriesCollection dataset){
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), this.getShortTermEma(), "short"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), this.getLongTermEma(), "long"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), this.getClosePrice(), "price"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), this.getMacd(), "macd"));
        return dataset;
    }
    
    /**
     *
     * @return
     */
    @Override
    protected String getName(){
        return "MACD";
    }

}
