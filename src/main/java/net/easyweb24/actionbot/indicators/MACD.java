/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.indicators;

import java.util.List;
import net.easyweb24.actionbot.dto.IndicatorsDTO;
import net.easyweb24.actionbot.utils.BarsBuilder;
import org.jfree.data.time.TimeSeriesCollection;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.MACDIndicator;


public class MACD extends AbstractMPIndicators{
    
    private EMAIndicator shortTermEma;
    private EMAIndicator longTermEma;
    private MACDIndicator macd;
    
    /**
     * 
     * @param series
     * @param period_long Long term
     * @param period_short Short term
     * @param period Bars count 
     */
    public MACD(BarSeries series,int period_long, int period_short, int period){
        super(series, period_long, period_short, period);
    }
    
    public MACD(BarSeries series, IndicatorsDTO ind) {
        super(series, ind);
    }
    
    /**
     *
     * @param series
     * @param period_long
     * @param period_short
     * @param period
     */
    @Override
    protected void init(BarSeries series, int period_long, int period_short, int period ) {
        
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
        
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), this.getMacd(), "macd"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), this.getShortTermEma(), "short"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), this.getLongTermEma(), "long"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), this.getClosePrice(), "price"));
        
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
