/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.controllers;

import java.util.List;
import net.easyweb24.actionbot.entity.CompanyNews;
import net.easyweb24.actionbot.entity.Symbols;
import net.easyweb24.actionbot.repository.CompanyNewsRepository;
import net.easyweb24.actionbot.repository.SymbolsRepository;
import net.easyweb24.actionbot.service.FinnhubDtoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/news")
public class NewsController {
    
    private final FinnhubDtoService finnhubDtoService;
    private final SymbolsRepository symbolsRepository;
    private final CompanyNewsRepository companyNewsRepository;
    
    public NewsController(
            FinnhubDtoService finnhubDtoService,
            SymbolsRepository symbolsRepository,
            CompanyNewsRepository companyNewsRepository
            ){
        
        this.finnhubDtoService = finnhubDtoService;
        this.symbolsRepository = symbolsRepository;
        this.companyNewsRepository = companyNewsRepository;
    }
    
    @GetMapping("/{symbol}")
    public String companyNews(@PathVariable(name = "symbol") String symbol, Model model) {

        model.addAttribute("title", "News");
        Symbols symbols = symbolsRepository.findByAbbreviation(symbol);
        if (symbols == null) {
            model.addAttribute("error", "Symbol does not exists !");
            return "someerror";
        } else {
            try {
                List<CompanyNews> news = finnhubDtoService.saveAndReadNews(symbol, companyNewsRepository, false);
                model.addAttribute("news", news);
            } catch (Exception ex) {
                model.addAttribute("error", "JSON parsing error !");
                return "someerror";
            }
        }
        return "company_news";
    }
}
