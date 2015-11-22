package dataAccessLayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class VehicleModel {
	public static BaseVehicle getBaseVehicleById(int id) {
		try {
			ObjectModel model = DatabaseDriver.findById(BaseVehicle.class, id);
			if(model != null) {
				return new BaseVehicle(model.getData());
			}
		} catch(SQLException e) {
			//Do not care
		}
		return null;
	}
	
	public static ArrayList<BaseVehicle> searchForBaseVehicles(HashMap<String,String> params) {
		ArrayList<BaseVehicle> list = new ArrayList<BaseVehicle>();
		ArrayList<ObjectModel> models = DatabaseDriver.find(BaseVehicle.class, params);
		if(models != null) {
			for(ObjectModel model : models) {
				list.add(new BaseVehicle(model.getData()));
			}
		}
		return list;
	}
	
	public static ArrayList<PlayerVehicle> searchForPlayerVehiclesByPlayerId(int player_id) {
		HashMap<String,String> params = new HashMap<>();
		params.put(PlayerVehicle.PLAYER_ID, player_id+"");
		return searchForPlayerVehicles(params);
	}
	
	public static PlayerVehicle createPlayerVehicleFromBaseVehicle(int id) {
		BaseVehicle vehicle = getBaseVehicleById(id);
		return createPlayerVehicleFromBaseVehicle(vehicle);
	}
	
	public static PlayerVehicle createPlayerVehicleFromBaseVehicle(BaseVehicle vehicle) {
		HashMap<String,String> data = vehicle.getData();
		data.put("base_id",data.get("id"));
		data.remove("id");
		return new PlayerVehicle(data);
	}
	
	public static PlayerVehicle getPlayerVehicleById(int id) {
		try {
			ObjectModel model = DatabaseDriver.findById(PlayerVehicle.class, id);
			if(model != null) {
				return new PlayerVehicle(model.getData());
			}
		} catch(SQLException e) {
			//Do not care
		}
		return null;
	}
	
	public static ArrayList<PlayerVehicle> searchForPlayerVehicles(HashMap<String,String> params) {
		ArrayList<PlayerVehicle> list = new ArrayList<PlayerVehicle>();
		ArrayList<ObjectModel> models = DatabaseDriver.find(PlayerVehicle.class, params);
		if(models != null) {
			for(ObjectModel model : models) {
				list.add(new PlayerVehicle(model.getData()));
			}
		}
		return list;
	}
	
	public static boolean insertVehicle(BaseVehicle vehicle) {
		return vehicle.save("all");
	}
}
