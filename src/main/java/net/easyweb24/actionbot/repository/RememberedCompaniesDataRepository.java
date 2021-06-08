/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.repository;

import java.util.List;
import net.easyweb24.actionbot.entity.RememberedComapnies;
import net.easyweb24.actionbot.entity.RememberedComapniesData;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RememberedCompaniesDataRepository  extends JpaRepository< RememberedComapniesData, Integer>{
    public List<RememberedComapniesData> findByRememberedComapniesId(RememberedComapnies companies);
    public List<RememberedComapniesData> findByRememberedComapniesId(RememberedComapnies companies, Sort sort);
    
    @Query(value = "SELECT MAX(profit) FROM remembered_companies_data WHERE remembered_companies_id = ?1", nativeQuery = true)
    public Double getMaxProfit(Integer remembered_companies_id);
}
