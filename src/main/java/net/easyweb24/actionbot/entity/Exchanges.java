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
@Table(name = "exchanges", indexes = {@Index(name = "exchanges_code_idx",  columnList="code", unique = false)})
@NamedQueries({
    @NamedQuery(name = "Exchanges.findAll", query = "SELECT e FROM Exchanges e"),
    @NamedQuery(name = "Exchanges.findById", query = "SELECT e FROM Exchanges e WHERE e.id = :id"),
    @NamedQuery(name = "Exchanges.findByCode", query = "SELECT e FROM Exchanges e WHERE e.code = :code"),
    @NamedQuery(name = "Exchanges.findByCurrency", query = "SELECT e FROM Exchanges e WHERE e.currency = :currency"),
    @NamedQuery(name = "Exchanges.findByName", query = "SELECT e FROM Exchanges e WHERE e.name = :name"),
    @NamedQuery(name = "Exchanges.findByMic", query = "SELECT e FROM Exchanges e WHERE e.mic = :mic"),
    @NamedQuery(name = "Exchanges.findByTimezone", query = "SELECT e FROM Exchanges e WHERE e.timezone = :timezone")})
public class Exchanges implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Size(max = 16)
    @Column(length = 16)
    private String code;
    @Size(max = 16)
    @Column(length = 16)
    private String currency;
    @Size(max = 255)
    @Column(length = 255)
    private String name;
    @Size(max = 16)
    @Column(length = 16)
    private String mic;
    @Size(max = 255)
    @Column(length = 255)
    private String timezone;

    public Exchanges() {
    }

    public Exchanges(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMic() {
        return mic;
    }

    public void setMic(String mic) {
        this.mic = mic;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
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
        if (!(object instanceof Exchanges)) {
            return false;
        }
        Exchanges other = (Exchanges) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.easyweb24.actionbot.entity.Exchanges[ id=" + id + " ]";
    }
    
}
