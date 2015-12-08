package driver.json.collection;

import java.util.HashMap;
import java.util.List;

import driver.data.model.Position;
import driver.json.record.MapDetails;
import utility.Logger;

import java.util.ArrayList;

public class MapManager {
	private HashMap<String,MapDetails> mapDetailList;
	private static MapManager Instance = null;

	/**
	 * Creates a singleton
	 * @return MapManager
	 */
	public static MapManager getInstance() {
		if(MapManager.Instance == null) {
			MapManager.Instance = new MapManager();
		}

		return MapManager.Instance;
	}

	protected MapManager() {
		mapDetailList = new HashMap<String,MapDetails>();
	}
	
	/**
	 * Adding MapDetails object to the list of maps
	 * @param MapDetails
	 */
	public void addMapDetails(MapDetails map) {
		Logger.logMessage("Adding map " + map.getName() + " with " + map.getPositions().size() +" posititions");
		mapDetailList.put(map.getName(), map);
	}	

	/**
	 * Returns the list of positions for the given map name
	 * @param String
	 * @return List<Position>
	 */
	public List<Position> getStartingPositions(String mapName) {
		List<Position> ret = new ArrayList<Position>();
		if(mapDetailList.get(mapName) != null) {
			ret = mapDetailList.get(mapName).getPositions();
		}
		return ret;
	}
	
	/**
	 * Returns the MapDetails object for the given room type
	 * @param int
	 * @return MapDetails
	 */
	public MapDetails getMapDetails(int type) {
		for(MapDetails map : mapDetailList.values()) {
			if(map.getType() == type) {
				MapDetails retMap = new MapDetails(map.getType(), map.getName(), map.getMode(), map.getMinNumOfPlayers(), map.getPositions());
				return retMap;
			}
		}
		return null;
	}
	
	/**
	 * Returns the MapDetails object for the given map name
	 * @param String
	 * @return MapDetails
	 */
	public MapDetails getMapDetails(String mapName) {
		MapDetails map = mapDetailList.get(mapName);
		MapDetails retMap = new MapDetails(map.getType(), map.getName(), map.getMode(), map.getMinNumOfPlayers(), map.getPositions());
		return retMap;
	}
}