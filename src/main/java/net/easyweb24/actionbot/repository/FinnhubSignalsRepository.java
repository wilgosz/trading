/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.repository;

import java.util.List;
import net.easyweb24.actionbot.dto.FinnhubSignalsDTO;
import net.easyweb24.actionbot.entity.FinnhubSignals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author zbigniewwilgosz
 */
@Repository
public interface FinnhubSignalsRepository extends JpaRepository < FinnhubSignals, Long > {
    FinnhubSignals findByAbbreviation(String abbreviation);
    
    @Query(value = ""
            + " SELECT f.*, f.update_date_time as updatedatetime , s.description as description FROM symbols s, finnhub_signals f, company_profile cp "
            + " WHERE s.abbreviation = f.abbreviation "
            + " AND cp.abbreviation = f.abbreviation "
            + " AND  f.signals = 'strong buy' "
            //+ " AND trending = 1"
            + " ORDER BY sell ASC, buy DESC  LIMIT 48", nativeQuery = true)
    List<FinnhubSignalsDTO> strongBuyQuery();
    
    @Query(value = ""
            + " SELECT f.*, f.update_date_time as updatedatetime , s.description as description FROM symbols s, finnhub_signals f, company_profile cp "
            + " WHERE s.abbreviation = f.abbreviation "
            + " AND cp.abbreviation = f.abbreviation "
            //+ " AND trending = 1"
            + " ORDER BY sell ASC, buy DESC  LIMIT 300", nativeQuery = true)
    List<FinnhubSignalsDTO> strongBuyQueryForUpdate();
    //Page<FinnhubSignals> findByDescriptionStartingWith(String letter, Pageable pglb);
}
