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
import net.easyweb24.actionbot.indicators.MONEYFLOW;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.Rule;
import org.ta4j.core.trading.rules.CrossedDownIndicatorRule;
import org.ta4j.core.trading.rules.CrossedUpIndicatorRule;
import org.ta4j.core.trading.rules.OverIndicatorRule;
import org.ta4j.core.trading.rules.UnderIndicatorRule;

/**
 *
 * @author zbigniewwilgosz
 */
public class MONEYFLOWRules extends MPRules{

    
    private MONEYFLOW moneyFlow;

    public MONEYFLOWRules(BarSeries series) {
        super(series);
    }
    
    public MONEYFLOWRules(BarSeries series, StrategiesDTO strategiesDTO){
        super(series, strategiesDTO);
    }

    @Override
    protected void buildEntryRule() {
        
        CrossedUpIndicatorRule entryRule = new CrossedUpIndicatorRule(moneyFlow.getMfi(), moneyFlow.getBottom_border());
        CrossedDownIndicatorRule exitRule = new CrossedDownIndicatorRule(moneyFlow.getMfi(), moneyFlow.getTop_border());
        setEntryStrategie(new BaseStrategy(entryRule, exitRule));
    }

    @Override
    protected void buildEntryRuleContinueInLastIndex() {
        UnderIndicatorRule underTopBorderIndicatorRule = new UnderIndicatorRule(moneyFlow.getMfi(), moneyFlow.getTop_border());
        Rule isEntryRuleContinueInLastIndex = new OverIndicatorRule(moneyFlow.getMfi(), moneyFlow.getBottom_border()).and(underTopBorderIndicatorRule);
        
        Rule exitRule = new UnderIndicatorRule(moneyFlow.getMfi(), moneyFlow.getTop_border());
        setEntryContinueStrategie(new BaseStrategy(isEntryRuleContinueInLastIndex, exitRule));
    }

    @Override
    protected void checkRules() {
        List<Double> values = moneyFlow.getMfiValues();
        checkSimplyRules(values);
        

    }

    @Override
    protected void setIndicator(BarSeries series, int strategieId) {
        moneyFlow = getIndicatorsService().getMoneyFlow(series, strategieId);
    }

    @Override
    public AbstractMPIndicators getIdicatorMainInfo() {
        return moneyFlow;
    }

    @Override
    protected void setIndicator(BarSeries series, IndicatorsDTO ind2) {
        moneyFlow = getIndicatorsService().getMoneyFlow(series, ind2);
    }
}