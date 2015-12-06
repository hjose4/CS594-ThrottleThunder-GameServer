package dataAccessLayer.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import dataAccessLayer.DatabaseDriver;
import dataAccessLayer.ObjectModel;
import dataAccessLayer.record.Cost;
import dataAccessLayer.record.Upgrade;

public class CostModel {
	
	public static Cost getCostBystatlevel(int statlevel) {
		
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("statLevel", statlevel+"");
			ArrayList<ObjectModel> model = DatabaseDriver.getInstance().find(Cost.class, param);
			if(model != null) {
				return new Cost(model.get(0).getData());
			}
		
		return null;
	}
	
}
