package dataAccessLayer;

import java.util.HashMap;

public class Vehicle extends ObjectModel {
	private int id;
	private String name;
	private double health;
	private double armor;
	private double weight;
	private double speed;
	private double acceleration;
	private double control;
	
	public Vehicle(HashMap<String, String> input)
	{
		super(input); 
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public double getArmor() {
		return armor;
	}

	public void setArmor(double armor) {
		this.armor = armor;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	public double getControl() {
		return control;
	}

	public void setControl(double control) {
		this.control = control;
	}

	public int getId() {
		return id;
	}
}
