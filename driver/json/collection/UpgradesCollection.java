package driver.json.collection;

import java.util.HashMap;

import driver.json.record.UpgradeType;

public class UpgradesCollection {
	private static HashMap<Integer,UpgradeType> upgrades = new HashMap<>();
	
	public static void addUpgradeType(int type, String name, int max) {
		UpgradeType model = new UpgradeType(type,name, max);
		upgrades.put(model.getType(), model);
	}
	
	public static UpgradeType getUpgrade(int id) {
		return upgrades.get(id);
	}
}
