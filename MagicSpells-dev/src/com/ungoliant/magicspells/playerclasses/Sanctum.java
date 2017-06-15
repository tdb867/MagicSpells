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
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 *
 * @author Travis
 */
public class Sanctum{
    private Cuboid sanctum;
    Map<String,Location> sanctumMap=new HashMap<String,Location>();
    
    public Sanctum(World world) {
        sanctumMap.put("architect",new Location(world,81,71,175));
        sanctumMap.put("bard",new Location(world,3788,90,3819)); //set to cathedral
        sanctumMap.put("cleric",new Location(world,3891,91,3831)); //set to cathedral
        sanctumMap.put("mage",new Location(world,3796,90,3819)); //set to cathedral
        sanctumMap.put("necromancer",new Location(world,3804,90,3819)); //set to cathedral
        sanctumMap.put("ranger",new Location(world,3812,90,3819)); //set to cathedral
        sanctumMap.put("shaman",new Location(world,172,68,169));
        sanctumMap.put("thief",new Location(world,3820,90,3819)); //set to cathedral
        sanctumMap.put("ward",new Location(world,3828,90,3819)); //set to cathedral
        sanctumMap.put("warrior",new Location(world,3836,90,3819)); //set to cathedral
    }
    
    public String getSanctum(Player player) {
        for (Map.Entry<String, Location> entry : sanctumMap.entrySet()) {
            Location l1 = entry.getValue();
            int expand = 2;
            sanctum = new Cuboid(l1,expand);
            System.out.println(sanctum.toString());
            if (sanctum.contains(player.getLocation())) {
                player.sendMessage("Sanctum found!");
                return entry.getKey();
            }
        }
        player.sendMessage("Sanctum not found.");
        return "";
    }
    
}
