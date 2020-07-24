/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.easyweb24.actionbot.dto.AggregateIndicators;
import net.easyweb24.actionbot.dto.CompanyNews;
import net.easyweb24.actionbot.dto.CompanyProfile;
import net.easyweb24.actionbot.dto.OHLC;
import net.easyweb24.actionbot.entity.FinnhubSignals;
import net.easyweb24.actionbot.entity.Symbols;
import net.easyweb24.actionbot.repository.FinnhubSignalsRepository;
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

    public FinnhubController(
            SymbolsRepository symbolsRepository,
            FinnhubService finnhubService,
            FinnhubDtoService finnhubDtoService,
            FinnhubSignalsRepository finnhubSignalsRepository) {
        this.symbolsRepository = symbolsRepository;
        this.finnhubService = finnhubService;
        this.finnhubDtoService = finnhubDtoService;
        this.finnhubSignalsRepository = finnhubSignalsRepository;
    }

    @RequestMapping(value = "/ohlc/{symbol}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<OHLC>> index(@PathVariable(name = "symbol") String symbol) throws ParseException, IOException {

        String jsonstring = finnhubService.stockCandlesFromLastYear(symbol);
        List<OHLC> OhlcList = finnhubDtoService.convertToOhlcList(jsonstring);

        return ResponseEntity.ok().body(OhlcList);
    }

    @RequestMapping(value = "/company", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CompanyProfile> company() throws ParseException, IOException {

        String jsonstring = finnhubService.companyProfile("AMZN");
        CompanyProfile company = finnhubDtoService.convertToCompanyProfile(jsonstring);

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
        List<CompanyNews> companyNews = finnhubDtoService.companyNews(jsonstring, true);

        return ResponseEntity.ok().body(companyNews);
    }

    @RequestMapping(value = "/symbols", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Symbols>> symbols() throws ParseException, IOException {
        //DELETE FROM `symbols` WHERE `desription` LIKE '';
        //DELETE FROM `symbols` WHERE `abbreviation` LIKE '%=%';
        //DELETE FROM `symbols` WHERE `abbreviation` LIKE '%-%'
        //DELETE FROM `symbols` WHERE `abbreviation` LIKE '%+%'
        //DELETE FROM `symbols` WHERE `abbreviation` LIKE '%.%'
        String jsonstring = finnhubService.companySymbols("US");
        List<Symbols> symbolsList = finnhubDtoService.convertToSymbolList(jsonstring, true);
        return ResponseEntity.ok().body(symbolsList);
    }

    //http://localhost:8080/finnhub/symbols2?page=0&limit=20&sort=id,desc
    @RequestMapping(value = "/symbols2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Symbols>> symbolsfFromDb(Pageable pgbl) throws ParseException, IOException {

        Page<Symbols> page = symbolsRepository.findAll(pgbl);
        return ResponseEntity.ok().body(page.getContent());
    }

    @RequestMapping(value = "/fnsignals", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<FinnhubSignals>> finnhubSignals() {

        List<FinnhubSignals> fnsignals_list = new ArrayList<>();

        Page<Symbols> page = symbolsRepository.findAll(PageRequest.of(1, 4000));
        List<Symbols> symbols = page.getContent();
        Iterator itr = symbols.iterator();
        String abbreviation;
        String jsonstring;
        AggregateIndicators aggregate;
        FinnhubSignals fnsignals;
        System.out.println(symbols.size());
        while (itr.hasNext()) {
            try {
                Symbols next = (Symbols) itr.next();
                abbreviation = next.getAbbreviation();

                jsonstring = finnhubService.aggregateIndicatorsPerDay(abbreviation);
                aggregate = finnhubDtoService.convertToAggregateIdicators(jsonstring);
                fnsignals = finnhubSignalsRepository.findByAbbreviation(abbreviation);

                if (fnsignals == null) {
                    fnsignals = new FinnhubSignals();
                    fnsignals.setAbbreviation(abbreviation);
                    fnsignals.setAdx(aggregate.getAdx());
                    fnsignals.setBuy(aggregate.getBuy());
                    fnsignals.setNeutral(aggregate.getNeutral());
                    fnsignals.setSell(aggregate.getSell());
                    fnsignals.setSignals(aggregate.getSignal());
                    fnsignals.setTrending(aggregate.isTrending());
                    fnsignals = finnhubSignalsRepository.save(fnsignals);
                }
                fnsignals_list.add(fnsignals);
                TimeUnit.SECONDS.sleep(1);
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
