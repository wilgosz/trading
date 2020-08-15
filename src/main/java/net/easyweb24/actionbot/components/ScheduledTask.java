/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.components;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author zbigniewwilgosz
 */
//@Component
public class ScheduledTask {
   
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void executeTask() {
        System.out.print("Task one executed at ");
        System.out.println(dateFormat.format(new Date()));
    }
    
    @Scheduled(fixedRate = 3000)
    public void executeTask2() {
        System.out.print("Task two one executed at ");
        System.out.println(dateFormat.format(new Date()));
    }
}
