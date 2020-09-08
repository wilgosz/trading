/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.service;

import net.easyweb24.actionbot.indicators.MACD;
import org.springframework.stereotype.Service;
import org.ta4j.core.BarSeries;

@Service
public class IndicatorsService {
    
    public MACD getMACD(BarSeries series){
        MACD macd = new MACD(series, 26, 9);
        return macd;
    }
}
