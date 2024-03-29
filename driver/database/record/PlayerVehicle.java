package driver.database.record;

import java.util.HashMap;

import driver.database.ObjectModel;

public class PlayerVehicle extends ObjectModel {
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String PLAYER_ID = "player_id";
	public static final String BASE_ID = "base_id";
	public static final String TIRE_ID = "tire_id";
	public static final String PAINT_ID = "paint_id";
	public static final String STATS_SPEED = "stats_speed";
	public static final String STATS_HANDLING = "stats_handling";
	public static final String STATS_ARMOR = "stats_armor";
	public static final String STATS_HEALTH = "stats_health";
	
	public PlayerVehicle(HashMap<String, String> input) {
		super(input);
	}

	public int getPlayerId() {
		return Integer.valueOf(get(PLAYER_ID));
	}
	
	public int getBaseVehicleId() {
		return Integer.valueOf(get(BASE_ID));
	}
	
	public int getCarTireId() {
		return Integer.valueOf(get(TIRE_ID));
	}
	
	public int getPaintId() {
		return Integer.valueOf(get(PAINT_ID));
	}

	public String getName() {
		return get(NAME);
	}
	
	public void setName(String vehicleName) {
		set(NAME,vehicleName);
	}

	public int getId() {
		return Integer.valueOf(get(ID));
	}
	
	public void setPaint(int paintId) {
		set(PAINT_ID,paintId);
	}
	
	public void setTire(int tireId) {
		set(TIRE_ID,tireId);
	}
	
	public int getSpeedUpgrade() {
		return Integer.valueOf(get(STATS_SPEED));
	}
	
	public void setSpeedUpgrade(int value) {
		System.out.println("Speed " + value);
		set(STATS_SPEED,value);
	}
	
	public boolean incrementSpeedUpgrade() {
		if(getSpeedUpgrade() < 7) {
			setSpeedUpgrade(getSpeedUpgrade()+1);
			return true;
		}
		
		return false;
	}
	
	public int getHandlingUpgrade() {
		return Integer.valueOf(get(STATS_HANDLING));
	}
	
	public void setHandlingUpgrade(int value) {
		System.out.println("Handling " + value);
		set(STATS_HANDLING,value);
	}
	
	public boolean incrementHandlingUpgrade() {
		if(getHandlingUpgrade() < 7) {
			this.setHandlingUpgrade(this.getHandlingUpgrade()+1);
			return true;
		}
		
		return false;
	}
	
	public int getArmorUpgrade() {
		return Integer.valueOf(get(STATS_ARMOR));
	}
	
	public void setArmorUpgrade(int value) {
		System.out.println("Armor " + value);
		set(STATS_ARMOR,value);
	}
	
	public boolean incrementArmorUpgrade() {
		if(this.getArmorUpgrade() < 7) {
			this.setArmorUpgrade(this.getArmorUpgrade()+1);
			return true;
		}
		
		return false;
	}
	
	public int getHealthUpgrade() {
		return Integer.valueOf(get(STATS_HEALTH));
	}
	
	public void setHealthUpgrade(int value) {
		System.out.println("Health " + value);
		set(STATS_HEALTH,value);
	}
	
	public boolean incrementHealthUpgrade() {
		if(this.getHealthUpgrade() < 7) {
			this.setHealthUpgrade(this.getHealthUpgrade()+1);
			return true;
		}
		
		return false;
	}

}
