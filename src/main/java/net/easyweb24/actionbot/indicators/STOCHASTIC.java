/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.indicators;

import java.util.List;
import net.easyweb24.actionbot.entity.Indicators;
import net.easyweb24.actionbot.utils.BarsBuilder;
import org.jfree.data.time.TimeSeriesCollection;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.StochasticOscillatorDIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;

/**
 *
 * @author zbigniewwilgosz
 */
public class STOCHASTIC extends AbstractMPIndicators {

    private StochasticOscillatorKIndicator stochasticOscillK;
    private StochasticOscillatorDIndicator stochasticOscillD;
    
    /**
     * 
     * @param series
     * @param period_long Smoothing D
     * @param period_short Don't use
     * @param period Bars count 
     */
    public STOCHASTIC(BarSeries series, int period_long, int period_short, int period) {
        super(series, period_long, period_short, period);
    }
    
    public STOCHASTIC(BarSeries series, Indicators ind) {
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
    protected void init(BarSeries series, int period_long, int period_short, int period) {
        stochasticOscillK = new StochasticOscillatorKIndicator(series, period);
        stochasticOscillD = new StochasticOscillatorDIndicator(getStochasticOscillK(), period_long);
    }

    @Override
    protected TimeSeriesCollection addToDrawingSeries(TimeSeriesCollection dataset) {

        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), stochasticOscillK, getName()+"-K"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), stochasticOscillD, getName()+"-D"));

        return dataset;
    }

    @Override
    protected String getName() {
        return "STOCHASTIC";
    }

    public StochasticOscillatorKIndicator getStochasticOscillK() {
        return stochasticOscillK;
    }
    
    public List<Double> getStochasticOscillKValues() {
        return getValues(stochasticOscillK);
    }

    public StochasticOscillatorDIndicator getStochasticOscillD() {
        return stochasticOscillD;
    }
    
    public List<Double> getStochasticOscillDValues() {
        return getValues(stochasticOscillD);
    }

}
