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
public class OHLC {
    
    private String time;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Integer value; // Volume

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the open
     */
    public Double getOpen() {
        return open;
    }

    /**
     * @param open the open to set
     */
    public void setOpen(Double open) {
        this.open = open;
    }

    /**
     * @return the high
     */
    public Double getHigh() {
        return high;
    }

    /**
     * @param high the high to set
     */
    public void setHigh(Double high) {
        this.high = high;
    }

    /**
     * @return the low
     */
    public Double getLow() {
        return low;
    }

    /**
     * @param low the low to set
     */
    public void setLow(Double low) {
        this.low = low;
    }

    /**
     * @return the close
     */
    public Double getClose() {
        return close;
    }

    /**
     * @param close the close to set
     */
    public void setClose(Double close) {
        this.close = close;
    }

    /**
     * @return the value
     */
    public Integer getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Integer value) {
        this.value = value;
    }
    
    
    
}
