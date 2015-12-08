package model;

import java.util.ArrayList;
import java.util.List;

import dataAccessLayer.record.Player;

public class Group {
	private Player leader;
	private List<Player> players = new ArrayList<>();
	private boolean ready; // true when the leader wants to enter a lobby
	private boolean finished;

	public Player getLeader() {
		return leader;
	}

	public Player[] getPlayers() {
		return players.toArray(new Player[] {});
	}

	public void addPlayer(Player p) {
		p.setGroup(this);
		players.add(p);
	}

	public boolean isReady() {
		return ready;
	}

	public void setLeader(Player leader) {
		this.leader = leader;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public Group(Player leader) {
		this.leader = leader;
		addPlayer(leader);
	}

	public boolean isFinished() {
		return finished;
	}

	public void removePlayer(Player player) {
		players.remove(player);
		if (player == leader)
			this.leader = players.get(0);
	}
}