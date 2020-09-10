/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.service;

import net.easyweb24.actionbot.indicators.BOLLINGER;
import net.easyweb24.actionbot.indicators.MACD;
import net.easyweb24.actionbot.indicators.MONEYFLOW;
import net.easyweb24.actionbot.indicators.RSI;
import net.easyweb24.actionbot.indicators.SMA;
import net.easyweb24.actionbot.indicators.STOCHASTIC;
import org.springframework.stereotype.Service;
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
}
