/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.easyweb24.actionbot.dto.AggregateIndicators;
import net.easyweb24.actionbot.entity.CompanyNews;
import net.easyweb24.actionbot.entity.CompanyProfile;
import net.easyweb24.actionbot.dto.OHLCDTO;
import net.easyweb24.actionbot.entity.OHLC;
import net.easyweb24.actionbot.entity.Symbols;
import net.easyweb24.actionbot.repository.CompanyNewsRepository;
import net.easyweb24.actionbot.repository.OHLCRepository;
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
    
    @Autowired
    private OHLCRepository ohlcRepository;

    public CompanyProfile convertToCompanyProfile(String jsonstring, String abbreviation) {
        JSONObject jsonObject = new JSONObject(jsonstring);

        CompanyProfile company = null;
        try {
            company = new CompanyProfile();
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
            company.setAbbreviation(abbreviation);
            company.setIpo(jsonObject.getString("ipo"));
            company.setWeburl(jsonObject.getString("weburl"));
        } catch (Exception e) {
            System.out.println(jsonObject);
            company = null;
        }

        return company;
    }

    public List<OHLCDTO> convertToOhlcList(String jsonstring) {
        JSONObject jsonObject = new JSONObject(jsonstring);
        List<OHLCDTO> OhlcList = new ArrayList();
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
                OHLCDTO ohlc = new OHLCDTO();
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
                symbols.setDescription(element.getString("description"));
                symbols.setExchangeAbbreviation("US");
                if (save) {
                    symbolsRepository.save(symbols);
                }
            }
            symbolsList.add(symbols);
        }
        return symbolsList;
    }

    public AggregateIndicators convertToAggregateIdicators(String jsonstring) {
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

    public List<CompanyNews> companyNews(List<CompanyNews> newsFromDB, boolean only3) {
        List<CompanyNews> companyNews = new ArrayList<CompanyNews>();
        Iterator it = newsFromDB.iterator();
        int i = 0;
        SimpleDateFormat sf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        while (it.hasNext()) {
            CompanyNews news = (CompanyNews) it.next();

            Date date = new Date(news.getDatetime() * 1000);
            news.setDatestring(sf.format(date));
            companyNews.add(news);
            if (only3) {
                if (i == 2) {
                    break;
                }
            }
            i++;
        }
        return companyNews;

    }

    public List<CompanyNews> saveAndReadNews(String symbol, CompanyNewsRepository companyNewsRepository, boolean only3) throws IOException {
        Long lastUpdatetime = companyNewsRepository.getLastUpdateTime(symbol);
        Long lastDateTime = companyNewsRepository.getLastNewsDateTime(symbol);
        Long currentTime = Instant.now().getEpochSecond();
        List<CompanyNews> news = new ArrayList<>();
        if (lastUpdatetime != null && Instant.now().getEpochSecond() - companyNewsRepository.getLastNewsDateTime(symbol) > (5 * 3600)) {
            news = companyNewsRepository.getNewsFromLastDay(symbol, (currentTime - (72 * 3600)));
            news = this.companyNews(news, only3);
            System.out.println("READ NEWS");
        } else {

            String aggregatejson = finnhubService.companyNews(symbol);
            news = this.companyNews(aggregatejson, symbol, false);
            Iterator it = news.iterator();
            while (it.hasNext()) {
                CompanyNews n = (CompanyNews) it.next();
                n.setLastupdate(currentTime);
                try {
                    if(lastUpdatetime == null || n.getDatetime()>lastDateTime){
                        companyNewsRepository.save(n);
                    }
                } catch (Exception e) {
                    System.out.println("Can't save news");
                }
            }
            news = companyNewsRepository.getNewsFromLastDay(symbol, (currentTime - (72 * 3600)));
            news = this.companyNews(news, only3);
            System.out.println("ADD NEWS");
        }
        return news;
    }

    public List<CompanyNews> companyNews(String jsonstring, String symbol, boolean only3) {
        List<CompanyNews> companyNews = new ArrayList<CompanyNews>();
        JSONArray newsArray = new JSONArray(jsonstring);
        for (int i = 0; i < newsArray.length(); i++) {
            CompanyNews news = new CompanyNews();
            news.setAbbreviation(symbol);
            news.setDatetime(((JSONObject) newsArray.get(i)).getLong("datetime"));
            news.setHeadline(((JSONObject) newsArray.get(i)).getString("headline"));
            news.setNewsId(((JSONObject) newsArray.get(i)).getLong("id"));
            news.setImage(((JSONObject) newsArray.get(i)).getString("image"));
            news.setSource(((JSONObject) newsArray.get(i)).getString("source"));
            news.setSummary(((JSONObject) newsArray.get(i)).getString("summary"));
            news.setUrl(((JSONObject) newsArray.get(i)).getString("url"));

            SimpleDateFormat sf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            Date date = new Date(((JSONObject) newsArray.get(i)).getLong("datetime") * 1000);
            news.setDatestring(sf.format(date));
            companyNews.add(news);
            if (only3) {
                if (i == 2) {
                    break;
                }
            }
        }
        return companyNews;

    }
    
    public boolean initSaveOHLC(String jsonstring, String abbreviation){
        int counter = ohlcRepository.getSymbolExists(abbreviation);
        
        if(counter == 0 ){
            
            List<OHLCDTO> ohlcList = this.convertToOhlcList(jsonstring);
            Iterator itr = ohlcList.iterator();
            while(itr.hasNext()){
                OHLCDTO ohlcdto = (OHLCDTO) itr.next();
                OHLC ohlc = new OHLC();
                ohlc.setAbbreviation(abbreviation);
                ohlc.setC(ohlcdto.getClose());
                ohlc.setH(ohlcdto.getHigh());
                ohlc.setL(ohlcdto.getLow());
                ohlc.setO(ohlcdto.getOpen());
                ohlc.setV(ohlcdto.getValue());
                ohlc.setDate(LocalDate.parse(ohlcdto.getTime()));
                ohlcRepository.save(ohlc);
            } 
        }
        
        return true;
    }
    
}
