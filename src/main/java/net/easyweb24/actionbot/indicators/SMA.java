package net.easyweb24.actionbot.indicators;

import java.util.List;
import net.easyweb24.actionbot.utils.BarsBuilder;
import org.jfree.data.time.TimeSeriesCollection;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.num.Num;

/**
 *
 * @author zbigniewwilgosz
 */
public class SMA extends AbstractMPIndicators{
    
    SMAIndicator shortSma;
    SMAIndicator longSma;

    public SMA(BarSeries series, int period_long, int period_short, int period) {
        super(series, period_long, period_short, period);
    }

    @Override
    protected void init(BarSeries series, int period_long, int period_short, int period) {
        shortSma = new SMAIndicator(getClosePrice(), 5);
        longSma = new SMAIndicator(getClosePrice(), 30);
    }

    @Override
    protected TimeSeriesCollection addToDrawingSeries(TimeSeriesCollection dataset) {
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), this.getShortTermEma(), "short"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), this.getLongTermEma(), "long"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), this.getClosePrice(), "price"));
        return dataset;
    }

    @Override
    protected String getName() {
        return "SMA";
    }

    public SMAIndicator getShortTermEma() {
        return shortSma;
    }
    
    public List<Double> getShortTermEmaValues(){
        return getValues(shortSma);
    }

    public SMAIndicator getLongTermEma() {
        return longSma;
    }
    
    public List<Double> getLongTermEmaValues() {
        return getValues(longSma);
    }
    
}
