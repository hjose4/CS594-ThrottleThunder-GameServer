package driver.json.collection;

import java.util.HashMap;

public class CostCollection {
	private static HashMap<Integer, Integer> costs = new HashMap<>();
	
	public static void addCost(int statlevel, int cost) {
		costs.put(statlevel, cost);
	}
	
	public static int getCost(int statlevel) {
		return costs.get(statlevel);
	}
}
