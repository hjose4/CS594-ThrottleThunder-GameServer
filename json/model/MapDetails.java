package json.model;

import java.util.ArrayList;
import java.util.List;

import model.Position;

public class MapDetails {
	private int type;
	private int mode;
	private int minNumOfPlayers;
	private String name;
	private List<Position> positions;
	public MapDetails(int type, String name, int mode, int minNumOfPlayers, List<Position> positions) {
		this.type = type;
		this.mode = mode;
		this.name = name;
		this.minNumOfPlayers = minNumOfPlayers;
		this.positions = positions;
	}
	public int getType() {
		return type;
	}
	public int getMode() {
		return this.mode;
	}
	public int getMinNumOfPlayers() {
		return minNumOfPlayers;
	}
	public String getName() {
		return name;
	}
	public List<Position> getPositions() {
		List<Position> list = new ArrayList<Position>(positions.size());
		for(Position point : positions) {
			list.add(point);
		}
		return list;
	}
	public int getMaxNumOfPlayers() {
		return this.positions.size();
	}
	
}
