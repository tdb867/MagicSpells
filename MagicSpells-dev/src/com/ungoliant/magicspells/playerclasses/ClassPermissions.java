/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungoliant.magicspells;

import java.util.*;
import org.bukkit.Location;

/**
 *
 * @author Travis
 */
public class ClassPermissions {
    //Each class gets two instant spells, a targeted spell, and a buff spell
    //This was arbitrarily decided by tbordovsky
    //spell names defined in .src\spells-regular.yml
    protected String[] architect = { //Do not implement
        "wall",
        "anvil",
        "stormblock",
        "zap",              
    };
    protected String[] bard = {
        "empower",
        "stormblock", //@TODO change to jukebox
        "resist", //@TODO cast on crouch
        "chime", //@TODO add particleprojectilespell
    };
    protected String[] cleric = {
        "prayer",
        "heal",
        "forcepush", //change name to rebuke, add damage
        "clarity", //*make sure this is mana regen and not free-cast
    };
    protected String[] mage = {
        "fireball",
        "firenova", //change to frostnova
//        "portal", //figure out how to implement
        "spellhaste",
    };
    protected String[] necromancer = {
        "witherskull", //TODO add to .yml and test, make sure no damage to environment
        "drainlife",
        "haze",
        "minion", //make sure it uses reagents
    };
    protected String[] ranger = {
        "volley",
        "leap", //invert direction
//        "trap", //TODO create traps, steel trap (cripple)
//        "invisibility" //poison arrow
    };
    protected String[] shaman = { //Do not implement
        "fireball",
        "geyser",
        "storm",
        "lightning",
    };
    protected String[] thief = {
        "haze", //make melee range, name pocketsand
        "stun", //add to yml, make range really small
        "disguise",
        "invisibility",
    };
    protected String[] ward = {
        "roar",
        "cleanse",
        "prayer",
        "reflect",
    };
    protected String[] warrior = {
        "roar",
        "cripple",
        "disarm",
        "flamewalk", //change to whirlwind
    };
 
//    private String[] listOfClasses = {
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
    
//    private String[] getListOfClasses() {
//        return this.listOfClasses;
//    }
    
}
