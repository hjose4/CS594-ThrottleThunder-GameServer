package dataAccessLayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UpgradeModel {
	public static Upgrade getVehicleById(int id) {
		try {
			ObjectModel model = DatabaseDriver.findById(BaseVehicle.class, id);
			if(model != null) {
				return new Upgrade(model.getData());
			}
		} catch(SQLException e) {
			//Do not care
		}
		return null;
	}
	
	public static ArrayList<Upgrade> searchForVehicles(HashMap<String,String> params) {
		ArrayList<Upgrade> list = new ArrayList<Upgrade>();
		ArrayList<ObjectModel> models = DatabaseDriver.find(BaseVehicle.class, params);
		if(models != null) {
			for(ObjectModel model : models) {
				list.add(new Upgrade(model.getData()));
			}
		}
		return list;
	}
	
	public static boolean insertUpgrade(Upgrade upgrade) {
		return upgrade.save("all");
	}
}
