package dataAccessLayer.record;

import java.util.HashMap;
import dataAccessLayer.ObjectModel;

public class PlayerVehicleUpgrade  extends ObjectModel {
	
	public static final String ID = "id";
	public static final String UPGRADE_ID = "upgrade_id";
	public static final String PVEHICLE_ID  = "player_vehicle_id";
	
	public PlayerVehicle(HashMap<String, String> input) {
		super(input);
	}

	public int getPlayerVehicleId() {
		return Integer.valueOf(get(PVEHICLE_ID));
	}
	
	public int getUpgradeId() {
		return Integer.valueOf(get(UPGRADE_ID));
	}
	
	public void setPlayerVehicleId(int vehicleId) {
		set(PVEHICLE_ID,vehicleId);
	}
	
	public void setUpgradeId(int upgradeId) {
		set(UPGRADE_ID, upgradeId);
	}


}
