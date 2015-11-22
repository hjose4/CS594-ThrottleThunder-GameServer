package dataAccessLayer.record;

import java.util.HashMap;

import dataAccessLayer.ObjectModel;

public class BaseVehicle extends ObjectModel {	
	public static final String NAME = "name";
	public static final String HEALTH = "health";
	public static final String ARMOR = "armor";
	public static final String WEIGHT = "weight";
	public static final String SPEED = "speed";
	public static final String ACCELERATION = "acceleration";
	public static final String CONTROL = "control";
	public static final String ID = "id";
	
	public BaseVehicle(HashMap<String, String> input) {
		super(input); 
	}
	
	public String getName() {
		return get(NAME);
	}

	public void setName(String name) {
		set(NAME,name);
	}

	public double getHealth() {
		return Double.valueOf(get(HEALTH));
	}

	public void setHealth(double health) {
		set(HEALTH,health);
	}

	public double getArmor() {
		return Double.valueOf(get(ARMOR));
	}

	public void setArmor(double armor) {
		set(ARMOR,armor);
	}

	public double getWeight() {
		return Double.valueOf(get(WEIGHT));
	}

	public void setWeight(double weight) {
		set(WEIGHT,weight);
	}

	public double getSpeed() {
		return Double.valueOf(get(SPEED));
	}

	public void setSpeed(double speed) {
		set(SPEED,speed);
	}

	public double getAcceleration() {
		return Double.valueOf(get(ACCELERATION));
	}

	public void setAcceleration(double acceleration) {
		set(ACCELERATION,acceleration);
	}

	public double getControl() {
		return Double.valueOf(get(CONTROL));
	}

	public void setControl(double control) {
		set(CONTROL,control);
	}

	public int getId() {
		return Integer.valueOf(get(ID));
	}
}
