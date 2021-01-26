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
import net.easyweb24.actionbot.entity.StrategiesIndicators;
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
import net.easyweb24.actionbot.repository.StrategiesIndicatorsRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;

@Service
public class IndicatorsService {

    private final IndicatorsRepository indicatorsRepository;
    private final StrategiesIndicatorsRepository strategiesIndicatorsRepository;

    public IndicatorsService(IndicatorsRepository indicatorsRepository, StrategiesIndicatorsRepository strategiesIndicatorsRepository) {
        this.indicatorsRepository = indicatorsRepository;
        this.strategiesIndicatorsRepository = strategiesIndicatorsRepository;
    }

    public CLOSE_PRICE getClosePrice(BarSeries series) {
        CLOSE_PRICE price = new CLOSE_PRICE(series);
        return price;
    }

    public ADX getAdx(BarSeries series, IndicatorsDTO ind2) {
        ADX adx = new ADX(series, ind2);
        return adx;
    }

    public ADX getAdx(BarSeries series) {
        IndicatorsDTO ind2 = getConverted("ADX");
        return getAdx(series, ind2);
    }

    public ADX getAdx(BarSeries series, int strategie_id) {
        if (strategie_id == 0) {
            return getAdx(series);
        }
        IndicatorsDTO ind2 = getConverted("ADX", strategie_id);
        return getAdx(series, ind2);
    }

    public MACD getMACD(BarSeries series, IndicatorsDTO ind2) {
        MACD macd = new MACD(series, ind2);
        return macd;
    }

    private MACD getMACD(BarSeries series) {
        IndicatorsDTO ind2 = getConverted("MACD");
        return getMACD(series, ind2);
    }

    public MACD getMACD(BarSeries series, int strategie_id) {
        if (strategie_id == 0) {
            return getMACD(series);
        }
        IndicatorsDTO ind2 = getConverted("MACD", strategie_id);
        return getMACD(series, ind2);
    }

    public SMA getSMA(BarSeries series, IndicatorsDTO ind2) {
        SMA sma = new SMA(series, ind2);
        return sma;
    }

    public SMA getSMA(BarSeries series) {
        IndicatorsDTO ind2 = getConverted("SMA");
        return getSMA(series, ind2);
    }

    public SMA getSMA(BarSeries series, int strategie_id) {
        if (strategie_id == 0) {
            return getSMA(series);
        }
        IndicatorsDTO ind2 = getConverted("SMA", strategie_id);
        return getSMA(series, ind2);
    }

    public STOCHASTIC getStochastic(BarSeries series, IndicatorsDTO ind2) {
        STOCHASTIC stochastic = new STOCHASTIC(series, ind2);
        return stochastic;
    }

    public STOCHASTIC getStochastic(BarSeries series) {
        IndicatorsDTO ind2 = getConverted("STOCHASTIC");
        return getStochastic(series, ind2);
    }

    public STOCHASTIC getStochastic(BarSeries series, int strategie_id) {
        if (strategie_id == 0) {
            return getStochastic(series);
        }
        IndicatorsDTO ind2 = getConverted("STOCHASTIC", strategie_id);
        return getStochastic(series, ind2);
    }

    public STOCHASTIC_SLOW getStochasticS(BarSeries series, IndicatorsDTO ind2) {
        STOCHASTIC_SLOW stochastic = new STOCHASTIC_SLOW(series, ind2);
        return stochastic;
    }

    public STOCHASTIC_SLOW getStochasticS(BarSeries series) {
        IndicatorsDTO ind2 = getConverted("STOCHASTIC_SLOW");
        return getStochasticS(series, ind2);
    }

    public STOCHASTIC_SLOW getStochasticS(BarSeries series, int strategie_id) {
        if (strategie_id == 0) {
            return getStochasticS(series);
        }
        IndicatorsDTO ind2 = getConverted("STOCHASTIC_SLOW", strategie_id);
        return getStochasticS(series, ind2);
    }

    public MONEYFLOW getMoneyFlow(BarSeries series, IndicatorsDTO ind2) {
        MONEYFLOW mfl = new MONEYFLOW(series, ind2);
        return mfl;
    }

    public MONEYFLOW getMoneyFlow(BarSeries series) {
        IndicatorsDTO ind2 = getConverted("MONEYFLOW");
        return getMoneyFlow(series, ind2);
    }

    public MONEYFLOW getMoneyFlow(BarSeries series, int strategie_id) {
        if (strategie_id == 0) {
            return getMoneyFlow(series);
        }
        IndicatorsDTO ind2 = getConverted("MONEYFLOW", strategie_id);
        return getMoneyFlow(series, ind2);
    }

    public RSI getRsi(BarSeries series, IndicatorsDTO ind2) {
        RSI rsi = new RSI(series, ind2);
        return rsi;
    }

    public RSI getRsi(BarSeries series) {
        IndicatorsDTO ind2 = getConverted("RSI");
        return getRsi(series, ind2);
    }

    public RSI getRsi(BarSeries series, int strategie_id) {
        if (strategie_id == 0) {
            return getRsi(series);
        }
        IndicatorsDTO ind2 = getConverted("RSI", strategie_id);
        return getRsi(series, ind2);
    }

    public BOLLINGER getBollinger(BarSeries series, IndicatorsDTO ind2) {
        BOLLINGER bllg = new BOLLINGER(series, ind2);
        return bllg;
    }

    public BOLLINGER getBollinger(BarSeries series) {

        IndicatorsDTO ind2 = getConverted("BOLLINGER");
        return getBollinger(series, ind2);
    }

    public BOLLINGER getBollinger(BarSeries series, int strategie_id) {
        if (strategie_id == 0) {
            return getBollinger(series);
        }
        IndicatorsDTO ind2 = getConverted("BOLLINGER", strategie_id);
        return getBollinger(series, ind2);
    }

    public IndicatorsDTO getConverted(String abbreviation) {
        Indicators ind = indicatorsRepository.findByAbbreviation(abbreviation);
        IndicatorsDTO ind2 = convertToDto(ind);
        return ind2;
    }

    public IndicatorsDTO getConverted(String abbreviation, int strategie_id) {
        StrategiesIndicators ind = getStrategiesIndicator(abbreviation, strategie_id);
        IndicatorsDTO ind2 = convertToDto2(ind);
        return ind2;
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
        add.add(ind.getActive());
        add.add(ind.getReverse());
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

    public IndicatorsDTO convertToDto2(StrategiesIndicators ind) {
        try {
            IndicatorsDTO ind2 = new IndicatorsDTO();
            BeanUtils.copyProperties(ind2, ind);
            return ind2;
        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(IndicatorsService.class.getName()).log(Level.SEVERE, null, ex);
            return new IndicatorsDTO();
        }
    }

    private StrategiesIndicators getStrategiesIndicator(String abreviation, int strategie_id) {
        return strategiesIndicatorsRepository.findIndicatorsByStrategiesAndAbbreviation(abreviation, strategie_id);
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
