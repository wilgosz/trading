/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.easyweb24.actionbot.components.utils.DBToBarSeries;
import net.easyweb24.actionbot.dto.StrategiesDTO;
import net.easyweb24.actionbot.entity.Strategies;
import net.easyweb24.actionbot.repository.StrategiesRepository;
import net.easyweb24.actionbot.rules.MACDRules;
import net.easyweb24.actionbot.rules.MONEYFLOWRules;
import net.easyweb24.actionbot.rules.MPRules;
import net.easyweb24.actionbot.rules.RSIRules;
import net.easyweb24.actionbot.rules.SMARules;
import net.easyweb24.actionbot.rules.STOCHRules;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

@Controller
@RequestMapping("/strategies")
public class StrategiesController {

    private final StrategiesRepository strategiesRepository;
    private final DBToBarSeries dbToBarSeries;

    public StrategiesController(
            StrategiesRepository strategiesRepository,
            DBToBarSeries dbToBarSeries
    ) {
        this.strategiesRepository = strategiesRepository;
        this.dbToBarSeries = dbToBarSeries;
    }

    @GetMapping("")
    public String strategies(Model model) {
        
        List<Strategies> strategies = strategiesRepository.findAll(Sort.by("name"));
        model.addAttribute("title", "User Strategies");
        model.addAttribute("strategies", strategies);
        return "strategies";
    }

    @GetMapping("/st")
    public String st() {

        Strategies strategies = strategiesRepository.findById(7);
        StrategiesDTO dto = new StrategiesDTO();
        try {
            BeanUtils.copyProperties(dto, strategies);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        BarSeries series = dbToBarSeries.getBars("CEF");
        List<MPRules> list = new ArrayList<>();
        list.add(new STOCHRules(new BaseBarSeries(), dto));
        list.add(new SMARules(new BaseBarSeries(), dto));
        list.add(new RSIRules(new BaseBarSeries(), dto));
        list.add(new MONEYFLOWRules(new BaseBarSeries(), dto));
        list.add(new MACDRules(new BaseBarSeries(), dto));
        //StandartRulesGroup group = new StandartRulesGroup(series, list, 120);
        return "st";
    }

}
