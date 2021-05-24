/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.easyweb24.actionbot.components.utils.DBToBarSeries;
import net.easyweb24.actionbot.controllers.HomeController;
import net.easyweb24.actionbot.dto.IndicatorsDTO;
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
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.ta4j.core.BarSeries;

@Service
public class RulesSevice {

    private final StrategiesIndicatorsRepository strategiesIndicatorsRepository;

    public RulesSevice(
            StrategiesIndicatorsRepository strategiesIndicatorsRepository
    ) {
        this.strategiesIndicatorsRepository = strategiesIndicatorsRepository;
    }

    public List<MPRules> getRulesByStrategyId(BarSeries series, Strategies strategies) {
        return getRulesByStrategyId(series, null, strategies);
    }

    /**
     *
     * @param series
     * @param strategies
     * @return list with MpRules
     *
     * all list's elements after return should by conncted with really BarSeries
     * This method is used to lange Simulation
     */
    public List<MPRules> getRulesByStrategyId(BarSeries series, BarSeries emptyseries, Strategies strategies) {

        if (emptyseries == null) {
            emptyseries = series;
        }

        List<MPRules> list = new ArrayList<>();

        try {

            List<StrategiesIndicators> indicatorsInStrategy = strategiesIndicatorsRepository.searchByStrategiesIdAndActiveIsTrue(strategies.getId());
            StrategiesDTO dto = new StrategiesDTO();
            try {
                BeanUtils.copyProperties(dto, strategies);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (StrategiesIndicators indicator : indicatorsInStrategy) {
                switch (indicator.getIndicatorsAbbreviation()) {
                    case "BOLLINGER":
                        break;
                    case "STOCHASTIC_SLOW":
                        list.add(new STOCHRules(emptyseries, dto));
                        break;
                    case "RSI":
                        list.add(new RSIRules(emptyseries, dto));
                        break;
                    case "MACD":
                        list.add(new MACDRules(emptyseries, dto));
                        break;
                    case "MONEYFLOW":
                        list.add(new MONEYFLOWRules(emptyseries, dto));
                        break;
                    case "SMA":
                        list.add(new SMARules(emptyseries, dto));
                        break;
                    case "ADX":
                        list.add(new ADXRules(emptyseries, dto));
                        break;
                }
            }
            IndicatorsDTO ind2 = new IndicatorsDTO();
            ind2.setAbbreviation("MACD2");
            ind2.setActive(false);
            ind2.setReverse(true);
            ind2.setBottomBorder(0);
            ind2.setPeriod(0);
            ind2.setPeriodLong(series.getBarCount() < 80 ? series.getBarCount() - 10 : 80);
            ind2.setPeriodShort(series.getBarCount() < 60 ? series.getBarCount() - 20 : 60);
            ind2.setStrategieId(strategies.getId());
            ind2.setTopBorder(0);

            list.add(new MACDRules(emptyseries, null, ind2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

}
