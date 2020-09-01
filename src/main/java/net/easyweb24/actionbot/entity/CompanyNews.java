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
import javax.persistence.Transient;

@Entity
@Table(name = "company_news", indexes = {
    @Index(name = "finnhub_company_news_abbr_idx", columnList = "abbreviation", unique = false),
    @Index(name = "finnhub_company_news_news_id_idx", columnList = "news_id", unique = false),
    @Index(name = "finnhub_company_news_datetime_idx", columnList = "datetime", unique = false)
})
@NamedQueries({
    @NamedQuery(name = "CompanyNews.findAll", query = "SELECT c FROM CompanyNews c")})

public class CompanyNews implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "news_id", nullable = false)
    private Long newsId;
    
    private Long datetime;
    
    @Transient
    private String datestring;
    
    private String headline;
    
    @Column(name="image", columnDefinition = "VARCHAR(512)")
    private String image;
    
    private String abbreviation;
    
    private String source;
    
    @Column(columnDefinition = "TEXT")
    private String summary;
    
    private String url;
    
    private Long lastupdate;

    /**
     * @return the datetime
     */
    public Long getDatetime() {
        return datetime;
    }

    /**
     * @param datetime the datetime to set
     */
    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }

    /**
     * @return the headline
     */
    public String getHeadline() {
        return headline;
    }

    /**
     * @param headline the headline to set
     */
    public void setHeadline(String headline) {
        this.headline = headline;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
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

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary the summary to set
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the news_id
     */
    public Long getNewsId() {
        return newsId;
    }

    /**
     * @param news_id the news_id to set
     */
    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompanyNews)) {
            return false;
        }
        CompanyNews other = (CompanyNews) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.easyweb24.actionbot.entity.CompanyNews[ id=" + id + " ]";
    }

    /**
     * @return the datestring
     */
    public String getDatestring() {
        return datestring;
    }

    /**
     * @param datestring the datestring to set
     */
    public void setDatestring(String datestring) {
        this.datestring = datestring;
    }

    /**
     * @return the lastupdate
     */
    public Long getLastupdate() {
        return lastupdate;
    }

    /**
     * @param lastupdate the lastupdate to set
     */
    public void setLastupdate(Long lastupdate) {
        this.lastupdate = lastupdate;
    }
}
