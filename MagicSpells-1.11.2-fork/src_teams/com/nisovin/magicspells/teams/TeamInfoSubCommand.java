package com.nisovin.magicspells.teams;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

public class TeamInfoSubCommand implements TeamsSubCommand {

	private MagicSpellsTeams plugin;
	
	public TeamInfoSubCommand(MagicSpellsTeams plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean process(CommandSender sender, String[] args) {
		if (args.length >= 2) {
			String name = args[1];
			Team team = plugin.getTeamByName(name);
			if (team == null) {
				sender.sendMessage("There is no team by that name!");
				return true;
			}
			sendTeamInfo(sender, team);
			return true;
		}
		return false;
	}
	
	private void sendTeamInfo(CommandSender sender, Team team) {
		sender.sendMessage(new String[] {
			"Name: " + team.getName(),
			"Permission: " + team.getPermission(),
			"Friendly fire: " + team.allowFriendlyFire(),
			"Can target: " + Arrays.toString(team.getCanTarget().toArray()),
			"Can't target: " + Arrays.toString(team.getCantTarget().toArray())
		});
	}

}
