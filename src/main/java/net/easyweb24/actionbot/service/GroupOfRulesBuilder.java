/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.service;

import java.util.List;
import net.easyweb24.actionbot.rules.MPRules;
import org.springframework.stereotype.Service;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

/**
 *
 * @author zbigniewwilgosz
 */


public abstract class GroupOfRulesBuilder {
    
    private List<MPRules> rulesList;
    private int sell = 0;
    private int neutral = 0;
    private int buy = 0;
    private int testDayRange = 1;
    
    public GroupOfRulesBuilder(BarSeries series, List<MPRules> list){
        testGroupRoles(series, list);
    }
    
    public GroupOfRulesBuilder(BarSeries series, List<MPRules> list, int testDayRange){
        setTestTimeRange(testDayRange);
        testGroupRoles(series, list);
    }
    
    protected abstract void groupAndBuildRule(BarSeries subseries, MPRules rules);
    
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
     *  add to sell
     */
    public void addSell() {
        this.sell ++;
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
     * add to neutral
     */
    public void addNeutral() {
        this.neutral ++;
    }

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
     * add to buy
     */
    public void addBuy() {
        this.buy++;
    }

    /**
     * @return the rulesList
     */
    public List<MPRules> getRulesList() {
        return rulesList;
    }

    /**
     * @param rulesList the rulesList to set
     */
    public void setRulesList(List<MPRules> rulesList) {
        this.rulesList = rulesList;
    }

    /**
     * @return the testTimeRange
     */
    public int getTestTimeRange() {
        return testDayRange;
    }

    /**
     * @param testTimeRange the testTimeRange to set
     */
    public void setTestTimeRange(int testTimeRange) {
        this.testDayRange = testTimeRange;
    }
    
    protected void testGroupRoles(BarSeries series, List<MPRules> list) {

        int index = series.getEndIndex();
        BarSeries subseries;

        subseries = new BaseBarSeries();

        for (int b = 0; b <= (index - getTestTimeRange()); b++) {
            subseries.addBar(series.getBar(b));
        }

        for (int i = 1; i <= getTestTimeRange(); i++) {

            subseries.addBar(series.getBar(index - getTestTimeRange() + i));
            setBuy(0);
            setNeutral(0);
            setSell(0);
            
            for (MPRules rules : list) {
                
                groupAndBuildRule(subseries, rules);
            }
            if (getBuy() > 7) {
                System.out.print(subseries.getBar(subseries.getEndIndex()).getSimpleDateName());
                System.out.println(" - " + getBuy());
            }
        }
    }
    
    
}
