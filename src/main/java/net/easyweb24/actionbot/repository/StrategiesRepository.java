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
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StrategiesRepository extends JpaRepository < Strategies, Integer > {
    Strategies findById(int id);
    List<Strategies> findByName(String name);
    List<Strategies> findByUserId(Long user_id, Sort sort);
    Strategies findByIdAndUserId(int id, Long user_id);
}
