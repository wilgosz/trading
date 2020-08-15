/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author zbigniewwilgosz
 */
@Entity
@Table(name = "company_profile", indexes = {
    @Index(name = "company_profile_abbreviation_idx", columnList = "abbreviation", unique = false)
})
@NamedQueries({
    @NamedQuery(name = "CompanyProfile.findAll", query = "SELECT c FROM CompanyProfile c")})

public class CompanyProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Size(max = 16)
    @Column(name = "abbreviation")
    public String abbreviation;
    
    private String country;
    private String currency;
    private String exchange;
    private String ipo;

    @Column(name = "market_capitalization")
    private int marketCapitalization;
    private String name;
    private String phone;
    @Column(name = "share_outstanding")
    private float shareOutstanding;
    private String ticker;
    private String weburl;
    private String logo;
    @Column(name = "finnhub_industry")
    private String finnhubIndustry;
    
    
    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return the exchange
     */
    public String getExchange() {
        return exchange;
    }

    /**
     * @param exchange the exchange to set
     */
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    /**
     * @return the ipo
     */
    public String getIpo() {
        return ipo;
    }

    /**
     * @param ipo the ipo to set
     */
    public void setIpo(String ipo) {
        this.ipo = ipo;
    }

    /**
     * @return the marketCapitalization
     */
    public int getMarketCapitalization() {
        return marketCapitalization;
    }

    /**
     * @param marketCapitalization the marketCapitalization to set
     */
    public void setMarketCapitalization(int marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the shareOutstanding
     */
    public float getShareOutstanding() {
        return shareOutstanding;
    }

    /**
     * @param shareOutstanding the shareOutstanding to set
     */
    public void setShareOutstanding(float shareOutstanding) {
        this.shareOutstanding = shareOutstanding;
    }

    /**
     * @return the ticker
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * @param ticker the ticker to set
     */
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    /**
     * @return the weburl
     */
    public String getWeburl() {
        return weburl;
    }

    /**
     * @param weburl the weburl to set
     */
    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    /**
     * @return the logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * @param logo the logo to set
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * @return the finnhubIndustry
     */
    public String getFinnhubIndustry() {
        return finnhubIndustry;
    }

    /**
     * @param finnhubIndustry the finnhubIndustry to set
     */
    public void setFinnhubIndustry(String finnhubIndustry) {
        this.finnhubIndustry = finnhubIndustry;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * @param abbreviation the abbreviation to set
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

}
