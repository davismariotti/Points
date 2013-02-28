package info.gomeow.points;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PointEvent extends Event {
	
	private static HandlerList handlers = new HandlerList();
	private final String team;
	private final int points;

	public PointEvent(String t, int points) {
		this.points = points;
		this.team = t;
	}
	
	public int getPoints() {
		return points;
	}
	
	public String getTeam() {
		return this.team;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
}
