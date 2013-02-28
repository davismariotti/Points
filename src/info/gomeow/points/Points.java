package info.gomeow.points;

import info.gomeow.points.util.Util;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.java.JavaPlugin;

public class Points extends JavaPlugin {
	
	public boolean containsIgnoreCase(Collection<String> l, String s) {
		 Iterator <String> it = l.iterator();
		 while(it.hasNext()){
			 if(it.next().equalsIgnoreCase(s)){
				 return true;
			 }
		 }
		 return false;
	}
	public String capFirst(String s) {
		return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
	}
	
	public boolean permissionsStartWith(CommandSender cs, String node) {
		for(PermissionAttachmentInfo p:cs.getEffectivePermissions()) {
			if(p.getPermission().startsWith(node)) {
				return true;
			}
		}
		return false;
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
						cs.sendMessage(ChatColor.RED+"That point team has already been created!");
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
						cs.sendMessage(ChatColor.RED+"That point team does not exist!");
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("set")) {
					if(this.permissionsStartWith(cs, "points.set")) {
						if(this.containsIgnoreCase(Util.getPoints().getConfigurationSection("Teams").getKeys(false),args[1])) {
							if(cs.hasPermission("points.set."+args[1].toLowerCase())) {
								int points;
								try {
									points = Integer.parseInt(args[2]);
								}
								catch(NumberFormatException nfe) {
									cs.sendMessage(ChatColor.RED+"");
									return true;
								}
								Util.getPoints().set("Teams."+args[1].toLowerCase()+".Points", points);
								Util.savePoints();
								PointEvent e = new PointEvent(args[1].toLowerCase(),points);
								getServer().getPluginManager().callEvent(e);
							}
						}
						else {
							cs.sendMessage(ChatColor.RED+"That point team does not exist!");
						}
					}
					cs.sendMessage(ChatColor.RED+"You do not have permission to do that!");
					return true;
				}
				else if(args[0].equalsIgnoreCase("give")) {
					
				}
				else if(args[0].equalsIgnoreCase("take")) {
					
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
	
	@EventHandler
	public void onPointChange(PointEvent event) {
		String team = event.getTeam();
		int points = event.getPoints();
	}
	
}
