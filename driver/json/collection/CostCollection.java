package driver.json.collection;

import java.util.HashMap;

public class CostCollection {
	private static HashMap<Integer, Integer> costs = new HashMap<>();
	
	public static void addCost(int statlevel, int cost) {
		costs.put(statlevel, cost);
	}
	
	public static Integer getCost(int statlevel) {
		if(costs.get(statlevel) != null)
				return Integer.valueOf(costs.get(statlevel));
		return null;
	}
}
