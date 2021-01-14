/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.indicators;

import java.util.List;
import net.easyweb24.actionbot.dto.IndicatorsDTO;
import net.easyweb24.actionbot.entity.Indicators;
import org.jfree.data.time.TimeSeriesCollection;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.adx.ADXIndicator;
import org.ta4j.core.indicators.adx.MinusDIIndicator;
import org.ta4j.core.indicators.adx.PlusDIIndicator;

/**
 *
 * @author zbigniewwilgosz
 */
public class ADX extends AbstractMPIndicators{
    
    private ADXIndicator adxIndicator;
    private PlusDIIndicator plusDIIndicator;
    private MinusDIIndicator minusDIIndicator;
    private SMAIndicator smaIndicator;

    public ADX(BarSeries series, IndicatorsDTO ind) {
        super(series, ind);
    }

    @Override
    protected void init(BarSeries series, int period_long, int period_short, int period) {
        
        setSmaIndicator(new SMAIndicator(getClosePrice(), period_long));
        setAdxIndicator(new ADXIndicator(series, period));
        setPlusDIIndicator(new PlusDIIndicator(series, period));
        setMinusDIIndicator(new MinusDIIndicator(series, period));
    }

    @Override
    protected TimeSeriesCollection addToDrawingSeries(TimeSeriesCollection dataset) {
        return dataset;
    }

    @Override
    protected String getName() {
        return "ADX";
    }

    /**
     * @return the adxIndicator
     */
    public ADXIndicator getAdxIndicator() {
        return adxIndicator;
    }
    
    /**
     * 
     * @return 
     */
    public List<Double> getAdxIndicatorValues(){
        return getValues(adxIndicator);
    }

    /**
     * @param adxIndicator the adxIndicator to set
     */
    public void setAdxIndicator(ADXIndicator adxIndicator) {
        this.adxIndicator = adxIndicator;
    }

    /**
     * @return the plusDIIndicator
     */
    public PlusDIIndicator getPlusDIIndicator() {
        return plusDIIndicator;
    }
    
    public List<Double> getPlusDIIndicatorValues(){
        return getValues(plusDIIndicator);
    }

    /**
     * @param plusDIIndicator the plusDIIndicator to set
     */
    public void setPlusDIIndicator(PlusDIIndicator plusDIIndicator) {
        this.plusDIIndicator = plusDIIndicator;
    }

    /**
     * @return the minusDIIndicator
     */
    public MinusDIIndicator getMinusDIIndicator() {
        return minusDIIndicator;
    }
    
    public List<Double> getMinusDIIndicatorValues(){
        return getValues(minusDIIndicator);
    }

    /**
     * @param minusDIIndicator the minusDIIndicator to set
     */
    public void setMinusDIIndicator(MinusDIIndicator minusDIIndicator) {
        this.minusDIIndicator = minusDIIndicator;
    }

    /**
     * @return the smaIndicator
     */
    public SMAIndicator getSmaIndicator() {
        return smaIndicator;
    }
    
    public List<Double> getSmaIndicatorValues(){
        return getValues(smaIndicator);
    } 

    /**
     * @param smaIndicator the smaIndicator to set
     */
    public void setSmaIndicator(SMAIndicator smaIndicator) {
        this.smaIndicator = smaIndicator;
    }
    
    
}
