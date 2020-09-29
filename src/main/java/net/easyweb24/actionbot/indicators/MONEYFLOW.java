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
import org.ta4j.core.indicators.MFIIndicator;
import org.ta4j.core.indicators.helpers.TypicalPriceIndicator;

/**
 *
 * @author zbigniewwilgosz
 */
public class MONEYFLOW extends AbstractMPIndicators{
    
    private TypicalPriceIndicator typicalPrice;
    private MFIIndicator mfl;

    public MONEYFLOW(BarSeries series, int period_long, int period_short, int period) {
        super(series, period_long, period_short, period);
    }
    
    public MONEYFLOW(BarSeries series, Indicators ind){
        super(series, ind);
    }

    @Override
    protected void init(BarSeries series, int period_long, int period_short, int period) {
       typicalPrice = new TypicalPriceIndicator(series);
       mfl = new MFIIndicator(series, typicalPrice, period);
    }

    @Override
    protected TimeSeriesCollection addToDrawingSeries(TimeSeriesCollection dataset) {
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), getMfi(), getName()));
        return dataset;
    }

    @Override
    protected String getName() {
        return "MONEYFLOW";
    }

    public MFIIndicator getMfi() {
        return mfl;
    }
    
    public List<Double> getMfiValues() {
        return getValues(mfl);
    }
    
}
