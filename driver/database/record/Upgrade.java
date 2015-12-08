package dataAccessLayer.record;

import java.util.HashMap;

import dataAccessLayer.ObjectModel;

public class Upgrade extends ObjectModel {
	private int id;
	private String name;
	private String description;
	private double armor;
	private double health;
	private double acceleration;
	private int vehicleId; 

	public Upgrade(HashMap<String, String> input) {
		super(input);
	}

	public String getName() {
		return name;
	}
	
	

	public void setName(String name) {
		this.name = name;
	}
	
	public void setVehicleId(int vehicle_id) {
		this.vehicleId = vehicle_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getArmor() {
		return armor;
	}

	public void setArmor(double armor) {
		this.armor = armor;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	public int getId() {
		return id;
	}

	public int getPrice() {
		// TODO Auto-generated method stub
		return 0;
	}
}
