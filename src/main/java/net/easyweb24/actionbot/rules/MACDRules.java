/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.rules;

import java.util.List;
import net.easyweb24.actionbot.dto.StrategiesDTO;
import net.easyweb24.actionbot.indicators.AbstractMPIndicators;
import net.easyweb24.actionbot.indicators.MACD;
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
public class MACDRules extends MPRules{

    
    private MACD macd;

    public MACDRules(BarSeries series) {
        super(series);
    }
    public MACDRules(BarSeries series, StrategiesDTO strategiesDTO){
        super(series, strategiesDTO);
    }

    protected void buildEntryRule() {
        
        CrossedUpIndicatorRule entryRule = new CrossedUpIndicatorRule(macd.getMacd(), 0);
        CrossedDownIndicatorRule exitRule = new CrossedDownIndicatorRule(macd.getMacd(), 0);
        setEntryStrategie(new BaseStrategy(entryRule, exitRule));
    }

    protected void buildEntryRuleContinueInLastIndex() {
        OverIndicatorRule isEntryRuleContinueInLastIndex = new OverIndicatorRule(macd.getMacd(), 0);
        UnderIndicatorRule exitRule = new UnderIndicatorRule(macd.getMacd(), 0);
        setEntryContinueStrategie(new BaseStrategy(isEntryRuleContinueInLastIndex, exitRule));
    }

    protected void checkRules() {
        List<Double> values = macd.getMacdValues();
        checkSimplyRules(values);
        

    }

    @Override
    protected void setIndicator(BarSeries series, int strategieId) {
        macd = getIndicatorsService().getMACD(series, strategieId);
    }
    
    @Override
    public AbstractMPIndicators getIdicatorMainInfo(){
        return macd ;
    }
}