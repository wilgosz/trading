/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.easyweb24.actionbot.dto.AggregateIndicators;
import net.easyweb24.actionbot.entity.CompanyNews;
import net.easyweb24.actionbot.entity.CompanyProfile;
import net.easyweb24.actionbot.dto.OHLCDTO;
import net.easyweb24.actionbot.entity.FinnhubSignals;
import net.easyweb24.actionbot.entity.OHLC;
import net.easyweb24.actionbot.entity.Symbols;
import net.easyweb24.actionbot.repository.CompanyProfileRepository;
import net.easyweb24.actionbot.repository.FinnhubSignalsRepository;
import net.easyweb24.actionbot.repository.OHLCRepository;
import net.easyweb24.actionbot.repository.SymbolsRepository;
import net.easyweb24.actionbot.service.FinnhubDtoService;
import net.easyweb24.actionbot.service.FinnhubService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zbigniewwilgosz
 */
@RestController
@RequestMapping("/finnhub")
public class FinnhubController {

    private final SymbolsRepository symbolsRepository;
    private final FinnhubService finnhubService;
    private final FinnhubDtoService finnhubDtoService;
    private final FinnhubSignalsRepository finnhubSignalsRepository;
    private final CompanyProfileRepository companyProfileRepository;
    private final OHLCRepository ohlcRepository;

    public FinnhubController(
            SymbolsRepository symbolsRepository,
            FinnhubService finnhubService,
            FinnhubDtoService finnhubDtoService,
            FinnhubSignalsRepository finnhubSignalsRepository,
            CompanyProfileRepository companyProfileRepository,
            OHLCRepository ohlcRepository) {
        this.symbolsRepository = symbolsRepository;
        this.finnhubService = finnhubService;
        this.finnhubDtoService = finnhubDtoService;
        this.finnhubSignalsRepository = finnhubSignalsRepository;
        this.companyProfileRepository = companyProfileRepository;
        this.ohlcRepository = ohlcRepository;
    }

    @RequestMapping(value = "/ohlc/{symbol}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<OHLCDTO>> index(@PathVariable(name = "symbol") String symbol) throws ParseException, IOException {

        String jsonstring = finnhubService.stockCandlesFromLastYear(symbol);
        List<OHLCDTO> OhlcList = finnhubDtoService.convertToOhlcList(jsonstring);

        return ResponseEntity.ok().body(OhlcList);
    }

    @RequestMapping(value = "/company", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CompanyProfile> company() throws ParseException, IOException {

        String jsonstring = finnhubService.companyProfile("AMZN");
        CompanyProfile company = finnhubDtoService.convertToCompanyProfile(jsonstring, "AMZN");

        return ResponseEntity.ok().body(company);
    }

    @RequestMapping(value = "/aggregate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AggregateIndicators> agregateIdicators() throws ParseException, IOException {

        String jsonstring = finnhubService.aggregateIndicatorsPerDay("HLAL");
        AggregateIndicators agregate = finnhubDtoService.convertToAggregateIdicators(jsonstring);

        return ResponseEntity.ok().body(agregate);
    }

    @RequestMapping(value = "/news", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<CompanyNews>> companyNews() throws ParseException, IOException {

        String jsonstring = finnhubService.companyNews("AAPL");
        List<CompanyNews> companyNews = finnhubDtoService.companyNews(jsonstring, "AAPL", true);

        return ResponseEntity.ok().body(companyNews);
    }

    /**
     * Start one time per mounth
     *
     * @return
     * @throws ParseException
     * @throws IOException
     */
    @RequestMapping(value = "/symbols", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Symbols>> symbols() throws ParseException, IOException {

        String jsonstring = finnhubService.companySymbols("US");
        List<Symbols> symbolsList = finnhubDtoService.convertToSymbolList(jsonstring, true);
        //DELETE FROM `symbols` WHERE `description` LIKE '';
        //DELETE FROM `symbols` WHERE `abbreviation` LIKE '%=%';
        //DELETE FROM `symbols` WHERE `abbreviation` LIKE '%-%';
        //DELETE FROM `symbols` WHERE `abbreviation` LIKE '%+%';
        //DELETE FROM `symbols` WHERE `abbreviation` LIKE '%.%';
        //DELETE FROM `symbols` WHERE `abbreviation` LIKE '%^%';
        return ResponseEntity.ok().body(symbolsList);
    }

    //http://localhost:8080/finnhub/symbols2?page=0&limit=20&sort=id,desc
    @RequestMapping(value = "/symbols2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Symbols>> symbolsfFromDb(Pageable pgbl) throws ParseException, IOException {

        Page<Symbols> page = symbolsRepository.findAll(pgbl);
        return ResponseEntity.ok().body(page.getContent());
    }

    @RequestMapping(value = "/fncompany", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<CompanyProfile>> fncompany() {

        List<CompanyProfile> companies = new ArrayList<>();
        Page<Symbols> page = symbolsRepository.findAll(PageRequest.of(8, 1000));
        List<Symbols> symbols = page.getContent();
        Iterator itr = symbols.iterator();
        String abbreviation;
        String jsonstring;
        CompanyProfile company;

        while (itr.hasNext()) {
            try {
                Symbols next = (Symbols) itr.next();
                abbreviation = next.getAbbreviation();
                System.out.println(abbreviation);
                if (companyProfileRepository.findByAbbreviation(abbreviation) == null) {
                    jsonstring = finnhubService.companyProfile(abbreviation);
                    company = finnhubDtoService.convertToCompanyProfile(jsonstring, abbreviation);
                    if (company != null) {
                        companyProfileRepository.save(company);
                    }

                    TimeUnit.MILLISECONDS.sleep(1500);
                }
            } catch (IOException ex) {
                Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex) {
                Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, ex);

            } catch (Exception ex) {
                Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return ResponseEntity.ok().body(companies);
    }

    @RequestMapping(value = "/fnohlc/{symbol}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<String>> getOhlc(@PathVariable(name = "symbol") String symbol) {

        List<String> ohlcSymbols = new ArrayList<>();
            try {
                List<OHLC> ohlc = ohlcRepository.findByAbbreviation(symbol);
                ohlcRepository.deleteAll(ohlc);
                finnhubDtoService.saveOHLC(symbol);
                ohlcSymbols.add(symbol);
            } catch (Exception e) {
                Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, e);
            }
        return ResponseEntity.ok().body(ohlcSymbols);
    }

    /**
     * TODO duplicate this method and find all simbols with connection to
     * company and signals Start duplicated method one time per 4 hours Strat
     * this method one time per mounth
     *
     * @return
     */
    @RequestMapping(value = "/fnsignals", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<FinnhubSignals>> finnhubSignals() {

        List<FinnhubSignals> fnsignals_list = new ArrayList<>();

        List<Symbols> symbols = symbolsRepository.findAllOnlyWithExistingComany();
        Iterator itr = symbols.iterator();
        String abbreviation;
        String jsonstring;
        AggregateIndicators aggregate;
        FinnhubSignals fnsignals;
        while (itr.hasNext()) {
            try {
                Symbols next = (Symbols) itr.next();
                abbreviation = next.getAbbreviation();

                jsonstring = finnhubService.aggregateIndicatorsPerDay(abbreviation);
                aggregate = finnhubDtoService.convertToAggregateIdicators(jsonstring);
                fnsignals = finnhubSignalsRepository.findByAbbreviation(abbreviation);
                System.out.println(fnsignals);
                if (fnsignals == null) {
                    fnsignals = new FinnhubSignals();
                    fnsignals.setCreateDateTime(LocalDateTime.now());
                }
                fnsignals.setAbbreviation(abbreviation);
                fnsignals.setAdx(aggregate.getAdx());
                fnsignals.setBuy(aggregate.getBuy());
                fnsignals.setNeutral(aggregate.getNeutral());
                fnsignals.setSell(aggregate.getSell());
                fnsignals.setSignals(aggregate.getSignal());
                fnsignals.setTrending(aggregate.isTrending());
                fnsignals.setUpdateDateTime(LocalDateTime.now());
                fnsignals = finnhubSignalsRepository.save(fnsignals);

                fnsignals_list.add(fnsignals);
                TimeUnit.MILLISECONDS.sleep(1500);
            } catch (IOException ex) {
                Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex) {
                Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(FinnhubController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ResponseEntity.ok().body(fnsignals_list);
    }
}
