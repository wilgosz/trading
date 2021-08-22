/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import net.easyweb24.actionbot.components.utils.DBToBarSeries;
import net.easyweb24.actionbot.dto.SimulationResults;
import net.easyweb24.actionbot.entity.MpSignals;
import net.easyweb24.actionbot.entity.Strategies;
import net.easyweb24.actionbot.entity.Symbols;
import net.easyweb24.actionbot.repository.MpSignalsRepository;
import net.easyweb24.actionbot.repository.StrategiesRepository;
import net.easyweb24.actionbot.repository.SymbolsRepository;
import net.easyweb24.actionbot.rules.MPRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ta4j.core.BarSeries;

@Service
public class MpSignalsService {

    @Autowired
    private MpSignalsRepository mpSignalsRepository;

    @Autowired
    private DBToBarSeries dbToBarSeries;

    @Autowired
    private SymbolsRepository symbolsRepository;

    @Autowired
    StrategiesRepository strategiesRepository;

    @Autowired
    private RulesSevice rulesSevice;

    private List<Symbols> symbols;

    public void saveSignals() {

        List<Strategies> allstrategies = strategiesRepository.findAll();
        symbols = symbolsRepository.findAllOnlyWithExistingComany();
        MpSignals signal;

        for (Strategies strategies : allstrategies) {
            
            
            List<MpSignals> record = new ArrayList<>();
            int count = 0;
            for (Symbols symbol : symbols) {
                try{
                count++;
                System.out.println(count + ". ");
                System.out.println(symbol.getAbbreviation());

                BarSeries series = dbToBarSeries.getBars(symbol.getAbbreviation());
                List<MPRules> list2 = rulesSevice.getRulesByStrategyId(series, strategies);
                StandartRulesGroup group2 = new StandartRulesGroup(list2);
                SimulationResults lastResults = group2.getLastResults();
                signal = mpSignalsRepository.findByAbbreviationAndStrategiesId(symbol.getAbbreviation(), strategies);
                if (signal == null) {
                    signal = new MpSignals();
                    signal.setCreateDateTime(LocalDateTime.now());
                }
                signal.setAbbreviation(symbol.getAbbreviation());
                signal.setUpdateDateTime(LocalDateTime.now());
                signal.setBuy(lastResults.getBuy());
                signal.setNeutral(lastResults.getNeutral());
                signal.setSell(lastResults.getSell());
                signal.setStrategiesId(strategies);
                record.add(signal);

                if (count > 100) {
                    //break;
                }
                }catch(NullPointerException ex){
                    System.err.println(symbol.getAbbreviation()+" "+ex.getMessage());
                }
            }
            System.err.println("Before Save");
            mpSignalsRepository.saveAll(record);
            System.err.println("Saved records");
        }
    }

    public void saveSignalsArchive() {

    }
}
