package dataAccessLayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerModel {
	public static Player getPlayerById(int id) {
		try {
			ObjectModel model = DatabaseDriver.findById(Player.class, id);
			if(model != null) {
				return new Player(model.getData());
			}
		} catch(SQLException e) {
			//Do not care
		}
		return null;
	}
	
	public static ArrayList<Player> searchForPlayers(HashMap<String,String> params) {
		ArrayList<Player> list = new ArrayList<Player>();
		ArrayList<ObjectModel> models = DatabaseDriver.find(Player.class, params);
		if(models != null) {
			for(ObjectModel model : models) {
				list.add(new Player(model.getData()));
			}
		}
		return list;
	}
	
	public static ArrayList<Player> searchForPlayers(Player player) {
		return searchForPlayers(player.getData());
	}
	
	public static boolean insertPlayer(Player player) {
		return player.save("all");
	}
}
