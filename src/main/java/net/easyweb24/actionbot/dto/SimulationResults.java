/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.dto;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 *
 * @author zbigniewwilgosz
 */
public class SimulationResults {
    
    private int buy;
    private int neutral;
    private int sell;
    private ZonedDateTime endtime;

    /**
     * @return the buy
     */
    public int getBuy() {
        return buy;
    }

    /**
     * @param buy the buy to set
     */
    public void setBuy(int buy) {
        this.buy = buy;
    }

    /**
     * @return the neutral
     */
    public int getNeutral() {
        return neutral;
    }

    /**
     * @param neutral the neutral to set
     */
    public void setNeutral(int neutral) {
        this.neutral = neutral;
    }

    /**
     * @return the sell
     */
    public int getSell() {
        return sell;
    }

    /**
     * @param sell the sell to set
     */
    public void setSell(int sell) {
        this.sell = sell;
    }

    /**
     * @return the endtime
     */
    public ZonedDateTime getEndtime() {
        return endtime;
    }

    /**
     * @param endtime the endtime to set
     */
    public void setEndtime(ZonedDateTime endtime) {
        this.endtime = endtime;
    }
    
    
}
