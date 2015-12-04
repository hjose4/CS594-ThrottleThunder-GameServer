package dataAccessLayer.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import dataAccessLayer.DatabaseDriver;
import dataAccessLayer.ObjectModel;
import dataAccessLayer.record.PlayerVehicleUpgrade;


public class PlayerVehicleUpgradeModel {
	
	public static PlayerVehicleUpgrade getPvehicleUpgradeById(int id) {
		try {
			ObjectModel model = DatabaseDriver.getInstance().findById(PlayerVehicleUpgrade.class, id);
			if(model != null) {
				return new PlayerVehicleUpgrade(model.getData());
			}
		} catch(SQLException e) {
			//Do not care
		}
		return null;
	}


}
