/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.indicators;

import java.util.List;
import net.easyweb24.actionbot.dto.IndicatorsDTO;
import net.easyweb24.actionbot.entity.Indicators;
import net.easyweb24.actionbot.utils.BarsBuilder;
import org.jfree.data.time.TimeSeriesCollection;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.RSIIndicator;

/**
 *
 * @author zbigniewwilgosz
 */
public class RSI extends AbstractMPIndicators{
    
    RSIIndicator rsi;

    public RSI(BarSeries series, int period_long, int period_short, int period) {
        super(series, period_long, period_short, period);
    }
    
    public RSI(BarSeries series, IndicatorsDTO ind) {
        super(series, ind);
    }

    @Override
    protected void init(BarSeries series, int period_long, int period_short, int period) {
        rsi = new RSIIndicator(getClosePrice(), period);
    }

    @Override
    protected TimeSeriesCollection addToDrawingSeries(TimeSeriesCollection dataset) {
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), getRsi(), getName()));
        return dataset;
    }

    @Override
    protected String getName() {
        return "RSI";
    }

    public RSIIndicator getRsi() {
        return rsi;
    }
    
    public List<Double> getRsiValues() {
        return getValues(rsi);
    }
    
}
