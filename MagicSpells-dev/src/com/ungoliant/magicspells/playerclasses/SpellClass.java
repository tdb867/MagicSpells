/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungoliant.magicspells.playerclasses;

import com.nisovin.magicspells.MagicSpells;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

/**
 *
 * @author Travis
 */
public abstract class SpellClass {
    //spell names defined in .src\spells-regular.yml
    protected String[] architect = { //Do not implement yet
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
    
    protected Map<String,String[]> spellMap = new HashMap<>();
    PermissionAttachment attachment = null;
    
    public SpellClass() {
        //consider implementing attributes hashmap for quick access from commandselect
        spellMap.put("architect", architect);
        spellMap.put("bard", bard);
        spellMap.put("cleric", cleric);
        spellMap.put("mage", mage);
        spellMap.put("necromancer", necromancer);
        spellMap.put("ranger", ranger);
        spellMap.put("shaman", shaman);
        spellMap.put("thief", thief);
        spellMap.put("ward", ward);
        spellMap.put("warrior", warrior);
    }
    public void removeSpellPerms(Player player, PermissionAttachment attachment) {
        com.nisovin.magicspells.Spellbook spellbook = MagicSpells.getSpellbook(player);        
        spellbook.removeAllSpells();
        for (String spell[] : spellMap.values()) {
            for (String spellName : spell) {
                System.out.println("Revoking permissions for: "+spellName);
            attachment.setPermission("magicspells.grant." + spellName, false);
//            attachment.setPermission("magicspells.cast." + spellName, false);
            attachment.setPermission("magicspells.learn."+spellName, false);
            }
        }
    }
    abstract void setSpellPerms(Player player, MagicSpells plugin);
    abstract void openClassGUI(Player player, String className);
    abstract String getClassName();
    abstract String[] getSpells();
}
