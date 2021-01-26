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
public class StrategiesDTO {

    private Integer id = 0;
    private String name = "Default strategy";
    private Integer timeRange = 14;
    private Integer supportTimeRange = 50;

    public StrategiesDTO() {

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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

}
