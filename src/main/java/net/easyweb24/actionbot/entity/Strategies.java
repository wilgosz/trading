/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author zbigniewwilgosz
 */
@Entity
@Table(name = "strategies", catalog = "trading", schema = "")
@NamedQueries({
    @NamedQuery(name = "Strategies.findAll", query = "SELECT s FROM Strategies s"),
    @NamedQuery(name = "Strategies.findById", query = "SELECT s FROM Strategies s WHERE s.id = :id"),
    @NamedQuery(name = "Strategies.findByName", query = "SELECT s FROM Strategies s WHERE s.name = :name")})
public class Strategies implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "name", length = 255)
    private String name;
    
    @Column(name = "time_range")
    private Integer timeRange;
    
    @Column(name = "support_time_range")
    private Integer supportTimeRange;
    
    @OneToMany(mappedBy = "strategiesId")
    @JsonIgnore
    private List<Alerts> alertsList;
    
    @OneToMany(mappedBy = "strategiesId")
    @JsonIgnore
    private List<StrategiesIndicators> strategiesIndicatorsList;
    
    @OneToMany(mappedBy = "strategiesId")
    @JsonIgnore
    private List<MpSignals> mpSignalsList;
    
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User user;

    public Strategies() {
    }

    public Strategies(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Alerts> getAlertsList() {
        return alertsList;
    }

    public void setAlertsList(List<Alerts> alertsList) {
        this.alertsList = alertsList;
    }

    public List<StrategiesIndicators> getStrategiesIndicatorsList() {
        return strategiesIndicatorsList;
    }

    public void setStrategiesIndicatorsList(List<StrategiesIndicators> strategiesIndicatorsList) {
        this.strategiesIndicatorsList = strategiesIndicatorsList;
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
        if (!(object instanceof Strategies)) {
            return false;
        }
        Strategies other = (Strategies) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.easyweb24.actionbot.entity.Strategies[ id=" + id + " ]";
    }

    /**
     * @return the timeRange
     */
    public Integer getTimeRange() {
        return timeRange;
    }

    /**
     * @param timeRange the timeRange to set
     */
    public void setTimeRange(Integer timeRange) {
        this.timeRange = timeRange;
    }

    /**
     * @return the supportTimeRange
     */
    public Integer getSupportTimeRange() {
        return supportTimeRange;
    }

    /**
     * @param supportTimeRange the supportTimeRange to set
     */
    public void setSupportTimeRange(Integer supportTimeRange) {
        this.supportTimeRange = supportTimeRange;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the mpSignalsList
     */
    public List<MpSignals> getMpSignalsList() {
        return mpSignalsList;
    }

    /**
     * @param mpSignalsList the mpSignalsList to set
     */
    public void setMpSignalsList(List<MpSignals> mpSignalsList) {
        this.mpSignalsList = mpSignalsList;
    }
    
}
