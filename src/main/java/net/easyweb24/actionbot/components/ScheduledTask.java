/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.components;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import net.easyweb24.actionbot.controllers.FinnhubController;
import net.easyweb24.actionbot.dto.FinnhubSignalsDTO;
import net.easyweb24.actionbot.entity.Symbols;
import net.easyweb24.actionbot.repository.FinnhubSignalsRepository;
import net.easyweb24.actionbot.repository.MpSignalsRepository;
import net.easyweb24.actionbot.repository.SymbolsRepository;
import net.easyweb24.actionbot.service.FinnhubDtoService;
import net.easyweb24.actionbot.service.FinnhubService;
import net.easyweb24.actionbot.service.MpSignalsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author zbigniewwilgosz
 */
@Component
public class ScheduledTask {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final SymbolsRepository symbolsRepository;
    private final MpSignalsService mpSignalsService;
    private final FinnhubDtoService finnhubDtoService;
    private final FinnhubSignalsRepository finnhubSignalsRepository;
    private final MpSignalsRepository mpSignalsRepository;

    public ScheduledTask(
            SymbolsRepository symbolsRepository,
            MpSignalsService mpSignalsService,
            FinnhubDtoService finnhubDtoService,
            FinnhubSignalsRepository finnhubSignalsRepository,
            MpSignalsRepository mpSignalsRepository
    ) {
        this.symbolsRepository = symbolsRepository;
        this.finnhubDtoService = finnhubDtoService;
        this.mpSignalsService = mpSignalsService;
        this.finnhubSignalsRepository = finnhubSignalsRepository;
        this.mpSignalsRepository = mpSignalsRepository;
    }

    //@Scheduled(cron = "0 */2 * * * *", zone = "Europe/Berlin")
    @Scheduled(cron = "0 0 2 * * *", zone = "Europe/Berlin")
    public void getCandlesAndIndicatorsPerDay() {
        //getAllIndicators();
        getCandles();
        saveMpSignals();
    }

    
    
    //@Scheduled(cron = "0 */1 * * * *", zone = "Europe/Berlin")
    @Scheduled(cron = "0 0 14-22 ? * MON-FRI", zone = "Europe/Berlin")
    public void getCandlesAndIndicatorsPerHour() {
        //getAllIndicatorsPerHour();
        getCandlesPerHour();
        saveMpSignals();
    }
    
    private void saveMpSignals(){
        mpSignalsService.saveSignals();
    }

    private void getCandlesPerHour() {
        List<FinnhubSignalsDTO> symbols; 
        
        //symbols = finnhubSignalsRepository.strongBuyQueryForUpdate();
        //saveOhlc(symbols);
        symbols = mpSignalsRepository.strongBuyQueryForUpdate();
        saveOhlc(symbols);
    }
    
    private void saveOhlc(List<FinnhubSignalsDTO> symbols){
        
        String abbreviation;
        int counter = 0;
        for (FinnhubSignalsDTO next : symbols) {
            abbreviation = next.getAbbreviation();
            try {
                finnhubDtoService.saveOHLC(abbreviation);
            } catch (IOException e) {
                java.util.logging.Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, e);
            }

            try {
                TimeUnit.MILLISECONDS.sleep(1500);
            } catch (Exception e) {
                java.util.logging.Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, e);
            }
            counter++;
            if (counter > 5) {
                //break;
            }
        }
    }

    private void getCandles() {
        List<String> ohlcSymbols = new ArrayList<>();
        List<Symbols> symbols = symbolsRepository.findAllOnlyWithExistingComany();
        String abbreviation;
        for (Symbols next : symbols) {
            abbreviation = next.getAbbreviation();
            try {
                finnhubDtoService.saveOHLC(abbreviation);
                ohlcSymbols.add(abbreviation);
            } catch (IOException e) {
                java.util.logging.Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, e);
            }

            try {
                TimeUnit.MILLISECONDS.sleep(1500);
            } catch (Exception e) {
                java.util.logging.Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private void getAllIndicators() {
        List<String> fnsignals_list = new ArrayList<>();
        List<Symbols> symbols = symbolsRepository.findAllOnlyWithExistingComany();
        String abbreviation;

        for (Symbols next : symbols) {
            abbreviation = next.getAbbreviation();
            try {
                finnhubDtoService.saveAgregateIndicators(abbreviation);
                fnsignals_list.add(abbreviation);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex) {
                java.util.logging.Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                TimeUnit.MILLISECONDS.sleep(1500);
            } catch (Exception e) {

            }
        }
    }

    private void getAllIndicatorsPerHour() {
        List<String> fnsignals_list = new ArrayList<>();
        List<FinnhubSignalsDTO> symbols = finnhubSignalsRepository.strongBuyQueryForUpdate();
        String abbreviation;
        int counter = 0;
        for (FinnhubSignalsDTO next : symbols) {
            abbreviation = next.getAbbreviation();
            fnsignals_list.add(abbreviation);
            try {
                finnhubDtoService.saveAgregateIndicators(abbreviation);
                fnsignals_list.add(abbreviation);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex) {
                java.util.logging.Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                TimeUnit.MILLISECONDS.sleep(1500);
            } catch (Exception e) {

            }
            counter++;
            if (counter > 5) {
                //break;
            }
        }
        System.out.println(fnsignals_list);
    }
}
