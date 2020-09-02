/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.components;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.easyweb24.actionbot.entity.Symbols;
import net.easyweb24.actionbot.repository.SymbolsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author zbigniewwilgosz
 */
//@Component
public class ScheduledTask {
   
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    @Autowired
    private SymbolsRepository symbolsRepository;

    @Scheduled(fixedRate = 20000)
    public void executeTask() {
        System.out.print("Task one executed at ");
        System.out.println(dateFormat.format(new Date()));
        List<Symbols> symbols = symbolsRepository.findAllOnlyWithExistingComany();
        System.out.println(symbols.size());
    }
}
