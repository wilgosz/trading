/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.components;

import io.micrometer.core.instrument.util.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.easyweb24.actionbot.components.utils.BEFORE;
import net.easyweb24.actionbot.entity.CompanyProfile;
import net.easyweb24.actionbot.entity.Symbols;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author zbigniewwilgosz
 */
@Component
public class FinnhubComponent {

    @Value("${finnhub.url}")
    private String finnhubUrl;

    @Value("${finnhub.token}")
    private String finnhubToken;

    /**
     *
     * @param url
     * @return
     * @throws IOException
     */
    public String readURLToString(String url) throws IOException {

        String fullUrl = finnhubUrl + url + finnhubToken;
        System.out.println(fullUrl);
        try (InputStream inputStream = new URL(fullUrl).openStream()) {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }
    }

    public String getDate(BEFORE before) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(getMilisec(before));
        return formatter.format(date);
    }
    
    public String getDateFromTo(BEFORE before){
        StringBuilder fromto = new StringBuilder("&from=");
        fromto.append(getDate(before)); 
        fromto.append("&to=");
        fromto.append(getDate(BEFORE.Today));
        return fromto.toString();
    }
    
    public String getMilisecFromTo(BEFORE before){
        StringBuilder fromto = new StringBuilder("&from=");
        fromto.append((getMilisec(before)/1000)); 
        fromto.append("&to=");
        fromto.append((getMilisec(BEFORE.Today)/1000));
        return fromto.toString();
    }
    
    public Long getMilisec(BEFORE before){
        Long timeBefore = 0L;
        switch (before) {
            case Day:
                timeBefore = (60L*60L*24L*1000L);
                break;
            case Week:
                timeBefore = (60L*60L*24L*1000L*7L);
                break;
            case Month:
                timeBefore = (60L*60L*24L*1000L*30L);
                break;
            case Year:
                timeBefore = (60L*60L*24L*1000L*365L);
                break;
        }
        
        return (System.currentTimeMillis() - timeBefore);
    }
    
    public CompanyProfile dummyCompany(Symbols symbols){
        CompanyProfile company = new CompanyProfile();
                company.setCountry("Unknow");
                company.setCurrency("Unknow");
                company.setExchange("Unknow");
                company.setLogo("/images/logo-text.png");
                company.setWeburl("Unknow");
                company.setFinnhubIndustry("Unknow");
                company.setMarketCapitalization(0);
                company.setName(symbols.getDescription());
                return company;
    }
}
