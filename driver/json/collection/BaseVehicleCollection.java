package driver.json.collection;

import java.util.HashMap;

import driver.json.record.BaseVehicle;

public class BaseVehicleCollection {
	private static HashMap<Integer,BaseVehicle> vehicles = new HashMap<>();
	
	public static void addVehicle(int id, String name) {
		BaseVehicle model = new BaseVehicle(id,name);
		vehicles.put(model.getCarType(), model);
	}
	
	public static BaseVehicle getVehicle(int id) {
		return vehicles.get(id);
	}
}
