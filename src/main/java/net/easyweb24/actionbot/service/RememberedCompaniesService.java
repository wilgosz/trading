/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import net.easyweb24.actionbot.entity.MpSignals;
import net.easyweb24.actionbot.entity.OHLC;
import net.easyweb24.actionbot.entity.RememberedComapnies;
import net.easyweb24.actionbot.entity.RememberedComapniesData;
import net.easyweb24.actionbot.entity.Strategies;
import net.easyweb24.actionbot.repository.MpSignalsRepository;
import net.easyweb24.actionbot.repository.OHLCRepository;
import net.easyweb24.actionbot.repository.RememberedCompaniesDataRepository;
import net.easyweb24.actionbot.repository.RememberedCompaniesRepository;
import net.easyweb24.actionbot.repository.StrategiesRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RememberedCompaniesService {

    public final RememberedCompaniesRepository rememberedComapniesRepository;
    public final RememberedCompaniesDataRepository rememberedCompaniesDataRepository;
    public final OHLCRepository oHLCRepository;
    public final MpSignalsRepository mpSignalsRepository;
    public final StrategiesRepository strategiesRepository;

    public RememberedCompaniesService(
            RememberedCompaniesRepository rememberedComapniesRepository,
            RememberedCompaniesDataRepository rememberedCompaniesDataRepository,
            OHLCRepository oHLCRepository,
            MpSignalsRepository mpSignalsRepository,
            StrategiesRepository strategiesRepository) {
        this.rememberedComapniesRepository = rememberedComapniesRepository;
        this.rememberedCompaniesDataRepository = rememberedCompaniesDataRepository;
        this.oHLCRepository = oHLCRepository;
        this.mpSignalsRepository = mpSignalsRepository;
        this.strategiesRepository = strategiesRepository;
    }
    
    @Transactional
    public List<RememberedComapnies> addTicks(){
        List<RememberedComapnies> list = rememberedComapniesRepository.findByActiveTrue();
        for(RememberedComapnies companies: list){
            RememberedComapniesData data = new RememberedComapniesData();
            OHLC ohlc = oHLCRepository.getLastRecord(companies.getAbbreviation());
            List<Strategies> strategies = strategiesRepository.findByUserId(companies.getUserId(), Sort.by("id"));
            data.setRememberedComapniesId(companies);
            data.setPrice(ohlc.getC());
            data.setDate(LocalDateTime.now());
            if(strategies.size()>0){
               Strategies strategie = strategies.get(0);
               double profit = getProfit(companies.getStartPrice(), ohlc.getC());
               data.setProfit(profit);
               MpSignals signal = mpSignalsRepository.findByAbbreviationAndStrategiesId(companies.getAbbreviation(), strategie);
               if(signal != null){
                   data.setBuy(signal.getBuy());
                   data.setNeutral(signal.getNeutral());
                   data.setSell(signal.getSell());
               }
               rememberedCompaniesDataRepository.save(data);
               
               companies.setProfit(profit);
               rememberedComapniesRepository.save(companies);
            }else{
               //rememberedCompaniesDataRepository.deleteAll(rememberedCompaniesDataRepository.findByRememberedComapniesId(companies));
               //rememberedComapniesRepository.delete(companies);
               companies.setActive(Boolean.FALSE);
               rememberedComapniesRepository.save(companies);
            }
            
        }
        return list;
    }
    
    private double getProfit(double old_price, double new_price){
        
        double profit = 0.0;
        
        if(old_price < new_price){
            profit = (( new_price - old_price )/old_price)*100;
        }else if(old_price > new_price){
            profit = -((( old_price - new_price )/old_price)*100);
        }
        
        return profit;
    }
}
