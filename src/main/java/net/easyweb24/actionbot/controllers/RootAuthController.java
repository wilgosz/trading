/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.controllers;

import java.util.Optional;
import net.easyweb24.actionbot.utils.CustomUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class RootAuthController {

    private Long user_id = 0L;

    @ModelAttribute
    public void addAttributes(Authentication authentication) {
        user_id = 0L;
        String name = null;
        if (authentication != null) {
            CustomUser user = (CustomUser) authentication.getPrincipal();
            user_id = user.getId();
            name = user.getUsername();
        } else {
            user_id = 0L;
        }
        
        System.out.println("*************************************************************************************** " + name + " *************************************");
        System.out.println("*************************************************************************************** " + user_id + " *************************************");
    }
    
    protected Long getUserId(){
        return user_id;
    }

}
