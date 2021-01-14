/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.service;

import java.util.List;
import net.easyweb24.actionbot.rules.MPRules;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

/**
 *
 * @author zbigniewwilgosz
 */
public class StandartRulesGroup extends GroupOfRulesBuilder {

    public StandartRulesGroup(BarSeries series, List<MPRules> list) {
        super(series, list);
    }
    
    public StandartRulesGroup(BarSeries series, List<MPRules> list, int testDayRange) {
        super(series, list, testDayRange);
    }

    /**
     *
     * @param subseries
     * @param rules
     */
    @Override
    protected void groupAndBuildRule(BarSeries subseries, MPRules rules) {

        rules.setSeries(subseries);
        if (!"MACDRules".equals(rules.getClass().getSimpleName())) {
            if (rules.isGoUp() && rules.isEntryContinue() && rules.isShouldEnter()) {
                addBuy();
                if (rules.getLastIndexTheBest() == 100) {
                    addBuy();
                }
            } else {
                addNeutral();
            }
        } else {
            if (!rules.isEntryContinue() && !rules.isGoUp()) {
                addBuy();
            } else {
                addNeutral();
            }
        }
    }
}
