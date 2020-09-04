/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.components.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import net.easyweb24.actionbot.entity.OHLC;
import net.easyweb24.actionbot.repository.OHLCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

@Component
public class DBToBarSeries {
    
    @Autowired
    OHLCRepository ohlcRepository;
    
    public static String DateBeforeYear(){
        
        Date today = new Date();
        Long beforeYear = (today.getTime() - (31536000000L));
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(beforeYear);
        return sf.format(date);
    }
    
    public BarSeries getBars(String abbreviation){
        
        BarSeries series = new BaseBarSeries(abbreviation);
        List<OHLC> dbseries = ohlcRepository.getOHLCFromLastYear(abbreviation, DBToBarSeries.DateBeforeYear());
        for(OHLC bar: dbseries){
                ZonedDateTime date = bar.getDate().atStartOfDay(ZoneId.of("Europe/Berlin"));
                double open = bar.getO();
                double high = bar.getH();
                double low = bar.getL();
                double close = bar.getC();
                double volume = bar.getV();
            series.addBar(date, open, high, low, close, volume);
        }
        return series;
    }
}