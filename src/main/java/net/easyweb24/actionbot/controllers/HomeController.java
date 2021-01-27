/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.controllers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.easyweb24.actionbot.dto.FinnhubSignalsDTO;
import net.easyweb24.actionbot.repository.FinnhubSignalsRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import net.easyweb24.actionbot.repository.MpSignalsRepository;

/**
 *
 * @author zbigniewwilgosz
 */
@Controller
public class HomeController {

    private final FinnhubSignalsRepository finnhubSignalsRepository;
    private final MpSignalsRepository mpSignalsRepository;

    public HomeController(
            FinnhubSignalsRepository finnhubSignalsRepository,
            MpSignalsRepository mpSignalsRepository
    ) {

        this.finnhubSignalsRepository = finnhubSignalsRepository;
        this.mpSignalsRepository = mpSignalsRepository;
    }

    @GetMapping("/")
    public String root(Authentication authentication, Model model) {

        /*CustomUser user = (CustomUser) authentication.getPrincipal();
        System.out.println( user.getId());
        System.out.println( user.getUsername());*/
        model.addAttribute("title", "Dashboard");
        List<FinnhubSignalsDTO> signalslist = finnhubSignalsRepository.strongBuyQuery();
        List<FinnhubSignalsDTO> mplist = mpSignalsRepository.strongBuyQuery();

        model.addAttribute("signals", signalslist);
        model.addAttribute("mpsignals", mplist);
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
