package dataAccessLayer.record;

import java.util.HashMap;

import dataAccessLayer.ObjectModel;

public class PlayerVehicle extends ObjectModel {
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String PLAYER_ID = "player_id";
	public static final String BASE_ID = "base_id";
	public static final String TIRE_ID = "tire_id";
	public static final String PAINT_ID = "paint_id";
	
	public PlayerVehicle(HashMap<String, String> input) {
		super(input);
	}

	public int getPlayerId() {
		return Integer.valueOf(get(PLAYER_ID));
	}
	
	public int getBaseVehicleId() {
		return Integer.valueOf(get(BASE_ID));
	}
	
	public int getCarTireId() {
		return Integer.valueOf(get(TIRE_ID));
	}
	
	public int getPaintId() {
		return Integer.valueOf(get(PAINT_ID));
	}

	public String getName() {
		return get(NAME);
	}
	
	public void setName(String vehicleName) {
		set(NAME,vehicleName);
	}

	public int getId() {
		return Integer.valueOf(get(ID));
	}
}
