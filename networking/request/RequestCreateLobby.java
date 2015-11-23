package networking.request;

import java.io.IOException;
import java.util.Date;

import core.GameSession;
import dataAccessLayer.model.GameRoomModel;
import dataAccessLayer.record.GameRoom;
import networking.response.ResponseCreateLobby;
import utility.DataReader;

public class RequestCreateLobby extends GameRequest {
	private String room_name;
	private int status;
	private int game_mode;
	private ResponseCreateLobby response;
	
	public RequestCreateLobby() {
		responses.add(response = new ResponseCreateLobby());
	}
	@Override
	public void parse() throws IOException {		
		room_name = DataReader.readString(dataInput);
		game_mode = DataReader.readInt(dataInput);
		status = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		if(client.getServer().getGameSessionByRoomName(room_name) == null) {
			GameRoom room = new GameRoom();
			room.setType(game_mode);
			room.setTimeStarted(new Date());
			room.setMapName(room_name);
			room.setRoomName(room_name);
			room.setStatus(status);
			GameRoomModel.insertGameRoom(room);
			response.setValid(1);
			
			GameSession session = new GameSession(client.getServer(),room);
			client.getServer().addToActiveSessions(session);
			client.setSession(session);
			response.setGameName(room_name);
			response.setUsername(client.getPlayer().getUsername());
			client.getServer().addResponseForAllOnlinePlayers(client.getId(), response);
		} else {
			response.setGameName(room_name);
			response.setUsername(client.getPlayer().getUsername());
			response.setValid(0);
		}
	}
}
