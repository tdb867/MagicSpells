package com.nisovin.magicspells.teams;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.nisovin.magicspells.MagicSpells;

public class Team {

	private String name;
	private String permissionNode;
	private boolean friendlyFire;
	
	private List<String> canTargetNames;
	private List<Team> canTargetTeams;
	private List<String> cantTargetNames;
	private List<Team> cantTargetTeams;
	
	public Team(ConfigurationSection config, String name) {
		this.name = name;
		permissionNode = config.getString("permission", "magicspells.team." + name);
		friendlyFire = config.getBoolean("friendly-fire", false);
		canTargetNames = config.getStringList("can-target");
		cantTargetNames = config.getStringList("cant-target");
	}
	
	public void initialize(MagicSpellsTeams plugin) {
		if (canTargetNames != null && !canTargetNames.isEmpty()) {
			canTargetTeams = new ArrayList<>();
			for (String name : canTargetNames) {
				Team team = plugin.getTeamByName(name);
				if (team != null) {
					canTargetTeams.add(team);
				} else {
					MagicSpells.error("Invalid team defined in can-target list");
				}
			}
			if (canTargetTeams.isEmpty()) {
				canTargetTeams = null;
			}
		}
		canTargetNames = null;
		if (cantTargetNames != null && !cantTargetNames.isEmpty()) {
			cantTargetTeams = new ArrayList<>();
			for (String name : cantTargetNames) {
				Team team = plugin.getTeamByName(name);
				if (team != null) {
					cantTargetTeams.add(team);
				} else {
					MagicSpells.error("Invalid team defined in cant-target list");
				}
			}
			if (cantTargetTeams.isEmpty()) {
				cantTargetTeams = null;
			}
		}
		cantTargetNames = null;
	}
	
	public boolean inTeam(Player player) {
		return player.hasPermission(permissionNode);
	}
	
	public boolean allowFriendlyFire() {
		return friendlyFire;
	}
	
	public boolean canTarget(Team team) {
		if (canTargetTeams != null && !canTargetTeams.contains(team)) return false;
		if (cantTargetTeams != null && cantTargetTeams.contains(team)) return false;
		return true;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPermission() {
		return permissionNode;
	}
	
	public Set<String> getCanTarget() {
		return new TreeSet<>(canTargetNames);
	}
	
	public Set<String> getCantTarget() {
		return new TreeSet<>(cantTargetNames);
	}
	
}
