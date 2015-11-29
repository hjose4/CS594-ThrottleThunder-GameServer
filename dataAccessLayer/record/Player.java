package dataAccessLayer.record;

import java.util.HashMap;
import dataAccessLayer.ObjectModel;
import model.Position;

public class Player extends ObjectModel {
	public static final String ID = "id";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String CURRENCY = "currency";
	
	private Position pos;
	private boolean isReady = false;
	private int carId, carPaint, carTire;
	private int lastPrize = 0;
	
	public Player() {
		super (new HashMap<String,String>());
		pos = new Position();
	}
	
	public Player(HashMap<String, String> input) {
		super (input);
		pos = new Position();
	}
	
	public String getUsername() {
		return get(USERNAME);
	}
	
	public int getId() {
		return Integer.valueOf(get(ID));
	}
	
	public int getCurrency() {
		return Integer.valueOf(get(CURRENCY));
	}
	
	public void setCurrency(int currency) {
		set(CURRENCY,currency);
	}
	
	public void setPassword(String password) {
		set(PASSWORD,password);
	}
	
	public void setUsername(String username) {
		set(USERNAME,username);
	}
	
	public Position getPosition(){
		return pos;
	}
	
	public void setPosition(float x, float y, float z, float h, float p, float r){
		pos.setX(x);
		pos.setY(y);
		pos.setZ(z);
		pos.setH(h);
		pos.setP(p);
		pos.setZ(r);
	}
	public void setForces(float steering, float wheelforce, float brakeforce){
		pos.setSteering(steering);
		pos.setWheelforce(wheelforce);
		pos.setBrakeforce(brakeforce);
	}


	public boolean isReady() {
		return isReady;
	}
	public void setReady() {
		this.isReady = true;
	}
	public void setNotReady(){
		this.isReady = false;
	}
	
	public void setLastPrize(int prize) {
		lastPrize = prize;
	}
	
	public int getLastPrize() {
		return lastPrize;
	}

	public int getCarPaint() {
		return carPaint;
	}

	public void setBaseCarId(int carId) {
		this.carId = carId;
	}
	
	public int getBaseCarId(int carId) {
		return this.carId;		
	}
	public void setCarPaint(int carPaint) {
		this.carPaint = carPaint;
	}

	public int getCarTire() {
		return carTire;
	}

	public void setCarTire(int carTire) {
		this.carTire = carTire;
	}
}
