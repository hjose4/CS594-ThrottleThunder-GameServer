package json.collections;

import java.util.HashMap;

public class CostCollection {
	private static HashMap<Integer, Integer> costs = new HashMap<>();
	
	public static void addCost(int type, int cost) {
		costs.put(type, cost);
	}
	
	public static int getCost(int id) {
		return costs.get(id);
	}
}
