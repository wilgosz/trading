/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.repository;

import net.easyweb24.actionbot.entity.Exchanges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author zbigniewwilgosz
 */
@Repository
public interface ExchangesRepository extends JpaRepository < Exchanges, Long > {
    Exchanges findByCode(String code);
}
