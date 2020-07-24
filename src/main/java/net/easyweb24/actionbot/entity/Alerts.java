/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.entity;

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
@Table(name = "alerts", catalog = "trading", schema = "")
@NamedQueries({
    @NamedQuery(name = "Alerts.findAll", query = "SELECT a FROM Alerts a"),
    @NamedQuery(name = "Alerts.findById", query = "SELECT a FROM Alerts a WHERE a.id = :id"),
    @NamedQuery(name = "Alerts.findByActive", query = "SELECT a FROM Alerts a WHERE a.active = :active")})
public class Alerts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "active", nullable = false)
    private int active;
    @JoinColumn(name = "strategies_id", referencedColumnName = "id")
    @ManyToOne
    private Strategies strategiesId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User userId;
    @OneToMany(mappedBy = "alertsId")
    private List<Signals> signalsList;

    public Alerts() {
    }

    public Alerts(Integer id) {
        this.id = id;
    }

    public Alerts(Integer id, int active) {
        this.id = id;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Strategies getStrategiesId() {
        return strategiesId;
    }

    public void setStrategiesId(Strategies strategiesId) {
        this.strategiesId = strategiesId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public List<Signals> getSignalsList() {
        return signalsList;
    }

    public void setSignalsList(List<Signals> signalsList) {
        this.signalsList = signalsList;
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
        if (!(object instanceof Alerts)) {
            return false;
        }
        Alerts other = (Alerts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.easyweb24.actionbot.entity.Alerts[ id=" + id + " ]";
    }
    
}
