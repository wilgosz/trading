/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author zbigniewwilgosz
 */
@Entity
@Table(name = "remembered_companies", indexes = {
    @Index(name = "remembered_companies_abbr_idx", columnList = "abbreviation", unique = false),
    @Index(name = "remembered_companies_date_idx", columnList = "date", unique = false),
    @Index(name = "remembered_companies_user_idx", columnList = "user_id", unique = false)
})

public class RememberedComapnies implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "abbreviation", length = 32)
    private String abbreviation;
    
    @Column(name = "active")
    private Boolean active = false;
    
    
    @Column(name = "start_price")
    private double startPrice;
    
    @Column(name = "profit")
    private Double profit;
    
    @Column(name = "date", columnDefinition="DATE")
    private LocalDate date;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @OneToMany(mappedBy = "rememberedComapniesId")
    @JsonIgnore
    private List<RememberedComapniesData> rememberedComapniesList;

    public RememberedComapnies() {
    }

    public RememberedComapnies(Integer id) {
        this.id = id;
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
        if (!(object instanceof RememberedComapnies)) {
            return false;
        }
        RememberedComapnies other = (RememberedComapnies) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.easyweb24.actionbot.entity.RememberedComapnies[ id=" + id + " ]";
    }

    /**
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
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
     * @return the startPrice
     */
    public double getStartPrice() {
        return startPrice;
    }

    /**
     * @param startPrice the startPrice to set
     */
    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
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

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the rememberedComapniesList
     */
    public List<RememberedComapniesData> getRememberedComapniesList() {
        return rememberedComapniesList;
    }

    /**
     * @param rememberedComapniesList the rememberedComapniesList to set
     */
    public void setRememberedComapniesList(List<RememberedComapniesData> rememberedComapniesList) {
        this.rememberedComapniesList = rememberedComapniesList;
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
