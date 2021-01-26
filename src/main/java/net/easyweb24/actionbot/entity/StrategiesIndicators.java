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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author zbigniewwilgosz
 */
@Entity
@Table(name = "strategies_indicators", indexes = {
    @Index(name = "strategies_indicators_abbr_idx", columnList = "indicators_abbreviation", unique = false)
})
@NamedQueries({
    @NamedQuery(name = "StrategiesIndicators.findAll", query = "SELECT s FROM StrategiesIndicators s"),
    @NamedQuery(name = "StrategiesIndicators.findById", query = "SELECT s FROM StrategiesIndicators s WHERE s.id = :id"),
    @NamedQuery(name = "StrategiesIndicators.findByIndicatorsAbbreviation", query = "SELECT s FROM StrategiesIndicators s WHERE s.indicatorsAbbreviation = :indicatorsAbbreviation"),
    @NamedQuery(name = "StrategiesIndicators.findByPeriodShort", query = "SELECT s FROM StrategiesIndicators s WHERE s.periodShort = :periodShort"),
    @NamedQuery(name = "StrategiesIndicators.findByPeriodLong", query = "SELECT s FROM StrategiesIndicators s WHERE s.periodLong = :periodLong"),
    @NamedQuery(name = "StrategiesIndicators.findByPeriod", query = "SELECT s FROM StrategiesIndicators s WHERE s.period = :period"),
    @NamedQuery(name = "StrategiesIndicators.findByTopBorder", query = "SELECT s FROM StrategiesIndicators s WHERE s.topBorder = :topBorder"),
    @NamedQuery(name = "StrategiesIndicators.findByBottomBorder", query = "SELECT s FROM StrategiesIndicators s WHERE s.bottomBorder = :bottomBorder"),
    @NamedQuery(name = "StrategiesIndicators.findByTimeRange", query = "SELECT s FROM StrategiesIndicators s WHERE s.timeRange = :timeRange")})
public class StrategiesIndicators implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "indicators_abbreviation", length = 32)
    private String indicatorsAbbreviation;
    @Column(name = "period_short")
    private Integer periodShort;
    @Column(name = "period_long")
    private Integer periodLong;
    @Column(name = "period")
    private Integer period;
    @Column(name = "top_border")
    private Integer topBorder;
    @Column(name = "bottom_border")
    private Integer bottomBorder;
    @Column(name = "time_range")
    private Integer timeRange;
    @Column(name = "active")
    private Boolean active = false;
    @Column(name = "reverse")
    private Boolean reverse = false;
    @JoinColumn(name = "strategies_id", referencedColumnName = "id")
    @ManyToOne
    private Strategies strategiesId;

    public StrategiesIndicators() {
    }

    public StrategiesIndicators(Integer id) {
        this.id = id;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIndicatorsAbbreviation() {
        return indicatorsAbbreviation;
    }

    public void setIndicatorsAbbreviation(String indicatorsAbbreviation) {
        this.indicatorsAbbreviation = indicatorsAbbreviation;
    }

    public Integer getPeriodShort() {
        return periodShort;
    }

    public void setPeriodShort(Integer periodShort) {
        this.periodShort = periodShort;
    }

    public Integer getPeriodLong() {
        return periodLong;
    }

    public void setPeriodLong(Integer periodLong) {
        this.periodLong = periodLong;
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

    public Integer getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(Integer timeRange) {
        this.timeRange = timeRange;
    }

    public Strategies getStrategiesId() {
        return strategiesId;
    }

    public void setStrategiesId(Strategies strategiesId) {
        this.strategiesId = strategiesId;
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
        if (!(object instanceof StrategiesIndicators)) {
            return false;
        }
        StrategiesIndicators other = (StrategiesIndicators) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.easyweb24.actionbot.entity.StrategiesIndicators[ id=" + id + " ]";
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
     * @return the reverse
     */
    public Boolean getReverse() {
        return reverse;
    }

    /**
     * @param reverse the reverse to set
     */
    public void setReverse(Boolean reverse) {
        this.reverse = reverse;
    }
    
}
