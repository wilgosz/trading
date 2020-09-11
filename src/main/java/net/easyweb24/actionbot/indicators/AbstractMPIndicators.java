package net.easyweb24.actionbot.indicators;

import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
    
    public AbstractMPIndicators(BarSeries series,int period_long, int period_short, int period){
        
        this.series = series;
        this.closePrice = new ClosePriceIndicator(this.getSeries());
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
    
    

}
