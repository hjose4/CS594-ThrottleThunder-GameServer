package dataAccessLayer.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import dataAccessLayer.DatabaseDriver;
import dataAccessLayer.ObjectModel;
import dataAccessLayer.record.Upgrade;

public class UpgradeModel {
	
	public static Upgrade getUpgradeById(int id) {
		try {
			ObjectModel model = DatabaseDriver.getInstance().findById(Upgrade.class, id);
			if(model != null) {
				return new Upgrade(model.getData());
			}
		} catch(SQLException e) {
			//Do not care
		}
		return null;
	}
	
}
