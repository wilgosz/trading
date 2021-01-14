/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.easyweb24.actionbot.dto.IndicatorsDTO;
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
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;

@Service
public class IndicatorsService {

    private final IndicatorsRepository indicatorsRepository;

    public IndicatorsService(IndicatorsRepository indicatorsRepository) {
        this.indicatorsRepository = indicatorsRepository;
    }

    /*public MACD getMACD(BarSeries series){
        MACD macd = new MACD(series, 26, 9, 0);
        return macd;
    }*/
    public ADX getAdx(BarSeries series) {

        Indicators ind = indicatorsRepository.findByAbbreviation("ADX");
        IndicatorsDTO ind2 = convertToDto(ind);
        ADX adx = new ADX(series, ind2);
        return adx;
    }

    public CLOSE_PRICE getClosePrice(BarSeries series) {
        CLOSE_PRICE price = new CLOSE_PRICE(series);
        return price;
    }

    public MACD getMACD(BarSeries series) {

        Indicators ind = indicatorsRepository.findByAbbreviation("MACD");
        IndicatorsDTO ind2 = convertToDto(ind);
        MACD macd = new MACD(series, ind2);
        return macd;
    }

    public MACD getMACD(BarSeries series, int strategie_id) {
        MACD macd = new MACD(series, 10, 5, 0);
        return macd;
    }

    public SMA getSMA(BarSeries series) {
        Indicators ind = indicatorsRepository.findByAbbreviation("SMA");
        IndicatorsDTO ind2 = convertToDto(ind);

        SMA sma = new SMA(series, ind2);
        return sma;
    }

    public SMA getSMA(BarSeries series, int strategie_id) {
        SMA sma = new SMA(series, 30, 5, 0);
        return sma;
    }

    public STOCHASTIC getStochastic(BarSeries series) {
        Indicators ind = indicatorsRepository.findByAbbreviation("STOCHASTIC");
        IndicatorsDTO ind2 = convertToDto(ind);
        STOCHASTIC stochastic = new STOCHASTIC(series, ind2);
        return stochastic;
    }

    public STOCHASTIC_SLOW getStochasticS(BarSeries series) {
        Indicators ind = indicatorsRepository.findByAbbreviation("STOCHASTIC_SLOW");
        IndicatorsDTO ind2 = convertToDto(ind);
        STOCHASTIC_SLOW stochastic = new STOCHASTIC_SLOW(series, ind2);
        return stochastic;
    }

    public MONEYFLOW getMoneyFlow(BarSeries series) {
        Indicators ind = indicatorsRepository.findByAbbreviation("MONEYFLOW");
        IndicatorsDTO ind2 = convertToDto(ind);
        MONEYFLOW mfl = new MONEYFLOW(series, ind2);
        return mfl;
    }

    public RSI getRsi(BarSeries series) {
        Indicators ind = indicatorsRepository.findByAbbreviation("RSI");
        IndicatorsDTO ind2 = convertToDto(ind);
        RSI rsi = new RSI(series, ind2);
        return rsi;
    }

    public BOLLINGER getBollinger(BarSeries series) {
        Indicators ind = indicatorsRepository.findByAbbreviation("BOLLINGER");
        IndicatorsDTO ind2 = convertToDto(ind);
        BOLLINGER bllg = new BOLLINGER(series, ind2);
        return bllg;
    }

    public List<CandleData> getCandles(BarSeries series) {
        List<CandleData> data = new ArrayList<>();
        int begin = series.getBeginIndex();
        int end = series.getEndIndex();

        for (int i = begin; i <= end; i++) {
            CandleData candle = new CandleData();
            Bar bar = series.getBar(i);
            candle.setX(Date.from(bar.getEndTime().toInstant()));
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

    public Indicators findByAbbreviationAndSave(Indicators data) {
        Indicators ind = indicatorsRepository.findByAbbreviation(data.getAbbreviation());

        if (ind != null) {
            ind.setBottomBorder(data.getBottomBorder());
            ind.setTopBorder(data.getTopBorder());
            ind.setPeriod(data.getPeriod());
            ind.setPeriodLong(data.getPeriodLong());
            ind.setPeriodShort(data.getPeriodShort());
            ind = indicatorsRepository.save(ind);

        }
        return ind;
    }

    public Map addTopBottom(Map map, AbstractMPIndicators ind) {
        List add = new ArrayList();
        add.add(ind.getBottom_border());
        add.add(ind.getTop_border());
        add.add(ind.getPeriod());
        add.add(ind.getPeriod_short());
        add.add(ind.getPeriod_long());
        map.put("add", add);
        return map;
    }

    public IndicatorsDTO convertToDto(Indicators ind) {
        try {
            IndicatorsDTO ind2 = new IndicatorsDTO();
            BeanUtils.copyProperties(ind2, ind);
            return ind2;
        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(IndicatorsService.class.getName()).log(Level.SEVERE, null, ex);
            return new IndicatorsDTO();
        }
    }

    public class CandleData {

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
