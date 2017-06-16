/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungoliant.magicspells.playerclasses;

import com.nisovin.magicspells.MagicSpells;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Travis
 */
public class CommandSelect extends JavaPlugin implements Listener {
    
    public MagicSpells plugin;
    protected Cuboid sanctum;
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    @Override
    public void onEnable() {
        getLogger().info("Hello World! MagicSpells-dev has been enabled!");
        System.out.println("MagicSpells-dev is enabled.");
        Bukkit.getPluginManager().registerEvents(this, this);
        plugin = MagicSpells.getInstance();
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
    private void openClassGUI(Player player, String playerClass) {
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN + "Class Selector");
        
        if (playerClass.equalsIgnoreCase("architect")) {
            ItemStack architect = new ItemStack(Material.DIAMOND_CHESTPLATE);
            ItemMeta architectMeta = architect.getItemMeta();
            architectMeta.setDisplayName(ChatColor.DARK_RED + "Architect");
            architect.setItemMeta(architectMeta);
            inv.setItem(1, architect);
        } else if (playerClass.equalsIgnoreCase("bard")){
            ItemStack bard = new ItemStack(Material.GRASS);
            ItemMeta bardMeta = bard.getItemMeta();
            bardMeta.setDisplayName(ChatColor.GREEN + "Bard");
            bard.setItemMeta(bardMeta);
            inv.setItem(1, bard);
        } else if (playerClass.equalsIgnoreCase("cleric")){
            ItemStack cleric = new ItemStack(Material.BEACON);
            ItemMeta clericMeta = cleric.getItemMeta();
            clericMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Cleric");
            cleric.setItemMeta(clericMeta);
            inv.setItem(1, cleric);
        } else if (playerClass.equalsIgnoreCase("mage")){
            ItemStack mage = new ItemStack(Material.EMERALD);
            ItemMeta mageMeta = mage.getItemMeta();
            mageMeta.setDisplayName(ChatColor.AQUA + "Mage");
            mage.setItemMeta(mageMeta);
            inv.setItem(1, mage);
        } else if (playerClass.equalsIgnoreCase("necromancer")){
            ItemStack necromancer = new ItemStack(Material.ANVIL);
            ItemMeta necromancerMeta = necromancer.getItemMeta();
            necromancerMeta.setDisplayName(ChatColor.DARK_PURPLE + "Necromancer");
            necromancer.setItemMeta(necromancerMeta);
            inv.setItem(1, necromancer);
        } else if (playerClass.equalsIgnoreCase("ranger")){
            ItemStack ranger = new ItemStack(Material.BOW);
            ItemMeta rangerMeta = ranger.getItemMeta();
            rangerMeta.setDisplayName(ChatColor.DARK_GREEN + "Ranger");
            ranger.setItemMeta(rangerMeta);
            inv.setItem(1, ranger);
        } else if (playerClass.equalsIgnoreCase("shaman")){
            ItemStack shaman = new ItemStack(Material.APPLE);
            ItemMeta shamanMeta = shaman.getItemMeta();
            shamanMeta.setDisplayName(ChatColor.DARK_AQUA + "Shaman");
            shaman.setItemMeta(shamanMeta);
            inv.setItem(1, shaman);
        } else if (playerClass.equalsIgnoreCase("thief")){
            ItemStack thief = new ItemStack(Material.SHEARS);
            ItemMeta thiefMeta = thief.getItemMeta();
            thiefMeta.setDisplayName(ChatColor.YELLOW + "Thief");
            thief.setItemMeta(thiefMeta);
            inv.setItem(1, thief);
        } else if (playerClass.equalsIgnoreCase("ward")){
            ItemStack ward = new ItemStack(Material.BARRIER);
            ItemMeta wardMeta = ward.getItemMeta();
            wardMeta.setDisplayName(ChatColor.DARK_GRAY + "Ward");
            ward.setItemMeta(wardMeta);
            inv.setItem(1, ward);
        } else if (playerClass.equalsIgnoreCase("warrior")){
            ItemStack warrior = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta warriorMeta = warrior.getItemMeta();
            warriorMeta.setDisplayName(ChatColor.GOLD + "Warrior");
            warrior.setItemMeta(warriorMeta);
            inv.setItem(1, warrior);
        }
        player.openInventory(inv);
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(!ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Class Selector")) {return;}
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        
        if(event.getCurrentItem()==null || event.getCurrentItem().getType()==Material.AIR || !event.getCurrentItem().hasItemMeta()) {
            player.closeInventory();
            player.sendMessage("Something went wrong");
            return;
        }
        
        SpellClass x = null;
        switch (event.getCurrentItem().getType()) {
            case DIAMOND_CHESTPLATE: x = new Architect(player, plugin);break;
            case GRASS: x = new Bard(player, plugin);break;
            case BEACON: x = new Cleric(player, plugin);break;
            case EMERALD: x = new Mage(player, plugin);break;    
            case ANVIL: x = new Necromancer(player, plugin);break;   
            case BOW: x = new Ranger(player, plugin);break;
            case APPLE: x = new Shaman(player, plugin);break;
            case SHEARS: x = new Thief(player, plugin);break;
            case BARRIER:  x = new Ward(player, plugin);break;   
            case DIAMOND_SWORD: x = new Warrior(player, plugin);break;
        }
        if (x != null) {
            x.setSpellPerms(player, plugin);
            player.closeInventory();
        } else {
            player.closeInventory();
            player.sendMessage("Nothing happened");
        }
    }
    
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
        ItemStack is = event.getItem();
        Block block = event.getClickedBlock();
        Material m = block.getType();
        if(m == Material.QUARTZ_BLOCK) {
            player.sendMessage("Looking for sanctum...");
            Sanctum s = new Sanctum(event.getPlayer().getWorld());
            SpellClass x = null;
            if (s.getSanctum(block).equalsIgnoreCase("architect")) {x = new Architect(player, MagicSpells.plugin);}
            //remove all permissions (instantiate spellClass)
            //set architect permissions
            else if (s.getSanctum(block).equalsIgnoreCase("bard")) {x = new Bard(player, MagicSpells.plugin);}
            else if (s.getSanctum(block).equalsIgnoreCase("cleric")) {x = new Cleric(player, MagicSpells.plugin);}
            else if (s.getSanctum(block).equalsIgnoreCase("mage")) {x = new Mage(player, MagicSpells.plugin);}
            else if (s.getSanctum(block).equalsIgnoreCase("necromancer")) {x = new Necromancer(player, MagicSpells.plugin);}
            else if (s.getSanctum(block).equalsIgnoreCase("ranger")) {x = new Ranger(player, MagicSpells.plugin);}
            else if (s.getSanctum(block).equalsIgnoreCase("shaman")) {x = new Shaman(player, MagicSpells.plugin);}
            else if (s.getSanctum(block).equalsIgnoreCase("thief")) {x = new Thief(player, MagicSpells.plugin);}
            else if (s.getSanctum(block).equalsIgnoreCase("ward")) {x = new Ward(player, MagicSpells.plugin);}
            else if (s.getSanctum(block).equalsIgnoreCase("warrior")) {x = new Warrior(player, MagicSpells.plugin);}
            if (x == null) {
                player.sendMessage("Sanctum not found");
            } else {
                
                openClassGUI(player, x.getClassName());
//                x.setSpellPerms(player, plugin);
            }
        }
        if(a==Action.PHYSICAL || is == null || is.getType()==Material.AIR) {return;}
        if(is.getType() == Material.COMPASS) {
//            openGUI(event.getPlayer());
        }
    }
        
//    @EventHandler
//    public void onPlayerBlockInteract (PlayerBucketEvent event) {
//        Player player = event.getPlayer();
//        Block block = event.getBlockClicked();
//        Material m = event.getBucket();
//        if(m == Material.QUARTZ_BLOCK) {
//            player.sendMessage("Looking for sanctum...");
//            Sanctum s = new Sanctum(event.getPlayer().getWorld());
//            SpellClass x = null;
//            if (s.getSanctum(block).equalsIgnoreCase("architect")) {x = new Architect(player, MagicSpells.plugin);}
//            //remove all permissions (instantiate spellClass)
//            //set architect permissions
//            else if (s.getSanctum(block).equalsIgnoreCase("bard")) {x = new Bard(player, MagicSpells.plugin);}
//            else if (s.getSanctum(block).equalsIgnoreCase("cleric")) {x = new Cleric(player, MagicSpells.plugin);}
//            else if (s.getSanctum(block).equalsIgnoreCase("mage")) {x = new Mage(player, MagicSpells.plugin);}
//            else if (s.getSanctum(block).equalsIgnoreCase("necromancer")) {x = new Necromancer(player, MagicSpells.plugin);}
//            else if (s.getSanctum(block).equalsIgnoreCase("ranger")) {x = new Ranger(player, MagicSpells.plugin);}
//            else if (s.getSanctum(block).equalsIgnoreCase("shaman")) {x = new Shaman(player, MagicSpells.plugin);}
//            else if (s.getSanctum(block).equalsIgnoreCase("thief")) {x = new Thief(player, MagicSpells.plugin);}
//            else if (s.getSanctum(block).equalsIgnoreCase("ward")) {x = new Ward(player, MagicSpells.plugin);}
//            else if (s.getSanctum(block).equalsIgnoreCase("warrior")) {x = new Warrior(player, MagicSpells.plugin);}
//            if (x == null) {
//                player.sendMessage("Sanctum not found");
//            } else {
//            x.setSpellPerms(player, plugin);
//            }
//        }
//    }
}

