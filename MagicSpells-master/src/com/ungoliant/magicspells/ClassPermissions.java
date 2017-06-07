/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungoliant.magicspells;

import java.util.*;

/**
 *
 * @author Travis
 */
public class ClassPermissions {
//    protected String[] warrior = {
//        "fireball",
//        "freeze",
//        "lightning",
//        "blink",
//    };
    protected String[] mage = {
        "fireball",
        "freeze",
        "lightning",
        "blink",
    };
//    protected String[] ranger = {
//        "fireball",
//        "freeze",
//        "lightning",
//        "blink",
//    };
//    protected String[] cleric = {
//        "fireball",
//        "freeze",
//        "lightning",
//        "blink",
//    };
    protected String[] ward = {
        "invulnerability",
        "cleanse",
        "empower",
        "reflect",
    };
//    protected String[] rogue = {
//        "fireball",
//        "freeze",
//        "lightning",
//        "blink",
//    };    
//    private String[] playerClass = {"warrior","mage","ranger","cleric","ward","rogue"};
    
    protected Map<String,String> allSpells = new HashMap<String,String>();
    
    
    public ClassPermissions() {
//        for(String spellName : warrior) {
//            spellMap.put(spellName, "warrior");
//        }
        for(String spellName : mage) {
            allSpells.put(spellName, "mage");
        }
        for(String spellName : ward) {
            allSpells.put(spellName, "ward");
        }
    }
    
}
