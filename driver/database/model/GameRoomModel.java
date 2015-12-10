package driver.database.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import driver.database.DatabaseDriver;
import driver.database.ObjectModel;
import driver.database.record.GameRoom;

public class GameRoomModel {
	public static GameRoom getGameRoomById(int id) {
		try {
			ObjectModel model = DatabaseDriver.getInstance().findById(GameRoom.class, id);
			if(model != null) {
				return new GameRoom(model.getData());
			}
		} catch(SQLException e) {
			//Do not care
		}
		return null;
	}
	
	public static ArrayList<GameRoom> searchForGameRooms(HashMap<String,String> params) {
		ArrayList<GameRoom> list = new ArrayList<GameRoom>();
		ArrayList<ObjectModel> models = DatabaseDriver.getInstance().find(GameRoom.class, params);
		if(models != null) {
			for(ObjectModel model : models) {
				list.add(new GameRoom(model.getData()));
			}
		}
		return list;
	}
	
	public static ArrayList<GameRoom> searchForGameRoomsByName(String name) {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put(GameRoom.ROOM_NAME, name);
		return searchForGameRooms(params);
	}
	
	public static boolean insertGameRoom(GameRoom gameRoom) {
		return gameRoom.save("all");
	}
}
