package dataAccessLayer.record;

import java.util.HashMap;

import dataAccessLayer.ObjectModel;

public class Friendship extends ObjectModel {
	public Friendship() {
		super(new HashMap<String,String>());
	}
	
	public Friendship(HashMap<String,String> props) {
		super(props);
	}
}
