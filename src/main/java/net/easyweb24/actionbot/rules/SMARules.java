/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.rules;

import java.util.List;
import net.easyweb24.actionbot.dto.IndicatorsDTO;
import net.easyweb24.actionbot.dto.StrategiesDTO;
import net.easyweb24.actionbot.indicators.AbstractMPIndicators;
import net.easyweb24.actionbot.indicators.SMA;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.trading.rules.CrossedDownIndicatorRule;
import org.ta4j.core.trading.rules.CrossedUpIndicatorRule;
import org.ta4j.core.trading.rules.OverIndicatorRule;
import org.ta4j.core.trading.rules.UnderIndicatorRule;

/**
 *
 * @author zbigniewwilgosz
 */
public class SMARules extends MPRules {

    private SMA sma;

    public SMARules(BarSeries series) {
        super(series);
    }
    
    public SMARules(BarSeries series, StrategiesDTO strategiesDTO){
        super(series, strategiesDTO);
    }

    @Override
    protected void buildEntryRule() {

        CrossedUpIndicatorRule entryRule = new CrossedUpIndicatorRule(sma.getMacdsma(), 0);
        CrossedDownIndicatorRule exitRule = new CrossedDownIndicatorRule(sma.getMacdsma(), 0);
        setEntryStrategie(new BaseStrategy(entryRule, exitRule));
    }

    @Override
    protected void buildEntryRuleContinueInLastIndex() {
        OverIndicatorRule isEntryRuleContinueInLastIndex = new OverIndicatorRule(sma.getMacdsma(), 0);
        UnderIndicatorRule exitRule = new UnderIndicatorRule(sma.getMacdsma(), 0);
        setEntryContinueStrategie(new BaseStrategy(isEntryRuleContinueInLastIndex, exitRule));
    }

    @Override
    protected void checkRules() {
        List<Double> values = sma.getMacdsmaValues();
        checkSimplyRules(values);
    }

    @Override
    protected void setIndicator(BarSeries series, int strategieId) {
        sma = getIndicatorsService().getSMA(series, strategieId);
    }
    
    @Override
    public AbstractMPIndicators getIdicatorMainInfo() {
        return sma;
    }

    @Override
    protected void setIndicator(BarSeries series, IndicatorsDTO ind2) {
        sma = getIndicatorsService().getSMA(series, ind2);
    }
}
