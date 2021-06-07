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
import net.easyweb24.actionbot.dto.CompanyRememberedDTO;
import net.easyweb24.actionbot.entity.RememberedComapnies;
import net.easyweb24.actionbot.entity.Strategies;
import net.easyweb24.actionbot.entity.StrategiesIndicators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RememberedCompaniesRepository extends JpaRepository< RememberedComapnies, Integer> {

    public RememberedComapnies findByAbbreviationAndUserIdAndActiveTrue(String abbreviation, Long user_id);
    public RememberedComapnies findByIdAndUserId(int id, Long user_id);
    public List<RememberedComapnies> findByActiveTrue();
    
    @Query(value = ""
            + "(SELECT c.*, rc.date, rc.start_price as startPrice, rc.profit, rc.active as activeWatch, rc.id as rid "
            + "FROM "
            + "company_profile c, "
            + "remembered_companies rc  "
            + "WHERE c.abbreviation = rc.abbreviation "
            + "AND rc.user_id = ?1 "
            + " "
            + "ORDER BY rc.active DESC, rc.date DESC)", nativeQuery = true)
    public List<CompanyRememberedDTO> getAllWitchDetail(Long user_id);
}
