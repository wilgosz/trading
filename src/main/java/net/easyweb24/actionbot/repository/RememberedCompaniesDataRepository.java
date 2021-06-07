/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.repository;

import net.easyweb24.actionbot.entity.RememberedComapniesData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RememberedCompaniesDataRepository  extends JpaRepository< RememberedComapniesData, Integer>{
    
}
