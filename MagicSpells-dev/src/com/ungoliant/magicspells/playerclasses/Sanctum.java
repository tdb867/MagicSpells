/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungoliant.magicspells.playerclasses;


import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 *
 * @author Travis
 */
public class Sanctum{
//    private Cuboid sanctum;
    Map<Location,String> sanctumMap=new HashMap<>();
    
    public Sanctum(World world) {
        sanctumMap.put(new Location(world,81,71,175),"architect");
        sanctumMap.put(new Location(world,3788,90,3819),"bard"); //set to cathedral
        sanctumMap.put(new Location(world,3891,91,3831),"cleric"); //set to cathedral
        sanctumMap.put(new Location(world,3796,90,3819),"mage"); //set to cathedral
        sanctumMap.put(new Location(world,3804,90,3819),"necromancer"); //set to cathedral
        sanctumMap.put(new Location(world,3812,90,3819),"ranger"); //set to cathedral
        sanctumMap.put(new Location(world,172,68,169),"shaman");
        sanctumMap.put(new Location(world,3820,90,3819),"thief"); //set to cathedral
        sanctumMap.put(new Location(world,3828,90,3819),"ward"); //set to cathedral
        sanctumMap.put(new Location(world,3836,90,3819),"warrior"); //set to cathedral
    }
    
//    public String getSanctum(Player player) {
//        for (Map.Entry<Location,String> entry : sanctumMap.entrySet()) {
//            Location l1 = entry.getValue();
//            int expand = 2;
//            sanctum = new Cuboid(l1,expand);
//            if (sanctum.contains(player.getLocation())) {
//                player.sendMessage("Sanctum found!");
//                return entry.getKey();
//            }
//        }
//        player.sendMessage("Sanctum not found.");
//        return "";
//    }
    public String getSanctum(Block block) {
        String className = "";
        className = sanctumMap.get(block.getLocation());
        return className;
    }
    
}
