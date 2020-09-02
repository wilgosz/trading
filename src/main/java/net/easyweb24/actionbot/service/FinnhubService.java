/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.service;

import java.io.IOException;
import net.easyweb24.actionbot.components.FinnhubComponent;
import net.easyweb24.actionbot.components.utils.BEFORE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author zbigniewwilgosz
 */
@Service
public class FinnhubService {
    
    @Autowired
    FinnhubComponent finnhubComponent; 
    
    public String companyProfile(String abbreviation) throws IOException{
        String url = "/stock/profile2?symbol=" + abbreviation;
        return finnhubComponent.readURLToString(url);
    }
    
    public String companySymbols(String exchange) throws IOException{
        String url = "/stock/symbol?exchange=" + exchange;
        return finnhubComponent.readURLToString(url);
    }
    
    public String companyNews(String abbreviation) throws IOException{
        String url = "/company-news?symbol=" + abbreviation + finnhubComponent.getDateFromTo(BEFORE.Week);
        return finnhubComponent.readURLToString(url);
    }
    
    public String basicFinancials(String abbreviation) throws IOException{
        String url = "/stock/metric?symbol=" + abbreviation + "&metric=price";
        return finnhubComponent.readURLToString(url);
    }
    
    public String news() throws IOException{
        String url = "/news?category=general";
        return finnhubComponent.readURLToString(url);
    }
    
    public String quote(String abbreviation) throws IOException{
        String url = "/quote?symbol=" + abbreviation;
        return finnhubComponent.readURLToString(url);
    }
    
    public String recommendationTrends(String abbreviation) throws IOException{
        String url = "/stock/recommendation?symbol=" + abbreviation;
        return finnhubComponent.readURLToString(url);
    }
    
    public String earningsSurprises(String abbreviation) throws IOException{
        String url = "/stock/earnings?symbol=" + abbreviation;
        return finnhubComponent.readURLToString(url);
    }
    
    /**
     * 
     * @param abbreviation
     * @param resolution 1, 5, 15, 30, 60, D, W, M
     * @param from
     * @return
     * @throws IOException 
     */
    public String stockCandles(String abbreviation, String resolution, BEFORE from) throws IOException{
        String url = "/stock/candle?symbol=" + abbreviation + "&resolution=" + resolution + finnhubComponent.getMilisecFromTo(from);
        return finnhubComponent.readURLToString(url);
    }
    
    public String stockCandlesFromLastYear(String abbreviation) throws IOException{
        return stockCandles(abbreviation, "D", BEFORE.Year);
    }
    
    public String stockCandlesFromLastWeek(String abbreviation) throws IOException{
        return stockCandles(abbreviation, "D", BEFORE.Week);
    }
    
    public String stockCandlesFromWeekPerHour(String abbreviation) throws IOException{
        return stockCandles(abbreviation, "60", BEFORE.Week);
    }
    
    public String stockCandlesFromDayPer5Min(String abbreviation) throws IOException{
        return stockCandles(abbreviation, "5", BEFORE.Day);
    }
    /**
     * 
     * @param abbreviation
     * @return
     * @throws IOException 
     */
    public String patternRecognitionPerDay(String abbreviation) throws IOException{
        return patternRecognition(abbreviation, "D");
    }
    
    public String patternRecognitionPerHour(String abbreviation) throws IOException{
        return patternRecognition(abbreviation, "60");
    }
    
    public String patternRecognitionPer15Min(String abbreviation) throws IOException{
        return patternRecognition(abbreviation, "15");
    }
    
    public String patternRecognitionPer5Min(String abbreviation) throws IOException{
        return patternRecognition(abbreviation, "5");
    }
    /**
     * 
     * @param abbreviation
     * @param resolution 1, 5, 15, 30, 60, D, W, M
     * @return
     * @throws IOException 
     */
    public String patternRecognition(String abbreviation, String resolution) throws IOException{
        String url = "/scan/pattern?symbol=" + abbreviation + "&resolution=" + resolution;
        return finnhubComponent.readURLToString(url);
    }
    
    
    /**
     * 
     * @param abbreviation
     * @param resolution 1, 5, 15, 30, 60, D, W, M
     * @return
     * @throws IOException 
     */
    public String supportResistance(String abbreviation, String resolution) throws IOException{
        String url = "/scan/support-resistance?symbol=" + abbreviation + "&resolution=" + resolution;
        return finnhubComponent.readURLToString(url);
    }
    
    public String supportResistancePerDay(String abbreviation) throws IOException{
        return supportResistance(abbreviation, "D");
    }
    
    public String supportResistancePerHour(String abbreviation) throws IOException{
        return supportResistance(abbreviation, "60");
    }
    
    public String supportResistancePer15min(String abbreviation) throws IOException{
        return supportResistance(abbreviation, "15");
    }
    
    public String supportResistancePer5min(String abbreviation) throws IOException{
        return supportResistance(abbreviation, "5");
    }
    
    /**
     * 
     * @param abbreviation
     * @param resolution 1, 5, 15, 30, 60, D, W, M
     * @return
     * @throws IOException 
     */
    public String aggregateIndicators(String abbreviation, String resolution) throws IOException{
        String url = "/scan/technical-indicator?symbol=" + abbreviation + "&resolution=" + resolution;
        return finnhubComponent.readURLToString(url);
    }
    
    public String aggregateIndicatorsPerDay(String abbreviation) throws IOException{
        return aggregateIndicators(abbreviation, "D");
    }
}


