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
@Table(name = "finnhub_signals", indexes = {
    @Index(name = "finnhub_signals_abbreviation_idx", columnList = "abbreviation", unique = false),
    @Index(name = "finnhub_signals_signals_idx", columnList = "signals", unique = false),
    @Index(name = "finnhub_signals_buy_idx", columnList = "buy", unique = false),
    @Index(name = "finnhub_signals_neutral_idx", columnList = "neutral", unique = false),
    @Index(name = "finnhub_signals_sell_idx", columnList = "sell", unique = false)
})
@NamedQueries({
    @NamedQuery(name = "FinnhubSignals.findAll", query = "SELECT f FROM FinnhubSignals f")})
public class FinnhubSignals implements Serializable {

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

    public FinnhubSignals() {
    }

    public FinnhubSignals(Integer id) {
        this.id = id;
    }

    public FinnhubSignals(Integer id, boolean trending) {
        this.id = id;
        this.trending = trending;
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
        this.abbreviation = abbreviation;
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
        this.signals = signals;
    }

    public Double getAdx() {
        return adx;
    }

    public void setAdx(Double adx) {
        this.adx = adx;
    }

    public boolean getTrending() {
        return trending;
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
        if (!(object instanceof FinnhubSignals)) {
            return false;
        }
        FinnhubSignals other = (FinnhubSignals) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.easyweb24.actionbot.entity.FinnhubSignals[ id=" + id + " ]";
    }
    
}
