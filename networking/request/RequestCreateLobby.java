package networking.request;

import java.io.IOException;
import core.GameSession;
import dataAccessLayer.GameRoom;
import dataAccessLayer.GameRoomModel;
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
		status = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		if(GameRoomModel.searchForGameRoomsByName(room_name).size() == 0) {
			GameRoom room = new GameRoom();
			room.setType(0);
			room.setTimeStarted(System.currentTimeMillis()/1000);
			room.setMapName("");
			room.setRoomName(room_name);
			room.setStatus(status);
			GameRoomModel.insertGameRoom(room);
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
