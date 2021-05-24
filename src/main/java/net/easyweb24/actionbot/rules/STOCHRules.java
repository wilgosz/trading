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
import net.easyweb24.actionbot.indicators.STOCHASTIC_SLOW;
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
public class STOCHRules extends MPRules {

    private STOCHASTIC_SLOW stoch;

    public STOCHRules(BarSeries series) {
        super(series);
    }

    public STOCHRules(BarSeries series, StrategiesDTO strategiesDTO) {
        super(series, strategiesDTO);
    }

    @Override
    protected void buildEntryRule() {

        CrossedUpIndicatorRule entryRule = new CrossedUpIndicatorRule(stoch.getStochasticOscillK(), stoch.getBottom_border());
        CrossedDownIndicatorRule exitRule = new CrossedDownIndicatorRule(stoch.getStochasticOscillK(), stoch.getTop_border());
        setEntryStrategie(new BaseStrategy(entryRule, exitRule));
    }

    @Override
    protected void buildEntryRuleContinueInLastIndex() {
        
        UnderIndicatorRule underTopBorderIndicatorRule = new UnderIndicatorRule(stoch.getStochasticOscillK(), stoch.getTop_border());
        Rule isEntryRuleContinueInLastIndex = new OverIndicatorRule(stoch.getStochasticOscillK(), stoch.getBottom_border()).and(underTopBorderIndicatorRule);
        UnderIndicatorRule exitRule = new UnderIndicatorRule(stoch.getStochasticOscillK(), stoch.getTop_border());
        setEntryContinueStrategie(new BaseStrategy(isEntryRuleContinueInLastIndex, exitRule));
    }

    @Override
    protected void checkRules() {
        List<Double> values = stoch.getStochasticOscillKValues();
        checkSimplyRules(values);

    }

    @Override
    protected void setIndicator(BarSeries series, int strategieId) {
        stoch = getIndicatorsService().getStochasticS(series, strategieId);
    }
    
    @Override
    public AbstractMPIndicators getIdicatorMainInfo() {
        return stoch;
    }

    @Override
    protected void setIndicator(BarSeries series, IndicatorsDTO ind2) {
        stoch = getIndicatorsService().getStochasticS(series, ind2);
    }
}
