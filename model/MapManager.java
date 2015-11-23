package model;

import java.util.HashMap;
import java.util.ArrayList;

public class MapManager {
	private HashMap<String,ArrayList<Position>> startingPositions;
	private static MapManager Instance = null;
	
	public static MapManager getInstance() {
		if(MapManager.Instance == null) {
			MapManager.Instance = new MapManager();
		}
		
		return MapManager.Instance;
	}
	
	protected MapManager() {
		startingPositions = new HashMap<String,ArrayList<Position>>();
		
		//Sample Code
		ArrayList<Position> positions = new ArrayList<Position>();
		for(int i = 0 ; i < 20; i++) {
			Position position = new Position();
			positions.add(position);
		}
		startingPositions.put("raceroyal", positions);
	}
	
	public void addStartingPositions(String mapName, ArrayList<Position> points) {
		startingPositions.put(mapName, points);
	}
	
	public ArrayList<Position> getStartingPositions(String mapName) {
		ArrayList<Position> ret = startingPositions.get(mapName);
		if( ret == null) {
			ret = new ArrayList<Position>();
		}
		return ret;
	}
}