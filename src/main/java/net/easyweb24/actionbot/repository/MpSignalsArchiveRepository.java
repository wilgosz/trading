/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.repository;

import java.time.LocalDate;
import net.easyweb24.actionbot.entity.MpSignalsArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author zbigniewwilgosz
 */
@Repository
public interface MpSignalsArchiveRepository extends JpaRepository < MpSignalsArchive, Long > {
    MpSignalsArchive findByAbbreviation(String abbreviation);
    MpSignalsArchive findByAbbreviationAndDate(String abbreviation, LocalDate date);
}
