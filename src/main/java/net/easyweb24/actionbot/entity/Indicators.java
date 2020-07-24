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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author zbigniewwilgosz
 */
@Entity
@Table(name = "indicators", catalog = "trading", schema = "")
@NamedQueries({
    @NamedQuery(name = "Indicators.findAll", query = "SELECT i FROM Indicators i"),
    @NamedQuery(name = "Indicators.findById", query = "SELECT i FROM Indicators i WHERE i.id = :id"),
    @NamedQuery(name = "Indicators.findByAbbreviation", query = "SELECT i FROM Indicators i WHERE i.abbreviation = :abbreviation"),
    @NamedQuery(name = "Indicators.findByPeriodLong", query = "SELECT i FROM Indicators i WHERE i.periodLong = :periodLong"),
    @NamedQuery(name = "Indicators.findByPeriodShort", query = "SELECT i FROM Indicators i WHERE i.periodShort = :periodShort"),
    @NamedQuery(name = "Indicators.findByPeriod", query = "SELECT i FROM Indicators i WHERE i.period = :period"),
    @NamedQuery(name = "Indicators.findByTopBorder", query = "SELECT i FROM Indicators i WHERE i.topBorder = :topBorder"),
    @NamedQuery(name = "Indicators.findByBottomBorder", query = "SELECT i FROM Indicators i WHERE i.bottomBorder = :bottomBorder")})
public class Indicators implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "abbreviation", length = 32)
    private String abbreviation;
    @Column(name = "period_long")
    private Integer periodLong;
    @Column(name = "period_short")
    private Integer periodShort;
    @Column(name = "period")
    private Integer period;
    @Column(name = "top_border")
    private Integer topBorder;
    @Column(name = "bottom_border")
    private Integer bottomBorder;

    public Indicators() {
    }

    public Indicators(Integer id) {
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

    public Integer getPeriodLong() {
        return periodLong;
    }

    public void setPeriodLong(Integer periodLong) {
        this.periodLong = periodLong;
    }

    public Integer getPeriodShort() {
        return periodShort;
    }

    public void setPeriodShort(Integer periodShort) {
        this.periodShort = periodShort;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getTopBorder() {
        return topBorder;
    }

    public void setTopBorder(Integer topBorder) {
        this.topBorder = topBorder;
    }

    public Integer getBottomBorder() {
        return bottomBorder;
    }

    public void setBottomBorder(Integer bottomBorder) {
        this.bottomBorder = bottomBorder;
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
        if (!(object instanceof Indicators)) {
            return false;
        }
        Indicators other = (Indicators) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.easyweb24.actionbot.entity.Indicators[ id=" + id + " ]";
    }
    
}
