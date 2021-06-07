/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.repository;

import java.util.List;
import net.easyweb24.actionbot.dto.FinnhubSignalsDTO;
import net.easyweb24.actionbot.entity.MpSignals;
import net.easyweb24.actionbot.entity.Strategies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author zbigniewwilgosz
 */
@Repository
public interface MpSignalsRepository extends JpaRepository < MpSignals, Long > {
    List<MpSignals> findByAbbreviation(String abbreviation);
    MpSignals findByAbbreviationAndStrategiesId(String abbreviation, Strategies strategies);
    
    @Query(value = ""
            + " SELECT f.*, f.update_date_time as updatedatetime, f.strategies_id as strategiesid , cp.active, s.description as description FROM symbols s, mp_signals f, company_profile cp "
            + " WHERE s.abbreviation = f.abbreviation "
            + " AND cp.abbreviation = f.abbreviation "
            + " AND cp.active = true "
            + " AND f.strategies_id = ?1 "
            //+ " AND  f.signals = 'strong buy' "
            //+ " AND trending = 1"
            + " ORDER BY buy DESC, sell ASC  LIMIT 48", nativeQuery = true)
    List<FinnhubSignalsDTO> strongBuyQuery(int strategies_id);
    
    @Query(value = ""
            + " SELECT f.*, f.update_date_time as updatedatetime, f.strategies_id as strategiesid, cp.active, s.description as description FROM symbols s, mp_signals f, company_profile cp "
            + " WHERE s.abbreviation = f.abbreviation "
            + " AND cp.abbreviation = f.abbreviation "
            + " AND cp.active = true "
            //+ " AND trending = 1"
            + " GROUP BY cp.abbreviation "
            + " ORDER BY buy DESC, sell ASC  LIMIT 500", nativeQuery = true)
    List<FinnhubSignalsDTO> strongBuyQueryForUpdate();
    
    List<MpSignals> findByStrategiesId(Strategies strategies);
}
/**
 * SELECT f.*, f.update_date_time as updatedatetime, f.strategies_id as strategiesid, cp.active, s.description as description FROM symbols s, mp_signals f, company_profile cp 
WHERE s.abbreviation = f.abbreviation AND cp.abbreviation = f.abbreviation AND cp.active = true 
GROUP BY cp.abbreviation
ORDER BY buy DESC, sell ASC LIMIT 500
 */