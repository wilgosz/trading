/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.service;

import java.util.ArrayList;
import java.util.List;
import net.easyweb24.actionbot.entity.RememberedComapnies;
import net.easyweb24.actionbot.entity.RememberedComapniesData;
import net.easyweb24.actionbot.repository.RememberedCompaniesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RememberedCompaniesService {

    public final RememberedCompaniesRepository rememberedComapniesCompaniesRepository;

    public RememberedCompaniesService(RememberedCompaniesRepository rememberedComapniesCompaniesRepository) {
        this.rememberedComapniesCompaniesRepository = rememberedComapniesCompaniesRepository;
    }
    
    @Transactional
    public List<RememberedComapnies> addTicks(){
        List<RememberedComapnies> list = rememberedComapniesCompaniesRepository.findByActiveTrue();
        return list;
    } 
}
