/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungoliant.magicspells;

import com.nisovin.magicspells.MagicSpells;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

/**
 *
 * @author Travis
 */
public class Mage extends ClassPermissions implements SpellLoadout{
    
    public Mage(Player player, MagicSpells plugin) {
        //Create the spellMap
        super();
        
        PermissionAttachment attachment = player.addAttachment(plugin);
        //grant permission for list, forget, and scroll spells
        if (player.isOp()) {attachment.setPermission("magicspells.advanced.*", true);}
        
        //Remove all spell permissions
        for(Map.Entry m : allSpells.entrySet()) {
            //removing this permission will not unlearn the spell, try /cast forget <player>
            attachment.setPermission("magicspells.grant."+m.getKey(), false);
            attachment.setPermission("magicspells.cast."+m.getKey(), false);
//            attachment.setPermission("magicspells.learn."+m.getKey(), false);            
        }
        
        //Add spell permissions just for the mage class
        for(Map.Entry m : allSpells.entrySet()) {
            if (m.getValue().equals("mage")) {
                attachment.setPermission("magicspells.grant."+m.getKey(), true);
                attachment.setPermission("magicspells.cast."+m.getKey(), true);
//                attachment.setPermission("magicspells.learn."+m.getKey(), false);
            }
        }
        
//        Spellbook spellbook = MagicSpells.getSpellbook(player);        
//        spellbook.removeAllSpells();
//        spellbook.addSpell(MagicSpells.getSpellByInternalName("fireball"));
    }
    
///** 
// * Add transient permission to a player. This operation adds a world-unspecific permission onto the player object in bukkit via Bukkit's permission interface.
// * @param player Player Object
// * @param permission Permission node
// * @return Success or Failure
// */
//    private boolean playerAddTransient(Player player,String permission){
//        for (  PermissionAttachmentInfo paInfo : player.getEffectivePermissions()) {
//            if (paInfo.getAttachment() != null && paInfo.getAttachment().getPlugin().equals(plugin)) {
//                paInfo.getAttachment().setPermission(permission,true);
//                return true;
//               }
//        }
//        PermissionAttachment attach=player.addAttachment(plugin);
//        attach.setPermission(permission,true);
//        return true;
//    }
    
    @Override
    public void castQ() {
        System.out.println("Casting fireball...");
    }
    
    @Override
    public void castE() {
        System.out.println("Casting freeze...");
    }
    
    @Override
    public void castR() {
        System.out.println("Casting lightning bolt...");
    }
    
    @Override
    public void castF() {
        System.out.println("Casting blink...");
    }
}
