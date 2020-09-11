/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.easyweb24.actionbot.indicators.BOLLINGER;
import net.easyweb24.actionbot.indicators.MACD;
import net.easyweb24.actionbot.indicators.MONEYFLOW;
import net.easyweb24.actionbot.indicators.RSI;
import net.easyweb24.actionbot.indicators.SMA;
import net.easyweb24.actionbot.indicators.STOCHASTIC;
import net.easyweb24.actionbot.indicators.STOCHASTIC_SLOW;
import org.springframework.stereotype.Service;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;

@Service
public class IndicatorsService {
    
    public MACD getMACD(BarSeries series){
        MACD macd = new MACD(series, 26, 9, 0);
        return macd;
    }
    
    public SMA getSMA(BarSeries series){
        SMA sma = new SMA(series, 30, 5, 0);
        return sma;
    }
    
    public STOCHASTIC getStochastic(BarSeries series){
        STOCHASTIC stochastic = new STOCHASTIC(series, 0, 0, 14);
        return stochastic;
    }
    
    public STOCHASTIC_SLOW getStochasticS(BarSeries series){
        STOCHASTIC_SLOW stochastic = new STOCHASTIC_SLOW(series, 0, 0, 14);
        return stochastic;
    }
    
    public MONEYFLOW getMoneyFlow(BarSeries series){
        MONEYFLOW mfl = new MONEYFLOW(series, 0, 0, 14);
        return mfl;
    }
    
    public RSI getRsi(BarSeries series){
        RSI rsi = new RSI(series, 0, 0, 14);
        return rsi;
    }
    
    public BOLLINGER getBollinger(BarSeries series){
        BOLLINGER bllg = new BOLLINGER(series, 0, 0, 14);
        return bllg;
    }
    
    public List<CandleData> getCandles(BarSeries series){
        List<CandleData> data = new ArrayList<>();
        int begin = series.getBeginIndex();
        int end = series.getEndIndex();
        
        for(int i = begin; i <= end; i++){
            CandleData candle = new CandleData();
            Bar bar = series.getBar(i);
            candle.setX( Date.from(bar.getEndTime().toInstant()));
            List<Double> y = new ArrayList<>();
            y.add(bar.getOpenPrice().doubleValue());
            y.add(bar.getHighPrice().doubleValue());
            y.add(bar.getLowPrice().doubleValue());
            y.add(bar.getClosePrice().doubleValue());
            candle.setY(y);
            data.add(candle);
            
        }
        return data;
    }
    
    public class CandleData{
        private Date x;
        private List<Double> y;

        /**
         * @return the x
         */
        public Date getX() {
            return x;
        }

        /**
         * @param x the x to set
         */
        public void setX(Date x) {
            this.x = x;
        }

        /**
         * @return the y
         */
        public List<Double> getY() {
            return y;
        }

        /**
         * @param y the y to set
         */
        public void setY(List<Double> y) {
            this.y = y;
        }
        
        
    }
}
