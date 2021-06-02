/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.repository;

import java.time.LocalDate;
import java.util.List;
import net.easyweb24.actionbot.entity.OHLC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author zbigniewwilgosz
 */
@Repository
public interface OHLCRepository extends JpaRepository < OHLC, Long > {
    List<OHLC> findByAbbreviation(String abbreviation);
    
    @Query(value = "SELECT COUNT(*) FROM ohlc WHERE abbreviation = ?1", nativeQuery = true)
    Integer getSymbolExists(String abbreviation);
    
    OHLC findByDateAndAbbreviation(LocalDate date, String abbreviation);
    
    @Query(value = "SELECT * FROM ohlc WHERE abbreviation = ?1 AND date >?2 ORDER BY date", nativeQuery = true)
    List<OHLC> getOHLCFromLastYear(String abbreviation, String from);
    
    @Query(value = "SELECT * FROM ohlc WHERE abbreviation = ?1 ORDER BY date DESC LIMIT 1", nativeQuery = true)
    OHLC getLastRecord(String abbreviation);
    
}
