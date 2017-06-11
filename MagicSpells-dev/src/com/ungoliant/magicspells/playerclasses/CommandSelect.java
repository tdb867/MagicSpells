/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungoliant.magicspells.playerclasses;

import com.nisovin.magicspells.MagicSpells;
import com.ungoliant.magicspells.Architect;
import com.ungoliant.magicspells.Bard;
import com.ungoliant.magicspells.Cleric;
import com.ungoliant.magicspells.Mage;
import com.ungoliant.magicspells.Necromancer;
import com.ungoliant.magicspells.Ranger;
import com.ungoliant.magicspells.Shaman;
import com.ungoliant.magicspells.SpellLoadout;
import com.ungoliant.magicspells.Thief;
import com.ungoliant.magicspells.Ward;
import com.ungoliant.magicspells.Warrior;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Travis
 */
public class CommandSelect extends JavaPlugin implements Listener {
    
    MagicSpells plugin;
    protected Integer[] SanctumCoords = {5,5,5};
    protected Cuboid sanctum;
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    @Override
    public void onEnable() {
        getLogger().info("Hello World! MagicSpells-dev has been enabled!");
        System.out.println("MagicSpells-dev is enabled.");
        Bukkit.getPluginManager().registerEvents(this, this);
    }
    
    @Override
    public void onDisable() {
        System.out.println("MagicSpells-dev is disabled.");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        if (cmd.getName().equalsIgnoreCase("hello") && sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage("Hello, " + player.getName() + "!");
            player.getInventory().addItem(new ItemStack(Material.COMPASS));
            return true;
        }
        
        return false;
    }
    
    public void teleportInWorld(Player player, int x, int y, int z) {
        player.teleport(new Location(player.getWorld(), x, y, z));
    }
//    private void openGUI(Player player) {
//        Inventory inv = Bukkit.createInventory(null, 18, ChatColor.DARK_GREEN + "Class Selector");
//        
//        ItemStack architect = new ItemStack(Material.DIAMOND_CHESTPLATE);
//        ItemMeta architectMeta = architect.getItemMeta();
//        architectMeta.setDisplayName(ChatColor.DARK_RED + "Architect");
//        architect.setItemMeta(architectMeta);
//        
//        ItemStack bard = new ItemStack(Material.GRASS);
//        ItemMeta bardMeta = bard.getItemMeta();
//        bardMeta.setDisplayName(ChatColor.GREEN + "Bard");
//        bard.setItemMeta(bardMeta);
//        
//        ItemStack cleric = new ItemStack(Material.BEACON);
//        ItemMeta clericMeta = cleric.getItemMeta();
//        clericMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Cleric");
//        cleric.setItemMeta(clericMeta);
//        
//        ItemStack mage = new ItemStack(Material.EMERALD);
//        ItemMeta mageMeta = mage.getItemMeta();
//        mageMeta.setDisplayName(ChatColor.AQUA + "Mage");
//        mage.setItemMeta(mageMeta);
//        
//        ItemStack necromancer = new ItemStack(Material.ANVIL);
//        ItemMeta necromancerMeta = necromancer.getItemMeta();
//        necromancerMeta.setDisplayName(ChatColor.DARK_PURPLE + "Necromancer");
//        necromancer.setItemMeta(necromancerMeta);
//        
//        ItemStack ranger = new ItemStack(Material.BOW);
//        ItemMeta rangerMeta = ranger.getItemMeta();
//        rangerMeta.setDisplayName(ChatColor.DARK_GREEN + "Ranger");
//        ranger.setItemMeta(rangerMeta);
//        
//        ItemStack shaman = new ItemStack(Material.APPLE);
//        ItemMeta shamanMeta = shaman.getItemMeta();
//        shamanMeta.setDisplayName(ChatColor.DARK_AQUA + "Shaman");
//        shaman.setItemMeta(shamanMeta);
//        
//        ItemStack thief = new ItemStack(Material.SHEARS);
//        ItemMeta thiefMeta = thief.getItemMeta();
//        thiefMeta.setDisplayName(ChatColor.YELLOW + "Thief");
//        thief.setItemMeta(thiefMeta);
//        
//        ItemStack ward = new ItemStack(Material.BARRIER);
//        ItemMeta wardMeta = ward.getItemMeta();
//        wardMeta.setDisplayName(ChatColor.DARK_GRAY + "Ward");
//        ward.setItemMeta(wardMeta);
//        
//        ItemStack warrior = new ItemStack(Material.DIAMOND_SWORD);
//        ItemMeta warriorMeta = warrior.getItemMeta();
//        warriorMeta.setDisplayName(ChatColor.GOLD + "Warrior");
//        warrior.setItemMeta(warriorMeta);
//        
//        inv.setItem(0, architect);
//        inv.setItem(1, bard);
//        inv.setItem(2, cleric);
//        inv.setItem(3, mage);
//        inv.setItem(4, necromancer);
//        inv.setItem(5, ranger);
//        inv.setItem(6, shaman);
//        inv.setItem(7, thief);
//        inv.setItem(8, ward);
//        inv.setItem(9, warrior);
//        
//        player.openInventory(inv);
//    }
    
//    @EventHandler
//    public void onInventoryClick(InventoryClickEvent event) {
//        if(!ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Class Selector")) {return;}
//        Player player = (Player) event.getWhoClicked();
//        event.setCancelled(true);
//        
//        if(event.getCurrentItem()==null || event.getCurrentItem().getType()==Material.AIR || !event.getCurrentItem().hasItemMeta()) {
//            player.closeInventory();
//            player.sendMessage("Something went wrong");
//            return;
//        }
//        
//        switch (event.getCurrentItem().getType()) {
//            case DIAMOND_CHESTPLATE:
//                SpellLoadout architect = new Architect(player, MagicSpells.plugin);
//                player.closeInventory();
//                player.sendMessage(String.format("%sYou have chosen the %sArchitect%s class!", ChatColor.GOLD, ChatColor.DARK_RED, ChatColor.GOLD));
//                break;
//            case GRASS:
//                SpellLoadout bard = new Bard(player, MagicSpells.plugin);
//                player.closeInventory();
//                player.sendMessage(String.format("%sYou have chosen the %sBard%s class!", ChatColor.GOLD, ChatColor.DARK_RED, ChatColor.GOLD));
//                break;
//            case BEACON:
//                SpellLoadout cleric = new Cleric(player, MagicSpells.plugin);
//                player.closeInventory();
//                player.sendMessage(String.format("%sYou have chosen the %sCleric%s class!", ChatColor.GOLD, ChatColor.DARK_RED, ChatColor.GOLD));
//                break;    
//            case EMERALD:
//                SpellLoadout mage = new Mage(player, MagicSpells.plugin);
//                player.closeInventory();
//                player.sendMessage(String.format("%sYou have chosen the %sMage%s class!", ChatColor.GOLD, ChatColor.DARK_RED, ChatColor.GOLD));
//                break;    
//            case ANVIL:
//                SpellLoadout necromancer = new Necromancer(player, MagicSpells.plugin);
//                player.closeInventory();
//                player.sendMessage(String.format("%sYou have chosen the %sNecromancer%s class!", ChatColor.GOLD, ChatColor.DARK_RED, ChatColor.GOLD));
//                break;    
//            case BOW:
//                SpellLoadout ranger = new Ranger(player, MagicSpells.plugin);
//                player.closeInventory();
//                player.sendMessage(String.format("%sYou have chosen the %sRanger%s class!", ChatColor.GOLD, ChatColor.DARK_RED, ChatColor.GOLD));
//                break;
//            case APPLE:
//                SpellLoadout shaman = new Shaman(player, MagicSpells.plugin);
//                player.closeInventory();
//                player.sendMessage(String.format("%sYou have chosen the %sShaman%s class!", ChatColor.GOLD, ChatColor.DARK_RED, ChatColor.GOLD));
//                break;
//            case SHEARS:
//                SpellLoadout thief = new Thief(player, MagicSpells.plugin);
//                player.closeInventory();
//                player.sendMessage(String.format("%sYou have chosen the %sThief%s class!", ChatColor.GOLD, ChatColor.DARK_RED, ChatColor.GOLD));
//                break;
//            case BARRIER:
//                SpellLoadout ward = new Ward(player, MagicSpells.plugin);
//                player.closeInventory();
//                player.sendMessage(String.format("%sYou have chosen the %sWard%s class!", ChatColor.GOLD, ChatColor.DARK_RED, ChatColor.GOLD));
//                break;    
//            case DIAMOND_SWORD:
//                SpellLoadout warrior = new Warrior(player, MagicSpells.plugin);
//                player.closeInventory();
//                player.sendMessage(String.format("%sYou have chosen the %sWarrior%s class!", ChatColor.GOLD, ChatColor.DARK_RED, ChatColor.GOLD));
//                break;
//            default:
//                player.closeInventory();
//                player.sendMessage("Nothing happened");
//                break;
//        }
//    }
    
//    @EventHandler
//    public void onPlayerJoin(PlayerJoinEvent event) {
//        Player player = event.getPlayer();
//        PlayerInventory inventory = player.getInventory();
//        ItemStack itemstack = new ItemStack(Material.DIAMOND, 64);
//        
//        if (!inventory.contains(itemstack)) {
//        inventory.addItem(itemstack); // Adds a stack of diamonds to the player's inventory
//        player.sendMessage("Welcome! You seem to be reeeally poor, so we gave you some more diamonds!");
//    }
//        
//        event.getPlayer().getInventory().addItem(new ItemStack(Material.COMPASS));
//    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action a = event.getAction();
//        ItemStack is = event.getItem();
        Block block = event.getClickedBlock();
//        if(block.getType()==Material.DIAMOND_BLOCK) {
//            SpellLoadout x = new Architect(event.getPlayer(), MagicSpells.plugin);
//        }
//        if(block.getType()==Material.EMERALD_BLOCK) {
//            SpellLoadout x = new Bard(event.getPlayer(), MagicSpells.plugin);
//        }
//        if(block.getType()==Material.GOLD_BLOCK) {
//            SpellLoadout x = new Cleric(event.getPlayer(), MagicSpells.plugin);
//        }
//        if(block.getType()==Material.HAY_BLOCK) {
//            SpellLoadout x = new Mage(event.getPlayer(), MagicSpells.plugin);
//        }
//        if(block.getType()==Material.LAPIS_BLOCK) {
//            SpellLoadout x = new Necromancer(event.getPlayer(), MagicSpells.plugin);
//        }
//        if(block.getType()==Material.MELON_BLOCK) {
//            SpellLoadout x = new Ranger(event.getPlayer(), MagicSpells.plugin);
//        }
        if(block.getType()==Material.QUARTZ_BLOCK) {
            player.sendMessage("Looking for sanctum...");
            Sanctum s = new Sanctum(event.getPlayer().getWorld());
            if (s.getSanctum(player).equalsIgnoreCase("architect")) {SpellLoadout x = new Architect(player, MagicSpells.plugin);}
            else if (s.getSanctum(player).equalsIgnoreCase("bard")) {SpellLoadout x = new Bard(player, MagicSpells.plugin);}
            else if (s.getSanctum(player).equalsIgnoreCase("cleric")) {SpellLoadout x = new Cleric(player, MagicSpells.plugin);}
            else if (s.getSanctum(player).equalsIgnoreCase("mage")) {SpellLoadout x = new Mage(player, MagicSpells.plugin);}
            else if (s.getSanctum(player).equalsIgnoreCase("necromancer")) {SpellLoadout x = new Necromancer(player, MagicSpells.plugin);}
            else if (s.getSanctum(player).equalsIgnoreCase("ranger")) {SpellLoadout x = new Ranger(player, MagicSpells.plugin);}
            else if (s.getSanctum(player).equalsIgnoreCase("shaman")) {SpellLoadout x = new Shaman(player, MagicSpells.plugin);}
            else if (s.getSanctum(player).equalsIgnoreCase("thief")) {SpellLoadout x = new Thief(player, MagicSpells.plugin);}
            else if (s.getSanctum(player).equalsIgnoreCase("ward")) {SpellLoadout x = new Ward(player, MagicSpells.plugin);}
            else if (s.getSanctum(player).equalsIgnoreCase("warrior")) {SpellLoadout x = new Warrior(player, MagicSpells.plugin);}
        }
//        if(a==Action.PHYSICAL || is == null || is.getType()==Material.AIR) {return;}
//        if(is.getType() == Material.COMPASS) {
//            openGUI(event.getPlayer());
//        }
        
    }
    

    }
//    @EventHandler
//    public void onPlayerBlockInteract (PlayerBucketEvent event) {
//        
//        
//        Block block = event.getBlockClicked();
//        if (block.getType() == Material.QUARTZ_BLOCK) {
//            SpellLoadout x = new Mage(event.getPlayer(), MagicSpells.plugin);
//        } else {
//            event.getPlayer().sendMessage(block.getType().toString());
//        }
//    }

