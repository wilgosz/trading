package net.easyweb24.actionbot.indicators;

import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.easyweb24.actionbot.dto.IndicatorsDTO;
import net.easyweb24.actionbot.entity.Indicators;
import net.easyweb24.actionbot.utils.TradingChart;
import org.jfree.data.time.TimeSeriesCollection;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.AbstractIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.Num;


public abstract class AbstractMPIndicators {
    
    private final  int beginIndex;
    private final  int endIndex;
    private final BarSeries series;
    private final ClosePriceIndicator closePrice;
    private List<String> dates;
    private int bottom_border = 0;
    private int top_border = 0;
    private int period = 0;
    private int period_long = 0;
    private int period_short = 0;
    
    public AbstractMPIndicators(BarSeries series,int period_long, int period_short, int period, int bottom_border, int top_border){
        
        this.series = series;
        this.period = period;
        this.period_long = period_long;
        this.period_short = period_short;
        this.closePrice = new ClosePriceIndicator(this.getSeries());
        this.bottom_border = bottom_border;
        this.top_border = top_border;
        this.init(this.series, period_long, period_short, period);
        this.beginIndex = closePrice.getBarSeries().getBeginIndex();
        this.endIndex = closePrice.getBarSeries().getEndIndex();
        int begin = series.getBeginIndex();
        int end = series.getEndIndex();
         dates = new ArrayList<String>();
        for ( int i = begin; i<= end ; i++ ){
            dates.add(series.getBar(i).getEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.GERMANY))); 
        }
    }
    public AbstractMPIndicators(BarSeries series,int period_long, int period_short, int period){
        this(series, period_long, period_short, period, 0, 0);
    }
    
    public AbstractMPIndicators(BarSeries series, IndicatorsDTO ind){
        this(series, ind.getPeriodLong(), ind.getPeriodShort(), ind.getPeriod(), ind.getBottomBorder(), ind.getTopBorder());
    }
    
    /**
     *
     * @param series
     * @param period_long
     * @param period_short
     */
    protected abstract void init(BarSeries series, int period_long, int period_short, int period );

    /**
     * @return the closePrice
     */
    public ClosePriceIndicator getClosePrice() {
        return closePrice;
    }
    
    public List<Double> getClosePriceValues(){
        return this.getValues(closePrice);
    }
    
    protected List<Double> getValues(AbstractIndicator idicator){
        List<Double> values = new ArrayList<>();
        for(int i=this.getBeginIndex(); i<= this.getEndIndex(); i++){
            values.add(((Num)idicator.getValue(i)).doubleValue());
        }
        return values;
    }
    
    public List<String> getDates(){
        return dates;
    }
    
    public void drawToOutputStrem(OutputStream outputStream) throws IOException{
        drawToOutputStrem(outputStream, 1000, 800);
    }
    
    public void drawToOutputStrem(OutputStream outputStream, int width, int height) throws IOException{
        TimeSeriesCollection dataset = addToDrawingSeries(new TimeSeriesCollection());
        TradingChart chart = new TradingChart();
        chart.drawChart(dataset, getName() , Boolean.FALSE).createImage(outputStream, width, height);
    }
    
    protected abstract TimeSeriesCollection addToDrawingSeries(TimeSeriesCollection dataset);
    
    protected abstract String getName();

    /**
     * @return the beginIndex
     */
    public int getBeginIndex() {
        return beginIndex;
    }

    /**
     * @return the endIndex
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * @return the series
     */
    public final BarSeries getSeries() {
        return series;
    }

    /**
     * @return the bottom_border
     */
    public int getBottom_border() {
        return bottom_border;
    }

    /**
     * @param bottom_border the bottom_border to set
     */
    public void setBottom_border(int bottom_border) {
        this.bottom_border = bottom_border;
    }

    /**
     * @return the top_border
     */
    public int getTop_border() {
        return top_border;
    }

    /**
     * @param top_border the top_border to set
     */
    public void setTop_border(int top_border) {
        this.top_border = top_border;
    }

    /**
     * @return the period
     */
    public int getPeriod() {
        return period;
    }

    /**
     * @return the period_long
     */
    public int getPeriod_long() {
        return period_long;
    }

    /**
     * @return the period_short
     */
    public int getPeriod_short() {
        return period_short;
    }
    
    

}
