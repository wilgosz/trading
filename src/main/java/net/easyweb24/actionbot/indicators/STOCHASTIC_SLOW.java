/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.indicators;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.easyweb24.actionbot.dto.IndicatorsDTO;
import net.easyweb24.actionbot.entity.Indicators;
import net.easyweb24.actionbot.utils.BarsBuilder;
import org.jfree.data.time.TimeSeriesCollection;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.StochasticOscillatorDIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;

/**
 *
 * @author zbigniewwilgosz
 */
public class STOCHASTIC_SLOW extends AbstractMPIndicators {

    private SMAIndicator stochasticOscillK;
    private SMAIndicator stochasticOscillD;
    
    
    /**
     * 
     * @param series
     * @param period_long  Smoothing D
     * @param period_short Smoothing K
     * @param period Bar Count 
     */
    public STOCHASTIC_SLOW(BarSeries series, int period_long, int period_short, int period) {
        super(series, period_long, period_short, period);
    }
    
    
    public STOCHASTIC_SLOW(BarSeries series, IndicatorsDTO ind) {
        super(series, ind);
    }

    /**
     *
     * @param series
     * @param period_long  Smoothing D
     * @param period_short Smoothing K
     * @param period Bar Count
     */
    @Override
    protected void init(BarSeries series, int period_long, int period_short, int period) {
        stochasticOscillK = new SMAIndicator(new StochasticOscillatorKIndicator(series, period),period_short);
        stochasticOscillD = new SMAIndicator(getStochasticOscillK(),period_long);
        
    }

    @Override
    protected TimeSeriesCollection addToDrawingSeries(TimeSeriesCollection dataset) {

        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), getStochasticOscillK(), getName()+"-K"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), getStochasticOscillD(), getName()+"-D"));

        return dataset;
    }

    @Override
    protected String getName() {
        return "STOCHASTIC";
    }

    public SMAIndicator getStochasticOscillK() {
        return stochasticOscillK;
    }
    
    public List<Double> getStochasticOscillKValues() {
        return getValues(stochasticOscillK);
    }

    public SMAIndicator getStochasticOscillD() {
        return stochasticOscillD;
    }
    
    public List<Double> getStochasticOscillDValues() {
        return getValues(stochasticOscillD);
    }

}
