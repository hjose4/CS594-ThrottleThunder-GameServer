package dataAccessLayer.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import dataAccessLayer.DatabaseDriver;
import dataAccessLayer.ObjectModel;
import dataAccessLayer.record.BaseVehicle;
import dataAccessLayer.record.Player;
import dataAccessLayer.record.PlayerVehicle;

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
	
	public static ArrayList<PlayerVehicle> searchForPlayerVehicles(Player player, int baseId) {
		HashMap<String,String> params = new HashMap<>();
		params.put(PlayerVehicle.PLAYER_ID, player.getId()+"");
		params.put(PlayerVehicle.BASE_ID, baseId+"");
		return searchForPlayerVehicles(params);
	}
	
	public static PlayerVehicle createPlayerVehicleFromBaseVehicle(BaseVehicle vehicle, Player player) {
		if(vehicle != null) {
			HashMap<String,String> data = vehicle.getData();
			data.put(PlayerVehicle.BASE_ID,data.get("id"));
			data.put(PlayerVehicle.PAINT_ID, 0+"");
			data.put(PlayerVehicle.TIRE_ID, 0+"");
			data.put(PlayerVehicle.PLAYER_ID, player.getId()+"");
			data.remove("id");
			PlayerVehicle pVehicle = new PlayerVehicle(data);
			pVehicle.save("all");
			return pVehicle;
		} return null;
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
