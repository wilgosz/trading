/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
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
import javax.validation.constraints.Size;

/**
 *
 * @author zbigniewwilgosz
 */
@Entity
@Table(name = "mp_signals", indexes = {
    @Index(name = "mp_signals_abbreviation_idx", columnList = "abbreviation", unique = false),
    @Index(name = "mp_signals_update_date_time_idx", columnList = "update_date_time", unique = false)
})

public class MpSignals implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 16)
    @Column(name = "abbreviation")
    private String abbreviation;
    
    @Column(name = "create_date_time", columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP")
    public LocalDateTime createDateTime;
 
    @Column(name = "update_date_time", columnDefinition="DATETIME ON UPDATE CURRENT_TIMESTAMP")
    public LocalDateTime updateDateTime;
    
    @JoinColumn(name = "strategies_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Strategies strategiesId;
    
    private Integer buy;
    private Integer neutral;
    private Integer sell;
    

    public MpSignals() {
    }

    public MpSignals(Integer id) {
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
        this.abbreviation = abbreviation;
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
        if (!(object instanceof MpSignals)) {
            return false;
        }
        MpSignals other = (MpSignals) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.easyweb24.actionbot.entity.MpSignals[ id=" + id + " ]";
    }

    /**
     * @return the createDateTime
     */
    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    /**
     * @param createDateTime the createDateTime to set
     */
    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    /**
     * @return the updateDateTime
     */
    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    /**
     * @param updateDateTime the updateDateTime to set
     */
    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
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
     * @return the strategiesId
     */
    public Strategies getStrategiesId() {
        return strategiesId;
    }

    /**
     * @param strategiesId the strategiesId to set
     */
    public void setStrategiesId(Strategies strategiesId) {
        this.strategiesId = strategiesId;
    }
    
}