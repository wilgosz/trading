/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.rules;

import net.easyweb24.actionbot.dto.IndicatorsDTO;
import net.easyweb24.actionbot.dto.StrategiesDTO;
import net.easyweb24.actionbot.indicators.AbstractMPIndicators;
import org.ta4j.core.BarSeries;

/**
 *
 * @author zbigniewwilgosz
 */
public class BollingerRules extends MPRules{

    public BollingerRules(BarSeries series) {
        super(series);
    }
    
    public BollingerRules(BarSeries series, StrategiesDTO strategiesDTO){
        super(series, strategiesDTO);
    }
    
    public BollingerRules(BarSeries series, StrategiesDTO strategiesDTO, IndicatorsDTO ind2){
        super(series, strategiesDTO, ind2);
    }

    @Override
    protected void buildEntryRule() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void buildEntryRuleContinueInLastIndex() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void checkRules() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void setIndicator(BarSeries series, int strategieId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void setIndicator(BarSeries series, IndicatorsDTO ind2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbstractMPIndicators getIdicatorMainInfo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
