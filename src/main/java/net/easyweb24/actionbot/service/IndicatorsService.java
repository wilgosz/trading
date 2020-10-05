/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import net.easyweb24.actionbot.entity.Indicators;
import net.easyweb24.actionbot.indicators.ADX;
import net.easyweb24.actionbot.indicators.AbstractMPIndicators;
import net.easyweb24.actionbot.indicators.BOLLINGER;
import net.easyweb24.actionbot.indicators.CLOSE_PRICE;
import net.easyweb24.actionbot.indicators.MACD;
import net.easyweb24.actionbot.indicators.MONEYFLOW;
import net.easyweb24.actionbot.indicators.RSI;
import net.easyweb24.actionbot.indicators.SMA;
import net.easyweb24.actionbot.indicators.STOCHASTIC;
import net.easyweb24.actionbot.indicators.STOCHASTIC_SLOW;
import net.easyweb24.actionbot.repository.IndicatorsRepository;
import org.springframework.stereotype.Service;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;

@Service
public class IndicatorsService {
    
    private final IndicatorsRepository indicatorsRepository;
    
    public IndicatorsService(IndicatorsRepository indicatorsRepository){
      this.indicatorsRepository = indicatorsRepository;
    }
    
    /*public MACD getMACD(BarSeries series){
        MACD macd = new MACD(series, 26, 9, 0);
        return macd;
    }*/
    
    public ADX getAdx(BarSeries series){
        Indicators ind = indicatorsRepository.findByAbbreviation("ADX");
        ADX adx = new ADX(series, ind);
        return adx;
    }
    
    public CLOSE_PRICE getClosePrice(BarSeries series){
        CLOSE_PRICE price = new CLOSE_PRICE(series);
        return price;
    }
    public MACD getMACD(BarSeries series){
        
        Indicators ind = indicatorsRepository.findByAbbreviation("MACD");
        MACD macd = new MACD(series, ind.getPeriodLong(), ind.getPeriodShort(), 0);
        return macd;
    }
    
    public MACD getMACD(BarSeries series, int strategie_id){
        MACD macd = new MACD(series, 10, 5, 0);
        return macd;
    }
    
    public SMA getSMA(BarSeries series){
        Indicators ind = indicatorsRepository.findByAbbreviation("SMA");
        SMA sma = new SMA(series, ind);
        return sma;
    }
    
    public SMA getSMA(BarSeries series, int strategie_id){
        SMA sma = new SMA(series, 30, 5, 0);
        return sma;
    }
    
    public STOCHASTIC getStochastic(BarSeries series){
        Indicators ind = indicatorsRepository.findByAbbreviation("STOCHASTIC");
        STOCHASTIC stochastic = new STOCHASTIC(series, ind.getPeriodLong(), ind.getPeriodShort(), ind.getPeriod());
        return stochastic;
    }
    
    public STOCHASTIC_SLOW getStochasticS(BarSeries series){
        Indicators ind = indicatorsRepository.findByAbbreviation("STOCHASTIC_SLOW");
        STOCHASTIC_SLOW stochastic = new STOCHASTIC_SLOW(series, ind);
        return stochastic;
    }
    
    public MONEYFLOW getMoneyFlow(BarSeries series){
        Indicators ind = indicatorsRepository.findByAbbreviation("MONEYFLOW");
        MONEYFLOW mfl = new MONEYFLOW(series, ind);
        return mfl;
    }
    
    public RSI getRsi(BarSeries series){
        Indicators ind = indicatorsRepository.findByAbbreviation("RSI");
        RSI rsi = new RSI(series, ind);
        return rsi;
    }
    
    public BOLLINGER getBollinger(BarSeries series){
        Indicators ind = indicatorsRepository.findByAbbreviation("BOLLINGER");
        BOLLINGER bllg = new BOLLINGER(series, ind.getPeriodLong(), ind.getPeriodShort(), ind.getPeriod());
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
    
    public Indicators findByAbbreviationAndSave(Indicators data){
        Indicators ind = indicatorsRepository.findByAbbreviation(data.getAbbreviation());
        
        if(ind != null){
            ind.setBottomBorder(data.getBottomBorder());
            ind.setTopBorder(data.getTopBorder());
            ind.setPeriod(data.getPeriod());
            ind.setPeriodLong(data.getPeriodLong());
            ind.setPeriodShort(data.getPeriodShort());
            ind = indicatorsRepository.save(ind);
        }
        return ind;
    }
    
    public Map addTopBottom(Map map, AbstractMPIndicators ind){
        List add = new ArrayList();
        add.add(ind.getBottom_border());
        add.add(ind.getTop_border());
        add.add(ind.getPeriod());
        add.add(ind.getPeriod_short());
        add.add(ind.getPeriod_long());
        map.put("add", add);
        return map;
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
