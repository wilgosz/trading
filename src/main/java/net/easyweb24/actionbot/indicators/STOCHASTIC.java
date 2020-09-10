/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.indicators;

import java.util.List;
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
    private HighPriceIndicator hight;

    public STOCHASTIC(BarSeries series, int period_long, int period_short, int period) {
        super(series, period_long, period_short, period);
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
        stochasticOscillK = new StochasticOscillatorKIndicator(series, 14);
        stochasticOscillD = new StochasticOscillatorDIndicator(getStochasticOscillK());
        hight= new HighPriceIndicator(series);
    }

    @Override
    protected TimeSeriesCollection addToDrawingSeries(TimeSeriesCollection dataset) {

        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), getStochasticOscillK(), getName()+"-K"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), getStochasticOscillD(), getName()+"-D"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), hight, getName()));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), getClosePrice(), getName()));

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
