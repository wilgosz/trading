/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import net.easyweb24.actionbot.components.utils.DBToBarSeries;
import net.easyweb24.actionbot.entity.MpSignals;
import net.easyweb24.actionbot.entity.MpSignalsArchive;
import net.easyweb24.actionbot.entity.Symbols;
import net.easyweb24.actionbot.repository.MpSignalsArchiveRepository;
import net.easyweb24.actionbot.repository.MpSignalsRepository;
import net.easyweb24.actionbot.repository.SymbolsRepository;
import net.easyweb24.actionbot.rules.MACDRules;
import net.easyweb24.actionbot.rules.MONEYFLOWRules;
import net.easyweb24.actionbot.rules.MPRules;
import net.easyweb24.actionbot.rules.RSIRules;
import net.easyweb24.actionbot.rules.SMARules;
import net.easyweb24.actionbot.rules.STOCHRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ta4j.core.BarSeries;

@Service
public class MpSignalsService {

    @Autowired
    private MpSignalsRepository mpSignalsRepository;

    @Autowired
    private MpSignalsArchiveRepository mpSignalsArchiveRepository;

    @Autowired
    private DBToBarSeries dbToBarSeries;

    @Autowired
    private SymbolsRepository symbolsRepository;

    private List<Symbols> symbols;

    public void saveSignals() {
        symbols = symbolsRepository.findAllOnlyWithExistingComany();
        List<MPRules> list;
        MACDRules macd;
        MpSignals signal;
        MpSignalsArchive signals_archive;
        List<MpSignals> record = new ArrayList<>();
        List<MpSignalsArchive> record_archive = new ArrayList<>();
        int counter = 0;
        for (Symbols symbol : symbols) {

            BarSeries series = dbToBarSeries.getBars(symbol.getAbbreviation());

            try {
                list = new ArrayList<>();
                list.add(new STOCHRules(series));
                list.add(new SMARules(series));
                list.add(new RSIRules(series));
                list.add(new MONEYFLOWRules(series));

                int buy = 0;
                int neutral = 0;
                int sell = 0;

                for (MPRules rules : list) {

                    if (rules.isShouldEnter() && rules.isEntryContinue() && rules.isGoUp()) {
                        buy++;
                        if (rules.getLastIndexTheBest() == 100) {
                            buy++;
                        }
                    } else {
                        neutral++;
                    }

                }
                macd = new MACDRules(series);
                if (!macd.isEntryContinue() && !macd.isGoUp()) {
                    buy++;
                } else {
                    neutral++;
                }

                signal = mpSignalsRepository.findByAbbreviation(symbol.getAbbreviation());
                if (signal == null) {
                    signal = new MpSignals();
                    signal.setCreateDateTime(LocalDateTime.now());
                }
                signal.setAbbreviation(symbol.getAbbreviation());
                signal.setUpdateDateTime(LocalDateTime.now());
                signal.setBuy(buy);
                signal.setNeutral(neutral);
                signal.setSell(sell);
                record.add(signal);

                signals_archive = mpSignalsArchiveRepository.findByAbbreviationAndDate(symbol.getAbbreviation(), LocalDate.now());
                if (signals_archive == null) {
                    signals_archive = new MpSignalsArchive();
                }
                signals_archive.setAbbreviation(symbol.getAbbreviation());
                signals_archive.setBuy(buy);
                signals_archive.setNeutral(neutral);
                signals_archive.setSell(sell);
                signals_archive.setDate(LocalDate.now());
                record_archive.add(signals_archive);

                System.out.println(symbol.getAbbreviation());
                counter++;
                //if(counter > 100){
                // break;
                //}

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


            /**/
        }
        System.err.println("Before Save");
        mpSignalsRepository.saveAll(record);
        System.err.println("Saved");
        mpSignalsArchiveRepository.saveAll(record_archive);
        System.err.println("Saved Archive");
    }

    public void saveSignalsArchive() {

    }
}
