/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author zbigniewwilgosz
 */
@Entity
@Table(name = "finnhub_signals_archive", indexes = {
    @Index(name = "finnhub_signals_archive_abbreviation_idx", columnList = "abbreviation", unique = false),
    @Index(name = "finnhub_signals_archive_signals_idx", columnList = "signals", unique = false),
    @Index(name = "finnhub_signals_archive_buy_idx", columnList = "buy", unique = false),
    @Index(name = "finnhub_signals_archive_neutral_idx", columnList = "neutral", unique = false),
    @Index(name = "finnhub_signals_archive_sell_idx", columnList = "sell", unique = false),
    @Index(name = "finnhub_signals_archive_date_idx", columnList = "date", unique = false)
})
@NamedQueries({
    @NamedQuery(name = "FinnhubSignalsArchive.findAll", query = "SELECT f FROM FinnhubSignalsArchive f")})
public class FinnhubSignalsArchive implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 16)
    @Column(name = "abbreviation")
    private String abbreviation;
    
    @Column(name = "buy")
    private Integer buy;
    
    @Column(name = "neutral")
    private Integer neutral;
    
    @Column(name = "sell")
    private Integer sell;
    
    @Size(max = 16)
    @Column(name = "signals")
    private String signals;
    
    //@Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "adx")
    private Double adx;
    
    @Basic(optional = false)
    @Column(name = "trending")
    private boolean trending;
    
    @Column(name = "date", columnDefinition="DATE")
    private LocalDate date;

    public FinnhubSignalsArchive() {
    }

    public FinnhubSignalsArchive(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.setAbbreviation(abbreviation);
    }

    public Integer getBuy() {
        return buy;
    }

    public void setBuy(Integer buy) {
        this.buy = buy;
    }

    public Integer getNeutral() {
        return neutral;
    }

    public void setNeutral(Integer neutral) {
        this.neutral = neutral;
    }

    public Integer getSell() {
        return sell;
    }

    public void setSell(Integer sell) {
        this.sell = sell;
    }

    public String getSignals() {
        return signals;
    }

    public void setSignals(String signals) {
        this.setSignals(signals);
    }

    public Double getAdx() {
        return adx;
    }

    public void setAdx(Double adx) {
        this.adx = adx;
    }

    public boolean getTrending() {
        return isTrending();
    }

    public void setTrending(boolean trending) {
        this.trending = trending;
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
        if (!(object instanceof FinnhubSignalsArchive)) {
            return false;
        }
        FinnhubSignalsArchive other = (FinnhubSignalsArchive) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.easyweb24.actionbot.entity.FinnhubSignalsArchive[ id=" + id + " ]";
    }
    
    /**
     * @return the trending
     */
    public boolean isTrending() {
        return trending;
    }

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
}
