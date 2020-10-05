package net.easyweb24.actionbot.indicators;

import java.util.List;
import net.easyweb24.actionbot.entity.Indicators;
import net.easyweb24.actionbot.utils.BarsBuilder;
import org.jfree.data.time.TimeSeriesCollection;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.MACDSMAIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.num.Num;

/**
 *
 * @author zbigniewwilgosz
 */
public class SMA extends AbstractMPIndicators{
    
    private SMAIndicator shortSma;
    private SMAIndicator longSma;
    private MACDSMAIndicator macdsma;

    /**
     * 
     * @param series
     * @param period_long Long term
     * @param period_short Short term
     * @param period Bars count 
     */
    public SMA(BarSeries series, int period_long, int period_short, int period) {
        super(series, period_long, period_short, period);
    }
    
    public SMA(BarSeries series, Indicators ind) {
        super(series, ind);
    }

    @Override
    protected void init(BarSeries series, int period_long, int period_short, int period) {
        setShortSma(new SMAIndicator(getClosePrice(), period_short));
        setLongSma(new SMAIndicator(getClosePrice(), period_long));
        setMacdsma(new MACDSMAIndicator(getClosePrice(), period_short, period_long));
    }

    @Override
    protected TimeSeriesCollection addToDrawingSeries(TimeSeriesCollection dataset) {
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), this.getShortSma(), "short"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), this.getLongSma(), "long"));
        dataset.addSeries(BarsBuilder.buildChartBarSeries(getSeries(), this.getClosePrice(), "price"));
        return dataset;
    }

    @Override
    protected String getName() {
        return "SMA";
    }
    /**
     * @return the shortSma
     */
    public SMAIndicator getShortSma() {
        return shortSma;
    }
    
    public List<Double> getShortSmaValues() {
        return getValues(shortSma);
    }

    /**
     * @param shortSma the shortSma to set
     */
    public void setShortSma(SMAIndicator shortSma) {
        this.shortSma = shortSma;
    }

    /**
     * @return the longSma
     */
    public SMAIndicator getLongSma() {
        return longSma;
    }
    
    public List<Double> getLongSmaValues(){
        return getValues(longSma);
    }

    /**
     * @param longSma the longSma to set
     */
    public void setLongSma(SMAIndicator longSma) {
        this.longSma = longSma;
    }

    /**
     * @return the macdsma
     */
    public MACDSMAIndicator getMacdsma() {
        return macdsma;
    }
    
    public List<Double> getMacdsmaValues(){
        return getValues(macdsma);
    }

    /**
     * @param macdsma the macdsma to set
     */
    public void setMacdsma(MACDSMAIndicator macdsma) {
        this.macdsma = macdsma;
    }
    
    
    
}
