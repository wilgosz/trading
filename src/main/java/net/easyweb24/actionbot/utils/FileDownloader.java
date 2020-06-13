/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author zbigniewwilgosz
 */
public class FileDownloader {
    
    private static String PROJECT_PATH;
    
    public static void downloadFile() throws IOException, ParseException {
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = new Date();
        String dstr = dateFormat.format(date2)+" 23:59:59";
        
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dstr);
        int to = Math.round(date.getTime()/1000);
        int from = to-(60*60*24*365);
        
        if (PROJECT_PATH != null) {
        } else {
            PROJECT_PATH = Paths.get(".").toUri().normalize().getPath();
        }
        System.out.print(PROJECT_PATH);
        File f = new File(PROJECT_PATH + "target/classes/download/"+"AAPL.csv");
        if (!f.exists()) {
            f.createNewFile();
        }
        BufferedInputStream inputStream = new BufferedInputStream(new URL("https://query1.finance.yahoo.com/v7/finance/download/AAPL?period1="+from+"&period2="+to+"&interval=1d&events=history").openStream());
        FileOutputStream fileOS = new FileOutputStream(f);
        byte data[] = new byte[1024];
        int byteContent;
        while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
            fileOS.write(data, 0, byteContent);
        }
        //1591653599
        //1591653632
    }
}
