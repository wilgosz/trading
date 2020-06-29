/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.controllers;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author zbigniewwilgosz
 */
@Controller
public class HomeController {
    
    @GetMapping("/")
    public String root(Principal principal) {
        System.out.println( principal.getName() );
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        if(request.isUserInRole("ROLE_USER")){
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }
}
