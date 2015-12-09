package dataAccessLayer.record;

import java.util.HashMap;

import dataAccessLayer.ObjectModel;

public class Upgrade extends ObjectModel {
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String DESCRIPTION ="description";
	private static final String ARMOR = "armor";
	private static final String HEALTH = "health";
	private static final String ACCELERATION ="acceleration";
	//private static final String  vehicleId; 

	public Upgrade(HashMap<String, String> input) {
		super(input);
	}

	public String getName() {
		return get(NAME);
	}
	
	public void setName(String name) {
		set(NAME,name);
	}
	
	//public void setVehicleId(int vehicle_id) {
		//this.vehicleId = vehicle_id;
	//}

	public String getDescription() {
		return get(DESCRIPTION);
	}

	public void setDescription(String description) {
		set(DESCRIPTION, description);
	}

	public double getArmor() {
		return Integer.valueOf(get(ARMOR));
	}

	public void setArmor(double armor) {
		set(ARMOR,armor);
	}

	public double getHealth() {
		return Integer.valueOf(get(HEALTH));
	}

	public void setHealth(double health) {
		set(HEALTH, health);
	}

	public double getAcceleration() {
		return Integer.valueOf(get(ACCELERATION));
	}

	public void setAcceleration(double acceleration) {
		set(ACCELERATION, acceleration);
	}

	public int getId() {
		return Integer.valueOf(get(ID));
	}

	
}
