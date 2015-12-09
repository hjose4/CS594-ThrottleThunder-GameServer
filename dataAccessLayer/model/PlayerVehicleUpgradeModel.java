package dataAccessLayer.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataAccessLayer.DatabaseDriver;
import dataAccessLayer.ObjectModel;
import dataAccessLayer.record.Player;
import dataAccessLayer.record.PlayerVehicleUpgrade;


public class PlayerVehicleUpgradeModel {
	
	public static PlayerVehicleUpgrade getPvehicleUpgradeById(int id) {
		try {
			ObjectModel model = DatabaseDriver.getInstance().findById(PlayerVehicleUpgrade.class, id);
			if(model != null) {
				return new PlayerVehicleUpgrade(model.getData());
			}
		} catch(SQLException e) {
			//Do not care
		}
		return null;
	}

	public static ArrayList<PlayerVehicleUpgrade> searchForUpgrades(int carId) {
			ArrayList<PlayerVehicleUpgrade> list = new ArrayList<PlayerVehicleUpgrade>();
			HashMap<String,String> params = new HashMap<>();
			params.put("player_vehicle_id", carId+"");
			List<ObjectModel> models = DatabaseDriver.getInstance().find(PlayerVehicleUpgrade.class, params);
			if(models != null) {
				for(ObjectModel model : models) {
					list.add(new PlayerVehicleUpgrade(model.getData()));
				}
			}
			return list;
	
	}


}
