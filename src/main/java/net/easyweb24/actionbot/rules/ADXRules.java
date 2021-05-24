/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.rules;

import java.util.List;
import net.easyweb24.actionbot.dto.IndicatorsDTO;
import net.easyweb24.actionbot.dto.StrategiesDTO;
import net.easyweb24.actionbot.indicators.ADX;
import net.easyweb24.actionbot.indicators.AbstractMPIndicators;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.adx.ADXIndicator;
import org.ta4j.core.indicators.adx.MinusDIIndicator;
import org.ta4j.core.indicators.adx.PlusDIIndicator;
import org.ta4j.core.trading.rules.CrossedDownIndicatorRule;
import org.ta4j.core.trading.rules.CrossedUpIndicatorRule;
import org.ta4j.core.trading.rules.OverIndicatorRule;
import org.ta4j.core.trading.rules.UnderIndicatorRule;

/**
 *
 * @author zbigniewwilgosz
 */
public class ADXRules extends MPRules {

    private ADX adx;

    public ADXRules(BarSeries series) {
        super(series);
    }

    public ADXRules(BarSeries series, StrategiesDTO strategiesDTO) {
        super(series, strategiesDTO);
    }

    /**
     *
     */
    @Override
    protected void buildEntryRule() {

        final SMAIndicator smaIndicator = adx.getSmaIndicator();
        final int adxBarCount = adx.getPeriod();
        final ADXIndicator adxIndicator = adx.getAdxIndicator();
        final OverIndicatorRule adxOver20Rule = new OverIndicatorRule(adxIndicator, adx.getBottom_border());

        final PlusDIIndicator plusDIIndicator = adx.getPlusDIIndicator();
        final MinusDIIndicator minusDIIndicator = adx.getMinusDIIndicator();

        final Rule plusDICrossedUpMinusDI = new CrossedUpIndicatorRule(plusDIIndicator, minusDIIndicator);
        final Rule plusDICrossedDownMinusDI = new CrossedDownIndicatorRule(plusDIIndicator, minusDIIndicator);
        final OverIndicatorRule closePriceOverSma = new OverIndicatorRule(adx.getClosePrice(), smaIndicator);
        final Rule entryRule = adxOver20Rule.and(plusDICrossedUpMinusDI);//.and(closePriceOverSma);

        final UnderIndicatorRule closePriceUnderSma = new UnderIndicatorRule(adx.getClosePrice(), smaIndicator);
        final Rule exitRule = adxOver20Rule.and(plusDICrossedDownMinusDI);//.and(closePriceUnderSma);

        setEntryStrategie(new BaseStrategy(entryRule, exitRule, adxBarCount));
    }

    /**
     *
     */
    @Override
    protected void buildEntryRuleContinueInLastIndex() {
        
        final SMAIndicator smaIndicator = adx.getSmaIndicator();
        final int adxBarCount = adx.getPeriod();
        final ADXIndicator adxIndicator = adx.getAdxIndicator();
        final OverIndicatorRule adxOver20Rule = new OverIndicatorRule(adxIndicator, adx.getBottom_border());

        final PlusDIIndicator plusDIIndicator = adx.getPlusDIIndicator();
        final MinusDIIndicator minusDIIndicator = adx.getMinusDIIndicator();

        final Rule plusDICrossedUpMinusDI = new OverIndicatorRule(plusDIIndicator, minusDIIndicator);
        final Rule plusDICrossedDownMinusDI = new UnderIndicatorRule(plusDIIndicator, minusDIIndicator);
        final Rule closePriceOverSma = new OverIndicatorRule(adx.getClosePrice(), smaIndicator);
        final Rule entryRule = adxOver20Rule.and(plusDICrossedUpMinusDI);//.and(closePriceOverSma);

        final UnderIndicatorRule closePriceUnderSma = new UnderIndicatorRule(adx.getClosePrice(), smaIndicator);
        final Rule exitRule = adxOver20Rule.and(plusDICrossedDownMinusDI);//.and(closePriceUnderSma);
        
        setEntryContinueStrategie(new BaseStrategy(entryRule, exitRule, adxBarCount));
    }

    @Override
    protected void checkRules() {
        List<Double> values = adx.getAdxIndicatorValues();
        checkSimplyRules(values);

    }

    @Override
    protected void setIndicator(BarSeries series, int strategieId) {
        adx = getIndicatorsService().getAdx(series, strategieId);
    }

    @Override
    public AbstractMPIndicators getIdicatorMainInfo() {
        return adx;
    }

    @Override
    protected void setIndicator(BarSeries series, IndicatorsDTO ind2) {
        adx = getIndicatorsService().getAdx(series, ind2);
    }
}
