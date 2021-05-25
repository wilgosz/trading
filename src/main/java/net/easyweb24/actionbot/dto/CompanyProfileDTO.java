/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.dto;


public interface CompanyProfileDTO{

    public Integer getId();
    public String getAbbreviation();
    public String getCountry();
    public String getCurrency();
    public String getExchange();
    public String getFinnhubIndustry();
    public String getIpo();
    public String getLogo();
    public Integer getMarketCapitalization();
    public String getName();
    public String getPhone();
    public Float getShareOutstanding();
    public String getTicker();
    public String getWeburl();
    public String getSignals();
    public boolean getTrending();
    public boolean getActive();
}
