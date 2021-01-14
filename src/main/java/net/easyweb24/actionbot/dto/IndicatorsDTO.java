/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.dto;

/**
 *
 * @author zbigniewwilgosz
 */
public class IndicatorsDTO {

    private Integer id;
    private String abbreviation;
    private Integer periodLong;
    private Integer periodShort;
    private Integer period;
    private Integer topBorder;
    private Integer bottomBorder;
    private Integer strategieId = 0;
    
    public IndicatorsDTO(){
        
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
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
     * @return the periodLong
     */
    public Integer getPeriodLong() {
        return periodLong;
    }

    /**
     * @param periodLong the periodLong to set
     */
    public void setPeriodLong(Integer periodLong) {
        this.periodLong = periodLong;
    }

    /**
     * @return the periodShort
     */
    public Integer getPeriodShort() {
        return periodShort;
    }

    /**
     * @param periodShort the periodShort to set
     */
    public void setPeriodShort(Integer periodShort) {
        this.periodShort = periodShort;
    }

    /**
     * @return the period
     */
    public Integer getPeriod() {
        return period;
    }

    /**
     * @param period the period to set
     */
    public void setPeriod(Integer period) {
        this.period = period;
    }

    /**
     * @return the topBorder
     */
    public Integer getTopBorder() {
        return topBorder;
    }

    /**
     * @param topBorder the topBorder to set
     */
    public void setTopBorder(Integer topBorder) {
        this.topBorder = topBorder;
    }

    /**
     * @return the bottomBorder
     */
    public Integer getBottomBorder() {
        return bottomBorder;
    }

    /**
     * @param bottomBorder the bottomBorder to set
     */
    public void setBottomBorder(Integer bottomBorder) {
        this.bottomBorder = bottomBorder;
    }

    /**
     * @return the strategieId
     */
    public Integer getStrategieId() {
        return strategieId;
    }

    /**
     * @param strategieId the strategieId to set
     */
    public void setStrategieId(Integer strategieId) {
        this.strategieId = strategieId;
    }
    
    
}
