package info.gomeow.points;

import info.gomeow.points.util.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Points extends JavaPlugin {
	
	public String capFirst(String s) {
		return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
	}

	@Override
	public void onEnable() {
		Util.setPlugin(this);
		Util.reloadPoints();
		Util.savePoints();
	}
	
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmdObj, String label, String[] args) {
		if(cmdObj.getName().equalsIgnoreCase("points")) {
			switch(args.length) {
			case 3:
				if(args[0].equalsIgnoreCase("create")) {
					if(!cs.hasPermission("points.create")) {
						cs.sendMessage(ChatColor.RED+"You do not have permission to do that!");
						return true;
					}
					if(Util.getPoints().getConfigurationSection("Teams").getKeys(false).contains(args[1])) {
						cs.sendMessage(ChatColor.RED+"That team has already been created!");
						return true;
					}
					ChatColor color = null;
					try {
						color = ChatColor.valueOf(args[2]);
					}
					catch(IllegalArgumentException iae) {
						cs.sendMessage(ChatColor.RED+"That is not a valid color!");
					}
					Util.getPoints().set("Teams."+args[1]+".Points", 0);
					Util.getPoints().set("Teams."+args[1]+".Color", capFirst(color.toString()));
					Util.savePoints();
				}
				else if(args[0].equalsIgnoreCase("destroy")) {
					if(!cs.hasPermission("points.destroy")) {
						cs.sendMessage(ChatColor.RED+"You do not have permission to do that!");
						return true;
					}
					if(!Util.getPoints().getConfigurationSection("Teams").getKeys(false).contains(args[1])) {
						cs.sendMessage(ChatColor.RED+"That team does not exist!");
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("set")) {
					
				}
			default: sendHelp(cs);
			}
		}
		return true;
	}

	private void sendHelp(CommandSender cs) {
		cs.sendMessage(ChatColor.GOLD+"/points - Shows this message.");
		cs.sendMessage(ChatColor.GOLD+"/points create <name> - Creates a point team.");
		cs.sendMessage(ChatColor.GOLD+"/points destroy <name> - Destroys a point team.");
	}
	
}
