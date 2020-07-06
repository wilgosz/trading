/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.controllers;

/**
 *
 * @author zbigniewwilgosz
 */
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import net.easyweb24.actionbot.dto.UserRegistrationDto;
import net.easyweb24.actionbot.service.UserServiceImpl;
import net.easyweb24.actionbot.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping
    public String showRegistrationForm(UserRegistrationDto userRegistrationDto, HttpServletRequest request) {
        if (request.isUserInRole("ROLE_USER")) {
            return "redirect:/";
        }
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@Valid @ModelAttribute("userRegistrationDto") UserRegistrationDto userRegistrationDto, BindingResult errors, RedirectAttributes redirectAttributes) {
        
        userValidator.validate(userRegistrationDto, errors);
        
        if (errors.hasErrors()) {
            return "registration";
        }

        userService.save(userRegistrationDto);
        return "redirect:/login";
        //return "registration2";
    }
}
