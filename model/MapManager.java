package model;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class MapManager {
	private HashMap<String,MapDetail> mapDetailList;
	private static MapManager Instance = null;

	public static MapManager getInstance() {
		if(MapManager.Instance == null) {
			MapManager.Instance = new MapManager();
		}

		return MapManager.Instance;
	}

	protected MapManager() {
		mapDetailList = new HashMap<String,MapDetail>();
	}
	
	public void addMapDetails(MapDetail map) {
		System.out.println("Adding map " + map.getName() + " with " + map.getPositions().size() +" posititions");
		mapDetailList.put(map.getName(), map);
	}	

	public List<Position> getStartingPositions(String mapName) {
		List<Position> ret = new ArrayList<Position>();
		if(mapDetailList.get(mapName) != null) {
			ret = mapDetailList.get(mapName).getPositions();
		}
		return ret;
	}
	
	public MapDetail getMapDetail(int type) {
		for(MapDetail map : mapDetailList.values()) {
			if(map.getType() == type) {
				return map;
			}
		}
		return null;
	}
	
	public MapDetail getMapDetail(String mapName) {
		return mapDetailList.get(mapName);
	}
}