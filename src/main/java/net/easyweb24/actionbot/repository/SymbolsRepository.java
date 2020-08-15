package net.easyweb24.actionbot.repository;

import java.util.List;
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
public interface SymbolsRepository extends JpaRepository < Symbols, Long > {
    Symbols findByAbbreviation(String abbreviation);
    Page<Symbols> findByDescriptionStartingWith(String letter, Pageable pglb);
    
    @Query(value = "SELECT s.* FROM symbols s, company_profile c WHERE s.abbreviation = c.abbreviation LIMIT 10", nativeQuery = true)
    List<Symbols> findAllOnlyWithExistingComany();
}
