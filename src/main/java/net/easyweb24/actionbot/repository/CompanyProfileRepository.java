/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.repository;

import java.util.List;
import net.easyweb24.actionbot.entity.CompanyProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import net.easyweb24.actionbot.dto.CompanyProfileDTO;

/**
 *
 * @author zbigniewwilgosz
 */
@Repository
public interface CompanyProfileRepository extends JpaRepository < CompanyProfile, Long > {
    CompanyProfile findByAbbreviation(String abbreviation);
    
    @Query(value = "SELECT c.*, fs.buy, fs.neutral, fs.sell "
            + " FROM company_profile c "
            + " LEFT JOIN mp_signals fs ON c.abbreviation = fs.abbreviation AND fs.strategies_id = ?1",
            countQuery = "SELECT COUNT(*) "
            + " FROM company_profile c "
            + " LEFT JOIN mp_signals fs ON c.abbreviation = fs.abbreviation AND fs.strategies_id = ?1 ",
            nativeQuery = true)
    Page<CompanyProfileDTO> getAllCompanies(int strategies_id, Pageable pageable);
    
    
    @Query(value = "SELECT c.*,  fs.buy, fs.neutral, fs.sell "
            + " FROM company_profile c "
            + " LEFT JOIN mp_signals fs ON c.abbreviation = fs.abbreviation AND fs.strategies_id = ?2 "
            + " WHERE (c.name LIKE ?1% OR c.abbreviation LIKE ?1%)",
            countQuery = "SELECT COUNT(*) "
            + " FROM company_profile c "
            + " LEFT JOIN mp_signals fs ON c.abbreviation = fs.abbreviation AND fs.strategies_id = ?2 "
            + " WHERE c.name LIKE ?1% OR c.abbreviation LIKE ?1%",
            nativeQuery = true)
    Page<CompanyProfileDTO> getAllCompaniesDescriptionStartingWith(String name, int strategies_id, Pageable pageable);
    
}
