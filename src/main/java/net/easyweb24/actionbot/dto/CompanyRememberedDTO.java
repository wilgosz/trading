/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.dto;

import java.time.LocalDate;


public interface CompanyRememberedDTO extends CompanyProfileDTO{

    public Double getStartPrice();
    public Double getProfit();
    public LocalDate getDate();
    public Boolean getActiveWatch();
    public Integer getRid();
}
