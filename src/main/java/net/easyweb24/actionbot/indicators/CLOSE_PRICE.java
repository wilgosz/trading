/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.indicators;

import org.jfree.data.time.TimeSeriesCollection;
import org.ta4j.core.BarSeries;

/**
 *
 * @author zbigniewwilgosz
 */
public class CLOSE_PRICE extends AbstractMPIndicators{
    
    public CLOSE_PRICE(BarSeries series) {
        super(series, 0, 0, 0);
    }

    @Override
    protected void init(BarSeries series, int period_long, int period_short, int period) {
    }

    @Override
    protected TimeSeriesCollection addToDrawingSeries(TimeSeriesCollection dataset) {
        return dataset;
    }

    @Override
    protected String getName() {
        return "Close Price";
    }
    
}
