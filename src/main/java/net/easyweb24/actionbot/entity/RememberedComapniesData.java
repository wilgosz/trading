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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author zbigniewwilgosz
 */
@Entity
@Table(name = "remembered_companies_data")

public class RememberedComapniesData implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "remembered_companies_id", referencedColumnName = "id")
    private RememberedComapnies rememberedComapniesId;
    
    @Column(name = "price")
    private double price;
    
    @Column(name = "date", columnDefinition="DATETIME")
    private LocalDateTime date;
    
    @Column(name = "profit")
    private Double profit;
    
    private Integer sell;
    private Integer neutral;
    private Integer buy;
    

    public RememberedComapniesData() {
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof RememberedComapniesData)) {
            return false;
        }
        RememberedComapniesData other = (RememberedComapniesData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.easyweb24.actionbot.entity.RememberedComapniesData[ id=" + id + " ]";
    }

    /**
     * @return the rememberedComapnies
     */
    public RememberedComapnies getRememberedComapniesId() {
        return rememberedComapniesId;
    }

    /**
     * @param rememberedComapnies the rememberedComapnies to set
     */
    public void setRememberedComapniesId(RememberedComapnies rememberedComapniesId) {
        this.rememberedComapniesId = rememberedComapniesId;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the date
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * @return the sell
     */
    public Integer getSell() {
        return sell;
    }

    /**
     * @param sell the sell to set
     */
    public void setSell(Integer sell) {
        this.sell = sell;
    }

    /**
     * @return the neutral
     */
    public Integer getNeutral() {
        return neutral;
    }

    /**
     * @param neutral the neutral to set
     */
    public void setNeutral(Integer neutral) {
        this.neutral = neutral;
    }

    /**
     * @return the buy
     */
    public Integer getBuy() {
        return buy;
    }

    /**
     * @param buy the buy to set
     */
    public void setBuy(Integer buy) {
        this.buy = buy;
    }

    /**
     * @return the profit
     */
    public Double getProfit() {
        return profit;
    }

    /**
     * @param profit the profit to set
     */
    public void setProfit(Double profit) {
        this.profit = profit;
    }

    
    
}
