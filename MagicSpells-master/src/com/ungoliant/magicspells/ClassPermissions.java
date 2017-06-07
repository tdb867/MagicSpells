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
    //Each class gets two instant spells, a targeted spell, and a buff spell
    //This was arbitrarily decided by tbordovsky
    //spell names defined in .src\spells-regular.yml
    protected String[] warrior = {
        "leap",
        "cripple",
        "disarm",
        "armor",
    };
    protected String[] mage = {
        "freeze",
        "firenova",
        "blink",
        "spellhaste",
    };
    protected String[] ranger = {
        "explosivearrow",
        "food",
        "lightning",
        "invisibility",
    };
    protected String[] cleric = {
        "pain",
        "heal",
        "prayer",
        "windwalk",
    };
    protected String[] ward = {
        "missile",
        "cleanse",
        "empower",
        "reflect",
    };
    protected String[] thief = {
        "poison",
        "torcharrow",
        "stun",
        "haste",
    };
    protected String[] bard = {
        "combust",
        "silence",
        "switch",
        "haste",
    };
    protected String[] shaman = {
        "fireball",
        "geyser",
        "storm",
        "waterwalk",
    };
    protected String[] necromancer = {
        "witherskull",
        "drainlife",
        "grip",
        "minion",
    };
    protected String[] architect = {
        "wall",
        "anvil",
        "stormblock",
        "zap",              
    };
//    private String[] playerClass = {
//        "warrior","mage","ranger","cleric","ward","thief","bard","shaman","necromancer","architect"
//    };
    
    protected Map<String,String> allSpells = new HashMap<String,String>();
    
    
    public ClassPermissions() {
        for(String spellName : warrior) {
            allSpells.put(spellName, "warrior");
        }
        for(String spellName : mage) {
            allSpells.put(spellName, "mage");
        }
        for(String spellName : ranger) {
            allSpells.put(spellName, "ranger");
        }
        for(String spellName : cleric) {
            allSpells.put(spellName, "cleric");
        }
        for(String spellName : ward) {
            allSpells.put(spellName, "ward");
        }        
        for(String spellName : bard) {
            allSpells.put(spellName, "bard");
        }        
        for(String spellName : shaman) {
            allSpells.put(spellName, "shaman");
        }
        for(String spellName : necromancer) {
            allSpells.put(spellName, "necromancer");
        }
        for(String spellName : architect) {
            allSpells.put(spellName, "architect");
        }        
    }
    
}
