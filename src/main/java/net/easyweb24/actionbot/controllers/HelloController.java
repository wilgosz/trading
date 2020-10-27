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
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import net.easyweb24.actionbot.components.utils.DBToBarSeries;
import net.easyweb24.actionbot.entity.MpSignals;
import net.easyweb24.actionbot.entity.MpSignalsArchive;
import net.easyweb24.actionbot.entity.Strategies;
import net.easyweb24.actionbot.entity.Symbols;
import net.easyweb24.actionbot.entity.User;
import net.easyweb24.actionbot.repository.MpSignalsArchiveRepository;
import net.easyweb24.actionbot.repository.MpSignalsRepository;
import net.easyweb24.actionbot.repository.SymbolsRepository;
import net.easyweb24.actionbot.repository.UserRepository;
import net.easyweb24.actionbot.rules.MACDRules;
import net.easyweb24.actionbot.rules.MONEYFLOWRules;
import net.easyweb24.actionbot.rules.MPRules;
import net.easyweb24.actionbot.rules.RSIRules;
import net.easyweb24.actionbot.rules.SMARules;
import net.easyweb24.actionbot.rules.STOCHRules;
import net.easyweb24.actionbot.service.MpSignalsService;
import net.easyweb24.actionbot.utils.FileDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.Strategy;

@Controller
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private MpSignalsService mpSignalsService;

    @RequestMapping("/")
    @ResponseBody
    public String index(HttpServletRequest request) throws ParseException {

        try {
            FileDownloader.downloadFile(request);
        } catch (IOException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
        }
        return "Greetings Man";
    }

    @GetMapping("/save")
    @ResponseBody
    public String save(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {

        return "saved";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/test")
    @ResponseBody
    public ResponseEntity<String> test() {
        
        mpSignalsService.saveSignals();
        return ResponseEntity.ok().body("TEST");
    }

}
