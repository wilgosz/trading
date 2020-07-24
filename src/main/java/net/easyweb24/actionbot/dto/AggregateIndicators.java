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
public class AggregateIndicators {
    
    public static final int SIGNAL_STRONG_BUY = 1;
    public static final int SIGNAL_BUY = 2;
    public static final int SIGNAL_HOLD = 3;
    public static final int SIGNAL_SELL = 4;
    public static final int STRONG_SELL = 5;
    
    private int buy;
    private int neutral;
    private int sell;
    private String signal;
    private double adx;
    private boolean trending;

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
     * @return the signal
     */
    public String getSignal() {
        return signal;
    }

    /**
     * @param signal the signal to set
     */
    public void setSignal(String signal) {
        this.signal = signal;
    }

    /**
     * @return the adx
     */
    public double getAdx() {
        return adx;
    }

    /**
     * @param adx the adx to set
     */
    public void setAdx(double adx) {
        this.adx = adx;
    }

    /**
     * @return the trending
     */
    public boolean isTrending() {
        return trending;
    }

    /**
     * @param trending the trending to set
     */
    public void setTrending(boolean trending) {
        this.trending = trending;
    }
}
