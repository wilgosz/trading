/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import net.easyweb24.actionbot.components.FinnhubComponent;
import net.easyweb24.actionbot.dto.AggregateIndicators;
import net.easyweb24.actionbot.dto.CompanyNews;
import net.easyweb24.actionbot.dto.CompanyProfile;
import net.easyweb24.actionbot.entity.Symbols;
import net.easyweb24.actionbot.repository.SymbolsRepository;
import net.easyweb24.actionbot.service.FinnhubDtoService;
import net.easyweb24.actionbot.service.FinnhubService;
import net.easyweb24.actionbot.utils.CustomUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author zbigniewwilgosz
 */
@Controller
public class HomeController {

    private final FinnhubService finnhubService;
    private final FinnhubDtoService finnhubDtoService;
    private final FinnhubComponent finnhubComponent;
    private final SymbolsRepository symbolsRepository;

    public HomeController(FinnhubService finnhubService, FinnhubDtoService finnhubDtoService, SymbolsRepository symbolsRepository, FinnhubComponent finnhubComponent) {

        this.finnhubService = finnhubService;
        this.finnhubDtoService = finnhubDtoService;
        this.symbolsRepository = symbolsRepository;
        this.finnhubComponent = finnhubComponent;
    }

    @GetMapping("/")
    public String root(Authentication authentication, Model model) {

        /*CustomUser user = (CustomUser) authentication.getPrincipal();
        System.out.println( user.getId());
        System.out.println( user.getUsername());
        model.addAttribute("title", "Dashboard");*/
        String jsonstring;
        try {

            jsonstring = finnhubService.companyProfile("BPMC");
            CompanyProfile company = finnhubDtoService.convertToCompanyProfile(jsonstring);
            model.addAttribute("company", company);

        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "index";
    }

    @RequestMapping(value = "/companies", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String symbols_from_db(Model model, ServletRequest request) throws ParseException, IOException {

        Page<Symbols> page;
        int pagenumber = 0;
        int pagesize = 20;

        if (request.getParameter("page") != null) {
            pagenumber = Integer.parseInt(request.getParameter("page"));
        }

        if (request.getParameter("size") != null) {
            pagesize = Integer.parseInt(request.getParameter("size"));
        }

        Pageable pgbl = PageRequest.of(pagenumber, pagesize, Sort.by("desription"));
        if (request.getParameter("letter") != null) {
            page = symbolsRepository.findByDesriptionStartingWith(request.getParameter("letter"), pgbl);
        } else {
            page = symbolsRepository.findAll(pgbl);
        }

        model.addAttribute("companies", page.getContent());
        model.addAttribute("page", page);
        return "companies";
    }

    @GetMapping("/companies/{symbol}")
    public String companyDetail(@PathVariable(name = "symbol") String symbol, Model model) {
        Symbols symbols = symbolsRepository.findByAbbreviation(symbol);

        if (symbols != null) {
            String companyjson;
            String aggregatejson;
            try {

                companyjson = finnhubService.companyProfile(symbol);
                CompanyProfile company = finnhubDtoService.convertToCompanyProfile(companyjson);
                model.addAttribute("company", company);
                model.addAttribute("symbol", symbol);
                

            } catch (Exception ex) {
                model.addAttribute("company", finnhubComponent.dummyCompany(symbols));

            }
            
            try{
                aggregatejson = finnhubService.aggregateIndicatorsPerDay(symbol);
                AggregateIndicators aggregate = finnhubDtoService.convertToAggregateIdicators(aggregatejson);
                model.addAttribute("aggregate", aggregate);
            }catch(Exception ex){
                
            }
            
            try{
                aggregatejson = finnhubService.companyNews(symbol);
                List<CompanyNews> news = finnhubDtoService.companyNews(aggregatejson, true);
                model.addAttribute("news", news);
            }catch(Exception ex){
                
            }
        } else {
            model.addAttribute("error", "Symbol does not exists !");
            return "someerror";
        }

        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        if (request.isUserInRole("ROLE_USER")) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }
}
