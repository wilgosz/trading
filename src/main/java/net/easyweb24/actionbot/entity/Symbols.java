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
@Table(name = "symbols", indexes = {
    @Index(name = "symbols_abbreviation_idx", columnList = "abbreviation", unique = false),
    @Index(name = "symbols_exchange_abbreviation_idx", columnList = "exchange_abbreviation", unique = false)
})
@NamedQueries({
    @NamedQuery(name = "Symbols.findAll", query = "SELECT s FROM Symbols s"),
    @NamedQuery(name = "Symbols.findById", query = "SELECT s FROM Symbols s WHERE s.id = :id"),
    @NamedQuery(name = "Symbols.findByAbbreviation", query = "SELECT s FROM Symbols s WHERE s.abbreviation = :abbreviation"),
    @NamedQuery(name = "Symbols.findByDisplayName", query = "SELECT s FROM Symbols s WHERE s.displayName = :displayName"),
    @NamedQuery(name = "Symbols.findByDescription", query = "SELECT s FROM Symbols s WHERE s.description = :description"),
    @NamedQuery(name = "Symbols.findByExchangeAbbreviation", query = "SELECT s FROM Symbols s WHERE s.exchangeAbbreviation = :exchangeAbbreviation")
})

public class Symbols implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Size(max = 16)
    @Column(length = 16)
    private String abbreviation;
    @Size(max = 16)
    @Column(name = "display_name", length = 16)
    private String displayName;
    @Size(max = 255)
    @Column(length = 255)
    private String description;
    @Size(max = 16)
    @Column(name = "exchange_abbreviation", length = 16)
    private String exchangeAbbreviation;

    public Symbols() {
    }

    public Symbols(Integer id) {
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the exchangeAbbreviation
     */
    public String getExchangeAbbreviation() {
        return exchangeAbbreviation;
    }

    /**
     * @param exchangeAbbreviation the exchangeAbbreviation to set
     */
    public void setExchangeAbbreviation(String exchangeAbbreviation) {
        this.exchangeAbbreviation = exchangeAbbreviation;
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
        if (!(object instanceof Symbols)) {
            return false;
        }
        Symbols other = (Symbols) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.easyweb24.actionbot.entity.Symbols[ id=" + id + " ]";
    }
}
