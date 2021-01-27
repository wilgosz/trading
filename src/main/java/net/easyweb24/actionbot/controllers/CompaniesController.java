/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.servlet.ServletRequest;
import net.easyweb24.actionbot.components.FinnhubComponent;
import net.easyweb24.actionbot.dto.AggregateIndicators;
import net.easyweb24.actionbot.dto.CompanyProfileDTO;
import net.easyweb24.actionbot.entity.CompanyNews;
import net.easyweb24.actionbot.entity.CompanyProfile;
import net.easyweb24.actionbot.entity.Symbols;
import net.easyweb24.actionbot.repository.CompanyNewsRepository;
import net.easyweb24.actionbot.repository.CompanyProfileRepository;
import net.easyweb24.actionbot.repository.SymbolsRepository;
import net.easyweb24.actionbot.service.FinnhubDtoService;
import net.easyweb24.actionbot.service.FinnhubService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author zbigniewwilgosz
 */
@Controller
@RequestMapping("/companies")
public class CompaniesController {
    
    private final CompanyProfileRepository companyProfileRepository;
    private final SymbolsRepository symbolsRepository;
    private final FinnhubService finnhubService;
    private final FinnhubDtoService finnhubDtoService;
    private final FinnhubComponent finnhubComponent;
    private final CompanyNewsRepository companyNewsRepository;
    
    public CompaniesController(
            CompanyProfileRepository companyProfileRepository,
            SymbolsRepository symbolsRepository,
            FinnhubService finnhubService,
            FinnhubDtoService finnhubDtoService,
            FinnhubComponent finnhubComponent,
            CompanyNewsRepository companyNewsRepository){
        
        this.companyProfileRepository = companyProfileRepository;
        this.symbolsRepository = symbolsRepository;
        this.finnhubService = finnhubService;
        this.finnhubDtoService= finnhubDtoService;
        this.finnhubComponent = finnhubComponent;
        this.companyNewsRepository = companyNewsRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String symbols_from_db(Model model, ServletRequest request) throws ParseException, IOException {

        Page<CompanyProfileDTO> page;
        int pagenumber = 0;
        int pagesize = 20;

        if (request.getParameter("page") != null && !request.getParameter("page").equals("")) {
            pagenumber = Integer.parseInt(request.getParameter("page"));
        }

        if (request.getParameter("size") != null && !request.getParameter("size").equals("")) {
            pagesize = Integer.parseInt(request.getParameter("size"));
        }

        Pageable pgbl = PageRequest.of(pagenumber, pagesize, Sort.by("name"));
        if (request.getParameter("letter") != null && !request.getParameter("letter").equals("")) {
            page = companyProfileRepository.getAllCompaniesDescriptionStartingWith(request.getParameter("letter"), pgbl);
        } else {
            page = companyProfileRepository.getAllCompanies(pgbl);
        }

        model.addAttribute("companies", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("title", "Companies");
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

}
