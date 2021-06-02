/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.controllers;

import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletRequest;
import net.easyweb24.actionbot.dto.IndicatorsDTO;
import net.easyweb24.actionbot.dto.StrategiesDTO;
import net.easyweb24.actionbot.entity.CompanyProfile;
import net.easyweb24.actionbot.entity.MpSignals;
import net.easyweb24.actionbot.entity.RememberedComapnies;
import net.easyweb24.actionbot.entity.Strategies;
import net.easyweb24.actionbot.entity.StrategiesIndicators;
import net.easyweb24.actionbot.entity.User;
import net.easyweb24.actionbot.repository.CompanyProfileRepository;
import net.easyweb24.actionbot.repository.MpSignalsRepository;
import net.easyweb24.actionbot.repository.OHLCRepository;
import net.easyweb24.actionbot.repository.RememberedCompaniesRepository;
import net.easyweb24.actionbot.repository.StrategiesIndicatorsRepository;
import net.easyweb24.actionbot.repository.StrategiesRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mp")
public class MPIndicatorsController extends RootAuthController {

    private final CompanyProfileRepository companyProfileRepository;
    private final StrategiesRepository strategiesRepository;
    private final StrategiesIndicatorsRepository strategiesIndicatorsRepository;
    private final MpSignalsRepository mpSignalsRepository;
    private final OHLCRepository oHLCRepository;
    private final RememberedCompaniesRepository rememberedCompaniesRepository;

    public MPIndicatorsController(
            CompanyProfileRepository companyProfileRepository,
            StrategiesRepository strategiesRepository,
            StrategiesIndicatorsRepository strategiesIndicatorsRepository,
            MpSignalsRepository mpSignalsRepository,
            OHLCRepository oHLCRepository,
            RememberedCompaniesRepository rememberedCompaniesRepository
    ) {
        this.companyProfileRepository = companyProfileRepository;
        this.strategiesRepository = strategiesRepository;
        this.strategiesIndicatorsRepository = strategiesIndicatorsRepository;
        this.mpSignalsRepository = mpSignalsRepository;
        this.oHLCRepository = oHLCRepository;
        this.rememberedCompaniesRepository = rememberedCompaniesRepository;
    }

    @GetMapping(value = {"/{symbol}", "/{symbol}/{strategyid}"})
    public String getIndicators(@PathVariable(name = "symbol") String symbol, @PathVariable(name = "strategyid", required = false) Integer strategyid, Model model) {
        int strategie_id = 0;
        StrategiesDTO strategiesDto = new StrategiesDTO();
        if (strategyid != null) {
            strategie_id = strategyid;
            Strategies strategies = strategiesRepository.findByIdAndUserId(strategie_id, getUserId());

            if (strategies != null) {
                try {
                    BeanUtils.copyProperties(strategiesDto, strategies);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(MPIndicatorsController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(MPIndicatorsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                MpSignals signal = mpSignalsRepository.findByAbbreviationAndStrategiesId(symbol, strategies);
                if (signal != null) {
                    model.addAttribute("aggregate", signal);
                }
            }
        }
        CompanyProfile company = companyProfileRepository.findByAbbreviation(symbol);
        if (company == null) {
            company = new CompanyProfile();
        }

        Long to = Instant.now().getEpochSecond();
        Long from = to - (60 * 60 * 24 * 30);

        model.addAttribute("title", company.getName());
        model.addAttribute("company", company);
        model.addAttribute("symbol", symbol);
        model.addAttribute("strategies_select", strategiesRepository.findByUserId(getUserId(), Sort.by("name")));
        model.addAttribute("strategies", strategiesDto);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("ohlc", oHLCRepository.getLastRecord(symbol));

        return "mpindicators";
    }

    @PostMapping("/remember")
    @ResponseBody
    public RememberedComapnies createRememberCompany(ServletRequest request) {

        String abbreviation = request.getParameter("abbreviation");
        Double start_price = Double.parseDouble(request.getParameter("start_price"));
        RememberedComapnies remember = new RememberedComapnies();
        remember.setAbbreviation(abbreviation);
        remember.setActive(true);
        remember.setDate(LocalDate.now());
        remember.setStartPrice(start_price);
        remember.setUserId(getUserId());

        rememberedCompaniesRepository.save(remember);

        return remember;
    }
    
    @PutMapping("/remember")
    @ResponseBody
    public RememberedComapnies updateRememberCompany(ServletRequest request) {

        String abbreviation = request.getParameter("abbreviation");
        RememberedComapnies remember = rememberedCompaniesRepository.findByAbbreviationAndUserIdAndActiveTrue(abbreviation, getUserId());
        remember.setActive(false);

        rememberedCompaniesRepository.save(remember);

        return remember;
    }

    @GetMapping("/remember")
    @ResponseBody
    public RememberedComapnies getRememberCompany(ServletRequest request) {

        String abbreviation = request.getParameter("abbreviation");
        RememberedComapnies remember = rememberedCompaniesRepository.findByAbbreviationAndUserIdAndActiveTrue(abbreviation, getUserId());
        return remember;
    }

    @RequestMapping("/updatestrategyindicators")
    @ResponseBody
    public List<IndicatorsDTO> updateStrategyIdicators(@RequestBody List<IndicatorsDTO> indicators) {

        for (IndicatorsDTO indicator : indicators) {
            StrategiesIndicators strat_indicators = strategiesIndicatorsRepository.findIndicatorsByStrategiesAndAbbreviation(indicator.getAbbreviation(), indicator.getStrategieId());
            if (strat_indicators == null) {
                strat_indicators = new StrategiesIndicators();
            }
            strat_indicators.setActive(indicator.getActive());
            strat_indicators.setBottomBorder(indicator.getBottomBorder());
            strat_indicators.setIndicatorsAbbreviation(indicator.getAbbreviation());
            strat_indicators.setPeriod(indicator.getPeriod());
            strat_indicators.setPeriodLong(indicator.getPeriodLong());
            strat_indicators.setPeriodShort(indicator.getPeriodShort());
            strat_indicators.setStrategiesId(strategiesRepository.findById(indicator.getStrategieId()).get());
            strat_indicators.setTimeRange(0);
            strat_indicators.setTopBorder(indicator.getTopBorder());
            strat_indicators.setReverse(indicator.getReverse());

            strategiesIndicatorsRepository.save(strat_indicators);
        }

        return indicators;
    }

    @RequestMapping("/updatestrategy")
    @ResponseBody
    public SaveUpdateStatus updateStrategy(StrategiesDTO strategies) {

        Strategies st;
        SaveUpdateStatus stat = new SaveUpdateStatus();
        Optional<Strategies> strat = strategiesRepository.findById(strategies.getId());
        if (strat.isPresent()) {
            st = strat.get();
        } else {
            st = new Strategies();
            List<Strategies> strat2 = strategiesRepository.findByName(strategies.getName());
            if (strat2.size() > 0) {
                stat.name_exits = true;
                stat.success = false;
                return stat;
            }
        }

        User user = new User();
        user.setId(getUserId());
        st.setName(strategies.getName());
        st.setSupportTimeRange(strategies.getSupportTimeRange());
        st.setTimeRange(strategies.getTimeRange());
        st.setUser(user);

        strategiesRepository.save(st);

        stat.is_new = strategies.getId() == 0 ? true : false;
        stat.success = true;
        stat.new_id = st.getId();
        return stat;
    }

    private class SaveUpdateStatus {

        private boolean is_new;
        private boolean success;
        private int new_id = 0;
        private boolean name_exits = false;

        public SaveUpdateStatus() {
        }

        /**
         * @return the is_new
         */
        public boolean getIs_new() {
            return is_new;
        }

        /**
         * @param is_new the is_new to set
         */
        public void setIs_new(boolean is_new) {
            this.is_new = is_new;
        }

        /**
         * @return the success
         */
        public boolean getSuccess() {
            return success;
        }

        /**
         * @param success the success to set
         */
        public void setSuccess(boolean success) {
            this.success = success;
        }

        /**
         * @return the new_id
         */
        public int getNew_id() {
            return new_id;
        }

        /**
         * @param new_id the new_id to set
         */
        public void setNew_id(int new_id) {
            this.new_id = new_id;
        }

        /**
         * @param name_exits the name_exits to set
         */
        public void setName_exits(boolean name_exits) {
            this.name_exits = name_exits;
        }

        public boolean getName_exists() {
            return name_exits;
        }

    }
}
