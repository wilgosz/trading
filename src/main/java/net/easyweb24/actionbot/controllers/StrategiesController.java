/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.easyweb24.actionbot.components.utils.DBToBarSeries;
import net.easyweb24.actionbot.dto.IndicatorsDTO;
import net.easyweb24.actionbot.dto.SimulationResults;
import net.easyweb24.actionbot.dto.StrategiesDTO;
import net.easyweb24.actionbot.entity.Strategies;
import net.easyweb24.actionbot.entity.StrategiesIndicators;
import net.easyweb24.actionbot.repository.StrategiesIndicatorsRepository;
import net.easyweb24.actionbot.repository.StrategiesRepository;
import net.easyweb24.actionbot.rules.ADXRules;
import net.easyweb24.actionbot.rules.MACDRules;
import net.easyweb24.actionbot.rules.MONEYFLOWRules;
import net.easyweb24.actionbot.rules.MPRules;
import net.easyweb24.actionbot.rules.RSIRules;
import net.easyweb24.actionbot.rules.SMARules;
import net.easyweb24.actionbot.rules.STOCHRules;
import net.easyweb24.actionbot.service.GroupOfRulesBuilder;
import net.easyweb24.actionbot.service.MpSignalsService;
import net.easyweb24.actionbot.service.RulesSevice;
import net.easyweb24.actionbot.service.StandartRulesGroup;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

@Controller
@RequestMapping("/strategies")
public class StrategiesController extends RootAuthController {

    private final StrategiesRepository strategiesRepository;
    private final DBToBarSeries dbToBarSeries;
    private final MpSignalsService mpSignalsService;
    private final RulesSevice rulesSevice;

    public StrategiesController(
            StrategiesRepository strategiesRepository,
            DBToBarSeries dbToBarSeries,
            MpSignalsService mpSignalsService,
            RulesSevice rulesSevice
    ) {
        this.strategiesRepository = strategiesRepository;
        this.dbToBarSeries = dbToBarSeries;
        this.mpSignalsService = mpSignalsService;
        this.rulesSevice = rulesSevice;
    }

    @GetMapping("")
    public String strategies(Model model) {

        List<Strategies> strategies = strategiesRepository.findByUserId(getUserId(), Sort.by("name"));
        model.addAttribute("title", "User Strategies");
        model.addAttribute("strategies", strategies);
        return "strategies";
    }

    @GetMapping("/simulator/{symbol}/{strategy_id}")
    @ResponseBody
    public Map<String, List> simulator(@PathVariable(name = "symbol") String symbol, @PathVariable(name = "strategy_id") int strategy_id) {

        Strategies strategies = strategiesRepository.findByIdAndUserId(strategy_id, getUserId());
        if (strategies != null) {
            BarSeries series = dbToBarSeries.getBars(symbol);
            List<MPRules> list = rulesSevice.getRulesByStrategyId(series, new BaseBarSeries(), strategies);
            StandartRulesGroup group = new StandartRulesGroup(series, list, series.getBarCount() < 200 ? series.getBarCount() - 20 : 200);
            System.out.println("Start saved");
            mpSignalsService.saveSignals();
            return group.getSimulationResults();
        }
        return new HashMap<String, List>();
    }

}
