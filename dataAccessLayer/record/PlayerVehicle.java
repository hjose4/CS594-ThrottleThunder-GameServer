package dataAccessLayer.record;

import java.util.HashMap;

import dataAccessLayer.DatabaseDriver;

public class PlayerVehicle extends BaseVehicle {
	public static final String PLAYER_ID = "player_id";
	public static final String BASE_ID = "base_id";
	
	public PlayerVehicle(HashMap<String, String> input) {
		super(input);
	}

	public int getPlayerId() {
		return Integer.valueOf(get(PLAYER_ID));
	}
	
	public int getBaseVehicleId() {
		return Integer.valueOf(get(BASE_ID));
	}
	
	@Override
	public boolean save(String field_to_save) {
		HashMap<String,String> props = new HashMap<>();
		props.put(ID, get(ID));
		props.put(BASE_ID, get(BASE_ID));
		props.put(PLAYER_ID, get(PLAYER_ID));
		props.put(NAME, get(NAME));
		//Updating an existing object
		if(field_to_save.equals("all") && get(ID) != null) {
			/*Not recommended: - why not just do one update statement instead of doing N times update.
			for(String field : data.keySet()) {				
				if  (!(field.equals("id"))) {
					DatabaseDriver.update(this.getClass(), Integer.valueOf(get("id")), field, get(field));
				}
			}*/
			return DatabaseDriver.update(this.getClass(), Integer.valueOf(props.get(ID)), props);
		} else if (!field_to_save.equals("id")) {
			if(props.get(ID) == null || !DatabaseDriver.alreadyExists(this.getClass(), props.get(ID))) {
				int id = DatabaseDriver.insert(this.getClass(),props);
				if(id > 0)
					set(ID,id);
				return id > 0;
			} else 
				return DatabaseDriver.update(this.getClass(),Integer.valueOf(props.get(ID)), field_to_save, get(field_to_save));
		}
		return false;
	}
}
