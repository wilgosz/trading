/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.repository;

import java.util.List;
import net.easyweb24.actionbot.dto.FinnhubSignalsDTO;
import net.easyweb24.actionbot.entity.FinnhubSignals;
import net.easyweb24.actionbot.entity.Symbols;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    
    @Query(value = "SELECT f.*, s.desription as description FROM symbols s, finnhub_signals f WHERE s.abbreviation = f.abbreviation AND f.signals = 'strong buy' ORDER BY buy DESC, sell ASC LIMIT 60", nativeQuery = true)
    List<FinnhubSignalsDTO> strongBuyQuery();
    //Page<FinnhubSignals> findByDesriptionStartingWith(String letter, Pageable pglb);
}
