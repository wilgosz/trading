package org.ta4j.core.indicators;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.helpers.TypicalPriceIndicator;
import org.ta4j.core.num.Num;

/**
 * Created by yogesh on 20-05-2018.
 */

public class MFIIndicator extends CachedIndicator<Num>{
    private final int timeFrame;
    private final TypicalPriceIndicator typicalPriceIndicator;
    private final BarSeries timeSeries;
    private float raw_money_flow = 0;
    private float positive_flow = 0;
    private float negative_flow = 0;
    private float money_flow_ratio = 0;
    private float money_flow_index = 0;

    public MFIIndicator(BarSeries timeSeries, TypicalPriceIndicator typicalPriceIndicator, int timeFrame) {
        super(typicalPriceIndicator);
        this.typicalPriceIndicator = typicalPriceIndicator;
        this.timeSeries = timeSeries;
        this.timeFrame = timeFrame;
        
    }

    @Override
    protected Num calculate(int index) {

         raw_money_flow = 0;
         positive_flow = 0;
         negative_flow = 0;
         money_flow_ratio = 0;
         money_flow_index = 0;

        for (int i = index; i >= index - (timeFrame-1); i--) {

            try{
                raw_money_flow = typicalPriceIndicator.getValue(i).multipliedBy(timeSeries.getBar(i).getVolume()).floatValue();

                if (typicalPriceIndicator.getValue(i).isGreaterThan(typicalPriceIndicator.getValue(i-1))) {
                    positive_flow = positive_flow + raw_money_flow;
                    //Log.d("<< P flow", String.valueOf(raw_money_flow));
                } else if (typicalPriceIndicator.getValue(i).isLessThan(typicalPriceIndicator.getValue(i-1))){
                    negative_flow = negative_flow + raw_money_flow;
                    //Log.d("<< N flow", String.valueOf(raw_money_flow));
                }}
            catch(IndexOutOfBoundsException ex){

            }
        }

        money_flow_ratio = positive_flow / negative_flow;

        money_flow_index = 1 + money_flow_ratio;

        money_flow_index = 100 / money_flow_index;

        money_flow_index = 100 - money_flow_index;
        
        
        try {
            return timeSeries.numOf((float)money_flow_index);
        } catch (Exception e) {
            //System.out.println(money_flow_index);
            return timeSeries.numOf(10);
        }
        
    }
}