/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletRequest;
import net.easyweb24.actionbot.components.FinnhubComponent;
import net.easyweb24.actionbot.dto.AggregateIndicators;
import net.easyweb24.actionbot.dto.CompanyProfileDTO;
import net.easyweb24.actionbot.entity.CompanyNews;
import net.easyweb24.actionbot.entity.CompanyProfile;
import net.easyweb24.actionbot.entity.Strategies;
import net.easyweb24.actionbot.entity.Symbols;
import net.easyweb24.actionbot.repository.CompanyNewsRepository;
import net.easyweb24.actionbot.repository.CompanyProfileRepository;
import net.easyweb24.actionbot.repository.StrategiesRepository;
import net.easyweb24.actionbot.repository.SymbolsRepository;
import net.easyweb24.actionbot.service.FinnhubDtoService;
import net.easyweb24.actionbot.service.FinnhubService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author zbigniewwilgosz
 */
@Controller
@RequestMapping("/companies")
public class CompaniesController extends RootAuthController {

    private final CompanyProfileRepository companyProfileRepository;
    private final SymbolsRepository symbolsRepository;
    private final FinnhubService finnhubService;
    private final FinnhubDtoService finnhubDtoService;
    private final FinnhubComponent finnhubComponent;
    private final CompanyNewsRepository companyNewsRepository;
    private final StrategiesRepository strategiesRepository;

    public CompaniesController(
            CompanyProfileRepository companyProfileRepository,
            SymbolsRepository symbolsRepository,
            FinnhubService finnhubService,
            FinnhubDtoService finnhubDtoService,
            FinnhubComponent finnhubComponent,
            CompanyNewsRepository companyNewsRepository,
            StrategiesRepository strategiesRepository) {

        this.companyProfileRepository = companyProfileRepository;
        this.symbolsRepository = symbolsRepository;
        this.finnhubService = finnhubService;
        this.finnhubDtoService = finnhubDtoService;
        this.finnhubComponent = finnhubComponent;
        this.companyNewsRepository = companyNewsRepository;
        this.strategiesRepository = strategiesRepository;
    }

    @RequestMapping(value = {"", "/strategy/{id}"}, method = RequestMethod.GET)
    public String symbols_from_db(@PathVariable(name = "id", required = false) Integer id, Model model, ServletRequest request) throws ParseException, IOException {

        Page<CompanyProfileDTO> page;
        int pagenumber = 0;
        int pagesize = 20;
        Strategies current_strategie = null;
        int current_strategie_id = 0;
        boolean show_letters = true;

        List<Strategies> strategies = strategiesRepository.findByUserId(getUserId(), Sort.by("id"));

        if (id == null) {
            if (strategies.size() > 0) {
                current_strategie = strategies.get(0);
            }
        } else {
            current_strategie = strategiesRepository.findByIdAndUserId(id, getUserId());
        }
        if (current_strategie != null) {
            current_strategie_id = current_strategie.getId();

        }

        if (request.getParameter("page") != null && !request.getParameter("page").equals("")) {
            pagenumber = Integer.parseInt(request.getParameter("page"));
        }

        if (request.getParameter("size") != null && !request.getParameter("size").equals("")) {
            pagesize = Integer.parseInt(request.getParameter("size"));
        }

        Pageable pgbl = PageRequest.of(pagenumber, pagesize, Sort.by("name"));
        if (request.getParameter("letter") != null && !request.getParameter("letter").equals("")) {
            page = companyProfileRepository.getAllCompaniesDescriptionStartingWith(request.getParameter("letter"), current_strategie_id, pgbl);
            if (request.getParameter("letter").length() > 1) {
                show_letters = false;
            }
        } else {
            page = companyProfileRepository.getAllCompanies(current_strategie_id, pgbl);
        }

        model.addAttribute("current_strategie_id", current_strategie_id);
        model.addAttribute("companies", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("show_letters", show_letters);
        model.addAttribute("title", "Companies");
        model.addAttribute("strategies", strategies);
        return "companies";
    }

    @GetMapping("/{symbol}")
    public String companyDetail(@PathVariable(name = "symbol") String symbol, Model model) {
        Symbols symbols = symbolsRepository.findByAbbreviation(symbol);

        if (symbols != null) {
            String companyjson;
            String aggregatejson;
            try {
                CompanyProfile company = companyProfileRepository.findByAbbreviation(symbol);
                if (company == null) {
                    company = new CompanyProfile();
                }
                /*if (company == null) {
                    companyjson = finnhubService.companyProfile(symbol);
                    company = finnhubDtoService.convertToCompanyProfile(companyjson, symbol);
                    if (company == null) {
                        company = new CompanyProfile();
                    } else {
                        companyProfileRepository.save(company);
                    }
                }*/
                model.addAttribute("company", company);
                model.addAttribute("title", company.getName());
                model.addAttribute("symbol", symbol);

            } catch (Exception ex) {
                model.addAttribute("company", finnhubComponent.dummyCompany(symbols));

            }

            try {
                aggregatejson = finnhubService.aggregateIndicatorsPerDay(symbol);
                AggregateIndicators aggregate = finnhubDtoService.convertToAggregateIdicators(aggregatejson);
                model.addAttribute("aggregate", aggregate);
            } catch (Exception ex) {
                AggregateIndicators aggregate = new AggregateIndicators();
                aggregate.setAdx(0);
                aggregate.setBuy(0);
                aggregate.setNeutral(0);
                aggregate.setSignal("No data");
                aggregate.setTrending(false);
                model.addAttribute("aggregate", aggregate);
            }

            try {
                List<CompanyNews> news = finnhubDtoService.saveAndReadNews(symbol, companyNewsRepository, true);
                model.addAttribute("news", news);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            model.addAttribute("error", "Symbol does not exists !");
            return "someerror";
        }

        return "company";
    }

    @RequestMapping(value = "/inactive", method = RequestMethod.GET)
    public String getInactiveCompanies(Model model) {
        Strategies current_strategie = null;
        int current_strategie_id = 0;

        List<Strategies> strategies = strategiesRepository.findByUserId(getUserId(), Sort.by("id"));

        if (strategies.size() > 0) {
            current_strategie = strategies.get(0);
            current_strategie_id = current_strategie.getId();
        }
        List<CompanyProfileDTO> inactive = companyProfileRepository.getInactiveComapnies(current_strategie_id);

        model.addAttribute("current_strategie_id", current_strategie_id);
        model.addAttribute("companies", inactive);
        model.addAttribute("title", "Inactive Companies");
        model.addAttribute("strategies", strategies);

        return "comapnies_inactive";
    }

    @DeleteMapping("/inactive/{abbreviation}")
    @ResponseBody
    public ResponseEntity<Void> makeCompanyInactive(@PathVariable(name = "abbreviation") String abbreviation) {
        CompanyProfile company = companyProfileRepository.findByAbbreviation(abbreviation);
        if (company != null) {
            company.setActive(false);
            companyProfileRepository.save(company);
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/active/{abbreviation}")
    @ResponseBody
    public ResponseEntity<Void> makeCompanyActive(@PathVariable(name = "abbreviation") String abbreviation) {
        CompanyProfile company = companyProfileRepository.findByAbbreviation(abbreviation);
        if (company != null) {
            company.setActive(true);
            companyProfileRepository.save(company);
        }
        return ResponseEntity.ok().build();
    }
}
