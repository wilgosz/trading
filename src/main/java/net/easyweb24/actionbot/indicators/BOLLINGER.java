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
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsLowerIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;

/**
 *
 * @author zbigniewwilgosz
 */
public class BOLLINGER extends AbstractMPIndicators {

    EMAIndicator avg14;
    StandardDeviationIndicator sd14;
    BollingerBandsMiddleIndicator middleBBand;
    BollingerBandsLowerIndicator lowBBand;
    BollingerBandsUpperIndicator upBBand;

    public BOLLINGER(BarSeries series, int period_long, int period_short, int period) {
        super(series, period_long, period_short, period);
    }

    @Override
    protected void init(BarSeries series, int period_long, int period_short, int period) {
        avg14 = new EMAIndicator(getClosePrice(), period);
        sd14 = new StandardDeviationIndicator(getClosePrice(), period);
        middleBBand = new BollingerBandsMiddleIndicator(avg14);
        lowBBand = new BollingerBandsLowerIndicator(middleBBand, sd14);
        upBBand = new BollingerBandsUpperIndicator(middleBBand, sd14);
    }

    @Override
    protected TimeSeriesCollection addToDrawingSeries(TimeSeriesCollection dataset) {
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), getMiddleBBand(), "middle"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), getLowBBand(), "low"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), getUpBBand(), "up"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), getClosePrice(), "price"));
        return dataset;
    }

    @Override
    protected String getName() {
        return "BOLLINGER";
    }

    public BollingerBandsMiddleIndicator getMiddleBBand() {
        return middleBBand;
    }

    public List<Double> getMiddleBBandValues() {
        return getValues(middleBBand);
    }

    public BollingerBandsLowerIndicator getLowBBand() {
        return lowBBand;
    }

    public List<Double> getLowBBandValues() {
        return getValues(lowBBand);
    }

    public BollingerBandsUpperIndicator getUpBBand() {
        return upBBand;
    }

    public List<Double> getUpBBandValues() {
        return getValues(upBBand);
    }
}
