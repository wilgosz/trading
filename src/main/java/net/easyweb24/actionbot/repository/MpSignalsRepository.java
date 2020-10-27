/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.repository;

import java.util.List;
import net.easyweb24.actionbot.dto.FinnhubSignalsDTO;
import net.easyweb24.actionbot.entity.MpSignals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author zbigniewwilgosz
 */
@Repository
public interface MpSignalsRepository extends JpaRepository < MpSignals, Long > {
    MpSignals findByAbbreviation(String abbreviation);
    
    @Query(value = ""
            + " SELECT f.*, f.update_date_time as updatedatetime , s.description as description FROM symbols s, mp_signals f, company_profile cp "
            + " WHERE s.abbreviation = f.abbreviation "
            + " AND cp.abbreviation = f.abbreviation "
            //+ " AND  f.signals = 'strong buy' "
            //+ " AND trending = 1"
            + " ORDER BY buy DESC, sell ASC  LIMIT 12", nativeQuery = true)
    List<FinnhubSignalsDTO> strongBuyQuery();
    
    @Query(value = ""
            + " SELECT f.*, f.update_date_time as updatedatetime , s.description as description FROM symbols s, mp_signals f, company_profile cp "
            + " WHERE s.abbreviation = f.abbreviation "
            + " AND cp.abbreviation = f.abbreviation "
            //+ " AND trending = 1"
            + " ORDER BY buy DESC, sell ASC  LIMIT 100", nativeQuery = true)
    List<FinnhubSignalsDTO> strongBuyQueryForUpdate();
}
