/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import net.easyweb24.actionbot.dto.FinnhubSignalsDTO;
import net.easyweb24.actionbot.entity.Strategies;
import net.easyweb24.actionbot.repository.FinnhubSignalsRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import net.easyweb24.actionbot.repository.MpSignalsRepository;
import net.easyweb24.actionbot.repository.StrategiesRepository;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author zbigniewwilgosz
 */
@Controller
public class HomeController extends RootAuthController {

    private final StrategiesRepository strategiesRepository;
    private final MpSignalsRepository mpSignalsRepository;

    public HomeController(
            StrategiesRepository strategiesRepository,
            MpSignalsRepository mpSignalsRepository
    ) {

        this.strategiesRepository = strategiesRepository;
        this.mpSignalsRepository = mpSignalsRepository;
    }

    @GetMapping({"/", "/dashboard/{id}"})
    public String root(@PathVariable(required = false) Integer id, Model model) {

        List<FinnhubSignalsDTO> mplist;
        Strategies current_strategie = null;
        int current_strategie_id = 0;
        List<Strategies> strategies = strategiesRepository.findByUserId(getUserId(), Sort.by("id"));
        if (id == null) {
            if (strategies.size() > 0) {
                current_strategie = strategies.get(0);
            }
        }else{
            Optional<Strategies> current_strategie_opt = strategiesRepository.findById(id);
            if(current_strategie_opt.isPresent()){
                current_strategie = current_strategie_opt.get();
            }
        }
        
        if(current_strategie != null){
            current_strategie_id = current_strategie.getId();
            mplist = mpSignalsRepository.strongBuyQuery(current_strategie_id);
            
        }else{
            mplist = new ArrayList<>();
        }
        
        model.addAttribute("title", "Dashboard");
        model.addAttribute("mpsignals", mplist);
        model.addAttribute("strategies", strategies);
        model.addAttribute("current_strategie_id", current_strategie_id);
        return "index";
    }

    @GetMapping("/actions")
    public String actions(Model model) {
        model.addAttribute("title", "Current Actions");
        return "actions";
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
