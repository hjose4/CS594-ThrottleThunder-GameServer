package networking.request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import core.GameSession;
import dataAccessLayer.DatabaseDriver;
import dataAccessLayer.GameRoom;
import dataAccessLayer.ObjectModel;
import networking.response.ResponseCreateLobby;
import utility.DataReader;

public class RequestCreateLobby extends GameRequest {
	private String room_name;
	private int status;
	private ResponseCreateLobby response;
	
	public RequestCreateLobby() {
		responses.add(response = new ResponseCreateLobby());
	}
	@Override
	public void parse() throws IOException {		
		room_name = DataReader.readString(dataInput);
		int gameid = DataReader.readInt(dataInput); //Dont need
		status = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		ArrayList<ObjectModel> gameRoom = DatabaseDriver.getInstance().find(GameRoom.class, "room_name", room_name);
		
		if(gameRoom.size() == 0) {
			HashMap<String, String> values = new HashMap<String, String>(); 
			values.put("type", 0+"");
			values.put("time_started", (System.currentTimeMillis()/1000)+"");
			values.put("map_name", "");
			values.put("room_name", room_name);
			values.put("status", status+"");
			GameRoom room = new GameRoom(values);
			DatabaseDriver.insert(room);
			client.getPlayer().setRoom(room);
			response.setValid(1);
			
			GameSession session = new GameSession(client.getServer());
			client.getServer().addToActiveSessions(session);
			response.setGameName(room_name);
			response.setUsername(client.getPlayer().getUsername());
			client.getServer().addResponseForAllOnlinePlayers(client.getId(), response);
		} else {
			response.setValid(0);
		}
	}
}
