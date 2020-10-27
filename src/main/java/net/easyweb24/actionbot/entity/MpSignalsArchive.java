/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author zbigniewwilgosz
 */
@Entity
@Table(name = "mp_signals_archive", indexes = {
    @Index(name = "mp_signals_archive_abbreviation_idx", columnList = "abbreviation", unique = false),
    @Index(name = "mp_signals_archive_date_idx", columnList = "date", unique = false)
})

public class MpSignalsArchive implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 16)
    @Column(name = "abbreviation")
    private String abbreviation;
    
    @Column(name = "date", columnDefinition="DATE")
    private LocalDate date;
    
    private Integer buy;
    private Integer neutral;
    private Integer sell;
    

    public MpSignalsArchive() {
    }

    public MpSignalsArchive(Integer id) {
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
        if (!(object instanceof MpSignalsArchive)) {
            return false;
        }
        MpSignalsArchive other = (MpSignalsArchive) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.easyweb24.actionbot.entity.MpSignalsArchive[ id=" + id + " ]";
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
