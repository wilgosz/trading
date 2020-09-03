/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.components;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import net.easyweb24.actionbot.controllers.FinnhubController;
import net.easyweb24.actionbot.dto.AggregateIndicators;
import net.easyweb24.actionbot.dto.FinnhubSignalsDTO;
import net.easyweb24.actionbot.entity.FinnhubSignals;
import net.easyweb24.actionbot.entity.Symbols;
import net.easyweb24.actionbot.repository.FinnhubSignalsRepository;
import net.easyweb24.actionbot.repository.SymbolsRepository;
import net.easyweb24.actionbot.service.FinnhubDtoService;
import net.easyweb24.actionbot.service.FinnhubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final FinnhubService finnhubService;
    private final FinnhubDtoService finnhubDtoService;
    private final FinnhubSignalsRepository finnhubSignalsRepository;
    
    public ScheduledTask(
            SymbolsRepository symbolsRepository,
            FinnhubService finnhubService,
            FinnhubDtoService finnhubDtoService,
            FinnhubSignalsRepository finnhubSignalsRepository
    ) {
        this.symbolsRepository = symbolsRepository;
        this.finnhubDtoService = finnhubDtoService;
        this.finnhubService = finnhubService;
        this.finnhubSignalsRepository = finnhubSignalsRepository;
    }

    //@Scheduled(cron = "0 */2 * * * *", zone = "Europe/Berlin")
    @Scheduled(cron = "0 0 23 * * *", zone = "Europe/Berlin")
    public void getCandlesAndIndicatorsPerDay() {
        getAllIndicators();
        getCandles();
    }
    
    //@Scheduled(cron = "0 */2 * * * *", zone = "Europe/Berlin")
    @Scheduled(cron = "0 0 14-22 ? * MON-FRI", zone = "Europe/Berlin")
    public void getCandlesAndIndicatorsPerHour() {
        getAllIndicatorsPerHour();
        getCandlesPerHour();
    }
    
    private void getCandlesPerHour() {
        List<String> ohlcSymbols = new ArrayList<>();
        List<FinnhubSignalsDTO> symbols = finnhubSignalsRepository.strongBuyQueryForUpdate();
        String abbreviation;
        int counter = 0;
        for (FinnhubSignalsDTO next : symbols) {
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
            counter ++;
            if(counter > 10){
                break;
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
        int counter =0;
        
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
            counter ++;
            if(counter > 10){
                break;
            }
        }
        System.out.println(fnsignals_list);
    }
}
