/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author zbigniewwilgosz
 */
@Entity
@Table(name = "signals")
@NamedQueries({
    @NamedQuery(name = "Signals.findAll", query = "SELECT s FROM Signals s")})
public class Signals implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "signal_name")
    private String signalName;
    @Column(name = "datetime")
    private Long datetime;
    @JoinColumn(name = "alerts_id", referencedColumnName = "id")
    @ManyToOne
    private Alerts alertsId;

    public Signals() {
    }

    public Signals(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSignalName() {
        return signalName;
    }

    public void setSignal(String signalName) {
        this.signalName = signalName;
    }

    public Long getDatetime() {
        return datetime;
    }

    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }

    public Alerts getAlertsId() {
        return alertsId;
    }

    public void setAlertsId(Alerts alertsId) {
        this.alertsId = alertsId;
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
        if (!(object instanceof Signals)) {
            return false;
        }
        Signals other = (Signals) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.easyweb24.actionbot.entity.Signals[ id=" + id + " ]";
    }
    
}
