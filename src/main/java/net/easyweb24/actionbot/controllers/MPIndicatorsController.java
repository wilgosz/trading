/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.controllers;

import net.easyweb24.actionbot.entity.CompanyProfile;
import net.easyweb24.actionbot.repository.CompanyProfileRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/mp")
public class MPIndicatorsController {
    
    private final CompanyProfileRepository companyProfileRepository;
    
    public MPIndicatorsController(CompanyProfileRepository companyProfileRepository){
        this.companyProfileRepository = companyProfileRepository;
    }
    
    @GetMapping("/{symbol}")
    public String getIndicators(@PathVariable(name = "symbol") String symbol, Model model){
        CompanyProfile company = companyProfileRepository.findByAbbreviation(symbol);
                if (company == null) {
                    company = new CompanyProfile();
                }
        model.addAttribute("title", company.getName());        
        model.addAttribute("symbol", symbol);
        return "mpindicators";
    }
}
