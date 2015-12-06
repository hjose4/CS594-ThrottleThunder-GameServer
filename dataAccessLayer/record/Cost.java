package dataAccessLayer.record;

import java.util.HashMap;

import dataAccessLayer.ObjectModel;


public class Cost extends ObjectModel {
	public static final String ID = "id";
	public static final String STAT_LEVEL = "statLevel";
	public static final String PRICE = "price";
	
	public Cost () {
		super(new HashMap<String,String>());
	}
	public Cost(HashMap<String, String> input) {
		super (input);
	}
	
	public int getId() {
		return Integer.valueOf(get(ID));
	}
	
	public int getStatlevel() {
		return Integer.valueOf(get(STAT_LEVEL));
	}
	
	
	
	public int getPrice() {
		return Integer.valueOf(get(PRICE));
	}
	
	
}
