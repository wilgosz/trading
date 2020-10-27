/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.rules;

import java.util.List;
import net.easyweb24.actionbot.indicators.RSI;
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
public class RSIRules extends MPRules{

    
    private RSI rsi;
    
    public RSIRules(BarSeries series) {
        super(series);
    }

    protected void buildEntryRule() {
        
        CrossedUpIndicatorRule entryRule = new CrossedUpIndicatorRule(rsi.getRsi(), rsi.getBottom_border());
        CrossedDownIndicatorRule exitRule = new CrossedDownIndicatorRule(rsi.getRsi(), rsi.getTop_border());
        setEntryStrategie(new BaseStrategy(entryRule, exitRule));
    }

    protected void buildEntryRuleContinueInLastIndex() {
        OverIndicatorRule isEntryRuleContinueInLastIndex = new OverIndicatorRule(rsi.getRsi(), rsi.getBottom_border());
        UnderIndicatorRule exitRule = new UnderIndicatorRule(rsi.getRsi(), rsi.getTop_border());
        setEntryContinueStrategie(new BaseStrategy(isEntryRuleContinueInLastIndex, exitRule));
    }

    protected void checkRules() {
        List<Double> values = rsi.getRsiValues();
        checkSimplyRules(values);
        

    }

    @Override
    protected void setIndicator(BarSeries series) {
        rsi = getIndicatorsService().getRsi(series);
    }
}