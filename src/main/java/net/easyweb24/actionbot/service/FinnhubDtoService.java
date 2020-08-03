/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.easyweb24.actionbot.dto.AggregateIndicators;
import net.easyweb24.actionbot.entity.CompanyNews;
import net.easyweb24.actionbot.dto.CompanyProfile;
import net.easyweb24.actionbot.dto.OHLC;
import net.easyweb24.actionbot.entity.Symbols;
import net.easyweb24.actionbot.repository.SymbolsRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author zbigniewwilgosz
 */
@Service
public class FinnhubDtoService {

    @Autowired
    private FinnhubService finnhubService;

    @Autowired
    private SymbolsRepository symbolsRepository;

    public CompanyProfile convertToCompanyProfile(String jsonstring) {
        JSONObject jsonObject = new JSONObject(jsonstring);

        CompanyProfile company = new CompanyProfile();
        company.setCountry(jsonObject.getString("country"));
        company.setCurrency(jsonObject.getString("currency"));
        company.setExchange(jsonObject.getString("exchange"));
        company.setFinnhubIndustry(jsonObject.getString("finnhubIndustry"));
        company.setLogo(jsonObject.getString("logo"));
        company.setMarketCapitalization(jsonObject.getInt("marketCapitalization"));
        company.setName(jsonObject.getString("name"));
        company.setPhone(jsonObject.getString("phone"));
        company.setShareOutstanding(jsonObject.getFloat("shareOutstanding"));
        company.setTicker(jsonObject.getString("ticker"));
        company.setWeburl(jsonObject.getString("weburl"));

        return company;
    }

    public List<OHLC> convertToOhlcList(String jsonstring) {
        JSONObject jsonObject = new JSONObject(jsonstring);
        List<OHLC> OhlcList = new ArrayList();
        if (jsonObject.getString("s").equals("ok")) {
            JSONArray close = jsonObject.getJSONArray("c");
            JSONArray high = jsonObject.getJSONArray("h");
            JSONArray low = jsonObject.getJSONArray("l");
            JSONArray open = jsonObject.getJSONArray("o");
            JSONArray time = jsonObject.getJSONArray("t");
            JSONArray value = jsonObject.getJSONArray("v");

            int size = close.length();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            for (int i = 0; i < size; i++) {
                OHLC ohlc = new OHLC();
                ohlc.setClose(close.getDouble(i));
                ohlc.setHigh(high.getDouble(i));
                ohlc.setLow(low.getDouble(i));
                ohlc.setOpen(open.getDouble(i));

                Date date = new Date(time.getLong(i) * 1000);
                ohlc.setTime(formatter.format(date));
                ohlc.setValue(value.getInt(i));
                OhlcList.add(ohlc);
            }
        }
        return OhlcList;
    }

    public List<Symbols> convertToSymbolList(String jsonstring, boolean save) {

        JSONArray jsonObject = new JSONArray(jsonstring);
        Iterator itr = jsonObject.iterator();
        List<Symbols> symbolsList = new ArrayList<Symbols>();

        while (itr.hasNext()) {
            JSONObject element = (JSONObject) itr.next();
            Symbols symbols = this.symbolsRepository.findByAbbreviation(element.getString("symbol"));
            if (symbols == null) {
                symbols = new Symbols();
                symbols.setAbbreviation(element.getString("symbol"));
                symbols.setDisplayName(element.getString("displaySymbol"));
                symbols.setDesription(element.getString("description"));
                symbols.setExchangeAbbreviation("US");
                if (save) {
                    symbolsRepository.save(symbols);
                }
            }
            symbolsList.add(symbols);
        }
        return symbolsList;
    }
    
    public AggregateIndicators convertToAggregateIdicators(String jsonstring){
        AggregateIndicators aggregate = new AggregateIndicators();
        JSONObject aggregatelAnalysis = new JSONObject(jsonstring);
        aggregate.setAdx(aggregatelAnalysis.getJSONObject("trend").getDouble("adx"));
        aggregate.setBuy(aggregatelAnalysis.getJSONObject("technicalAnalysis").getJSONObject("count").getInt("buy"));
        aggregate.setNeutral(aggregatelAnalysis.getJSONObject("technicalAnalysis").getJSONObject("count").getInt("neutral"));
        aggregate.setSell(aggregatelAnalysis.getJSONObject("technicalAnalysis").getJSONObject("count").getInt("sell")); 
        aggregate.setSignal(aggregatelAnalysis.getJSONObject("technicalAnalysis").getString("signal")); 
        aggregate.setTrending(aggregatelAnalysis.getJSONObject("trend").getBoolean("trending"));
        
        return aggregate;
    }
    
    public List<CompanyNews> companyNews(String jsonstring, boolean only3){
        List<CompanyNews> companyNews = new ArrayList<CompanyNews>();
        JSONArray newsArray = new JSONArray(jsonstring);
        for(int i=0; i<newsArray.length(); i++){
            CompanyNews news = new CompanyNews();
            news.setAbbreviation(((JSONObject)newsArray.get(i)).getString("related"));
            news.setDatetime(((JSONObject)newsArray.get(i)).getLong("datetime"));
            news.setHeadline(((JSONObject)newsArray.get(i)).getString("headline"));
            news.setNewsId(((JSONObject)newsArray.get(i)).getLong("id"));
            news.setImage(((JSONObject)newsArray.get(i)).getString("image"));
            news.setSource(((JSONObject)newsArray.get(i)).getString("source"));
            news.setSummary(((JSONObject)newsArray.get(i)).getString("summary"));
            news.setUrl(((JSONObject)newsArray.get(i)).getString("url"));
            companyNews.add(news);
            if(only3){
                if(i==2){
                    break;
                }
            }
        }
        return companyNews;
        
    }
}
