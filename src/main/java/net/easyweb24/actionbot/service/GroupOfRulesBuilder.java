/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.easyweb24.actionbot.dto.SimulationResults;
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
    private Map<String, List> results = new HashMap<>();
    private SimulationResults result;

    public GroupOfRulesBuilder(BarSeries series, List<MPRules> list) {
        testGroupRoles(series, list);
    }

    public GroupOfRulesBuilder(BarSeries series, List<MPRules> list, int testDayRange) {
        setTestTimeRange(testDayRange);
        testGroupRoles(series, list);
    }

    /**
     * This Constructor work corectly only with with rules with complete
     * BarSeries
     *
     * @param list
     */
    public GroupOfRulesBuilder(List<MPRules> list) {
        groupRulesForEndIndex(list);
    }

    protected abstract void groupAndBuildRule(MPRules rules);

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
     * add to sell
     */
    public void addSell() {
        this.sell++;
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
        this.neutral++;
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

    public Map<String, List> getSimulationResults() {
        return results;
    }

    public SimulationResults getLastResults() {
        return result;
    }

    public void groupRulesForEndIndex(List<MPRules> list) {
        try {
            for (MPRules rules : list) {
                groupAndBuildRule(rules);
            }
            result = new SimulationResults();
            result.setBuy(getBuy());
            result.setSell(getSell());
            result.setNeutral(getNeutral());
            BarSeries series = list.get(0).getIdicatorMainInfo().getSeries();
            result.setEndtime(series.getBar(series.getEndIndex()).getEndTime());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    protected void testGroupRoles(BarSeries series, List<MPRules> list) {

        results = new HashMap<>();
        int index = series.getEndIndex();
        BarSeries subseries;

        subseries = new BaseBarSeries();
        List buy = new ArrayList();
        List dates = new ArrayList();

        for (int b = 0; b <= (index - getTestTimeRange()); b++) {
            subseries.addBar(series.getBar(b));
        }

        for (int i = 1; i <= getTestTimeRange(); i++) {

            subseries.addBar(series.getBar(index - getTestTimeRange() + i));
            setBuy(0);
            setNeutral(0);
            setSell(0);

            for (MPRules rules : list) {

                rules.setSeries(subseries, rules.getInd2());
                groupAndBuildRule(rules);
            }
            if (getBuy() > -1) {

                buy.add(getBuy());
                dates.add(subseries.getBar(subseries.getEndIndex()).getEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.GERMANY)));
                results.put("buy", buy);
                results.put("dates", dates);
            }
        }
    }

}
