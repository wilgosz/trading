/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.controllers;

import java.util.List;

/**
 *
 * @author zbigniewwilgosz
 */
    public class SupportResponse{
        
        public SupportResponse(){
        }
        private List<Float> levels;

        /**
         * @return the levels
         */
        public List<Float> getLevels() {
            return levels;
        }

        /**
         * @param levels the levels to set
         */
        public void setLevels(List<Float> levels) {
            this.levels = levels;
        }
        
    }
