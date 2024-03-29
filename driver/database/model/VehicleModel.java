package driver.database.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import driver.database.DatabaseDriver;
import driver.database.ObjectModel;
import driver.database.record.Player;
import driver.database.record.PlayerVehicle;
import driver.json.record.BaseVehicle;

public class VehicleModel {
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
	
	public static PlayerVehicle createPlayerVehicleFromBaseVehicle(BaseVehicle model, Player player) {
		if(model != null) {
			HashMap<String,String> data = new HashMap<>();
			data.put(PlayerVehicle.BASE_ID,model.getCarType()+"");
			data.put(PlayerVehicle.NAME, model.getName());
			data.put(PlayerVehicle.PAINT_ID, 0+"");
			data.put(PlayerVehicle.TIRE_ID, 0+"");
			data.put(PlayerVehicle.PLAYER_ID, player.getId()+"");
			PlayerVehicle vehicle = new PlayerVehicle(data);
			vehicle.save("all");
			return vehicle;
		} return null;
	}
	
	public static PlayerVehicle getPlayerVehicleById(int id) {
		try {
			ObjectModel model = DatabaseDriver.getInstance().findById(PlayerVehicle.class, id);
			if(model != null) {
				return new PlayerVehicle(model.getData());
			}
		} catch(SQLException e) {
			//Do not care
		}
		return null;
	}
	
	public static ArrayList<PlayerVehicle> searchForPlayerVehicles(Player player, BaseVehicle base) {
		HashMap<String,String> params = new HashMap<>();
		params.put(PlayerVehicle.PLAYER_ID, player.getId()+"");
		params.put(PlayerVehicle.BASE_ID,base.getCarType()+"");
		ArrayList<PlayerVehicle> list = new ArrayList<PlayerVehicle>();
		ArrayList<ObjectModel> models = DatabaseDriver.getInstance().find(PlayerVehicle.class, params);
		if(models != null) {
			for(ObjectModel model : models) {
				list.add(new PlayerVehicle(model.getData()));
			}
		}
		return list;
	}
	
	public static ArrayList<PlayerVehicle> searchForPlayerVehicles(HashMap<String,String> params) {
		ArrayList<PlayerVehicle> list = new ArrayList<PlayerVehicle>();
		ArrayList<ObjectModel> models = DatabaseDriver.getInstance().find(PlayerVehicle.class, params);
		if(models != null) {
			for(ObjectModel model : models) {
				list.add(new PlayerVehicle(model.getData()));
			}
		}
		return list;
	}
	
	
	
}
