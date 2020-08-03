/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.repository;

import java.util.List;
import net.easyweb24.actionbot.entity.CompanyNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author zbigniewwilgosz
 */
@Repository
public interface CompanyNewsRepository extends JpaRepository < CompanyNews, Long > {
    List<CompanyNews> findByAbbreviation(String abbreviation);
    CompanyNews findByNewsId(Long news_id);
}
