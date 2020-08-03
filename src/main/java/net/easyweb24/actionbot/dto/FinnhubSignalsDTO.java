/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.dto;


public interface FinnhubSignalsDTO{

    public Integer getId();
    public String getAbbreviation();
    public Integer getBuy();
    public Integer getNeutral();
    public Integer getSell();
    public String getSignals();
    public Double getAdx();
    public boolean getTrending();
    public String getDescription();
}
