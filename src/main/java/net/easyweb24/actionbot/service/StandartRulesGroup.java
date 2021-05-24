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

    public StandartRulesGroup(List<MPRules> list) {
        super(list);
    }

    /**
     *
     * @param subseries
     * @param rules
     */
    @Override
    protected void groupAndBuildRule(MPRules rules) {

        if (!rules.getIdicatorMainInfo().getReverse()) {
            if (rules.isEntryContinue()) {
                addBuy();
            } else {
                addSell();
            }
            if (rules.isShouldEnter() && rules.isEntryContinue()) {
                addBuy();
                if (rules.isGoUp()) {
                    addBuy();
                    addBuy();
                    addBuy();
                } else {
                    addNeutral();
                }
            } else if (rules.isShouldExit()) {
                addSell();
                addSell();
                addSell();
                if (!rules.isGoUp()) {
                    addSell();
                } else {
                    addNeutral();
                }
            } else if (rules.isGoUp()) {
                addBuy();
                addNeutral();
                addNeutral();
                addNeutral();
            } else {
                addSell();
                addSell();
                addSell();
                addNeutral();
            }

        } else {
            if (!rules.isEntryContinue()) {
                addBuy();
                addBuy();
                addBuy();
                if (rules.isGoUp()) {
                    addBuy();
                } else {
                    addNeutral();
                }
            } else {
                addNeutral();
                addNeutral();
                addNeutral();
                addNeutral();
            }
        }
    }
}
