/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.repository;

/**
 *
 * @author zbigniewwilgosz
 */
import java.util.List;
import net.easyweb24.actionbot.entity.Strategies;
import net.easyweb24.actionbot.entity.StrategiesIndicators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface StrategiesIndicatorsRepository extends JpaRepository < StrategiesIndicators, Integer > {
    
    @Query(value = "SELECT s.* FROM strategies_indicators s WHERE s.indicators_abbreviation  = ?1 AND s.strategies_id = ?2", nativeQuery = true)
    StrategiesIndicators findIndicatorsByStrategiesAndAbbreviation(String abbreviation, int strategies_id);
    @Query(value = "SELECT s.* FROM strategies_indicators s WHERE s.strategies_id = ?1 AND active=true", nativeQuery = true)
    List<StrategiesIndicators> searchByStrategiesIdAndActiveIsTrue(int strategies_id);
    List<StrategiesIndicators> findByStrategiesId(Strategies strategies);
    
    
}
