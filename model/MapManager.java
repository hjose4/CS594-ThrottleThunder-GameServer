package model;

import java.awt.Point;
import java.util.HashMap;

import javafx.geometry.Point3D;

import java.util.ArrayList;

public class MapManager {
	private HashMap<String,ArrayList<ArrayList<Point3D>>> startingPositions;
	private static MapManager Instance = null;
	
	public static MapManager getInstance() {
		if(MapManager.Instance == null) {
			MapManager.Instance = new MapManager();
		}
		
		return MapManager.Instance;
	}
	
	protected MapManager() {
		startingPositions = new HashMap<String,ArrayList<ArrayList<Point3D>>>();
		
		//Sample Code
		ArrayList<ArrayList<Point3D>> positions = new ArrayList<ArrayList<Point3D>>();
		for(int i = 0 ; i < 20; i++) {
			ArrayList<Point3D> points = new ArrayList<Point3D>();
			points.add(new Point3D(0,0,0)); //Position
			points.add(new Point3D(0,0,0)); //HPR
			positions.add(points);
		}
		startingPositions.put("demo", positions);
	}
	
	public void addStartingPositions(String mapName, ArrayList<ArrayList<Point3D>> points) {
		startingPositions.put(mapName, points);
	}
	
	public ArrayList<ArrayList<Point3D>> getStartingPositions(String mapName) {
		return startingPositions.get(mapName);
	}
}
