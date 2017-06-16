/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungoliant.magicspells.playerclasses;

import com.nisovin.magicspells.MagicSpells;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachment;

/**
 *
 * @author Travis
 */
public class Thief extends SpellClass{
    private final String className = "thief";
    Integer[] sanctum =  {3820,90,3819};
//    Material menuItem = Material.DIAMOND_CHESTPLATE;
//    ChatColor chatColor = ChatColor.DARK_RED;

    public Thief(Player player, MagicSpells plugin) {
        //Create the spellMap
        super();
        attachment = player.addAttachment(plugin);
        player.sendMessage(String.format("%sYou have chosen the %sThief%s class!", ChatColor.GOLD, ChatColor.DARK_RED, ChatColor.GOLD));
    }
    @Override
    protected void setSpellPerms(Player player, MagicSpells plugin) {
        super.removeSpellPerms(player, attachment);
        //Add spell permissions just for this class
        for (String spellName : getSpells()) {
//            attachment.setPermission("magicspells.grant."+spellName, true);
            attachment.setPermission("magicspells.cast."+spellName, true);
            attachment.setPermission("magicspells.learn."+spellName, true);
        }
        player.sendMessage("You have chosen the Thief class!");
    }
    
    @Override
    void openClassGUI(Player player, String className) {
//        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN + "Class Selector");
//        ItemStack architect = new ItemStack(Material.DIAMOND_CHESTPLATE);
//        ItemMeta architectMeta = architect.getItemMeta();
//        architectMeta.setDisplayName(ChatColor.DARK_RED + "Architect");
//        architect.setItemMeta(architectMeta);
//        inv.setItem(0, architect);
    }
    
    @Override
    protected String getClassName() {
        return this.className;
    }
    
    @Override
    protected String[] getSpells() {
        return super.spellMap.get(getClassName());
    }
    
//    protected Material getMenuItem() {
//        return this.menuItem;
//    }
    
    protected Integer[] getSanctum() {
        return this.sanctum;
    }
    
//    protected ChatColor getChatColor() {
//        return this.chatColor;
//    }

}