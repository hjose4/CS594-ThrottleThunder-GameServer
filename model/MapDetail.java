package model;

import java.util.ArrayList;
import java.util.List;

public class MapDetail {
	private int type;
	private int minNumOfPlayers;
	private String name;
	private List<Position> positions;
	public MapDetail(int type, String name, int minNumOfPlayers, List<Position> positions) {
		this.type = type;
		this.name = name;
		this.minNumOfPlayers = minNumOfPlayers;
		this.positions = positions;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getMinNumOfPlayers() {
		return minNumOfPlayers;
	}
	public void setMinNumOfPlayers(int minNumOfPlayers) {
		this.minNumOfPlayers = minNumOfPlayers;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Position> getPositions() {
		return positions;
	}
	public void setPositions(ArrayList<Position> positions) {
		this.positions = positions;
	}
	public int getMaxNumOfPlayers() {
		return this.positions.size();
	}
	
}
