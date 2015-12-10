package driver.database.record;

import java.util.HashMap;

import driver.database.ObjectModel;

public class Friendship extends ObjectModel {
	public static final String ID = "id";
	
	public Friendship() {
		super(new HashMap<String,String>());
	}
	
	public Friendship(HashMap<String,String> props) {
		super(props);
	}
	
	public int getId() {
		return Integer.valueOf(get(ID));
	}
}
