package networking.request;

import java.io.IOException;
import java.util.HashMap;

import dataAccessLayer.DatabaseDriver;
import dataAccessLayer.GameRoom;
import networking.response.ResponseEnterGameName;
import utility.DataReader;

/**
 * When the user attempts to join a game lobby, they send 
 * their username and lobby id to the server, and the 
 * server responds to the users already in the lobby 
 * through ResponseEnterGameLobby
 *
 */
public class RequestEnterGameName extends GameRequest {
	private String room_name;
	private ResponseEnterGameName response;
	
	public RequestEnterGameName() {
		responses.add(response = new ResponseEnterGameName());
	}
	@Override
	public void parse() throws IOException {
		String username = DataReader.readString(dataInput);
		room_name = DataReader.readString(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		
		
		GameRoom gameRoom = new GameRoom(DatabaseDriver.find(GameRoom.class, "room_name", room_name).remove(0).getData());
		
		if(gameRoom != null) {
			client.getPlayer().setRoom(gameRoom);			
			response.setValid(1);
			response.setUsername(client.getPlayer().getUsername());
			client.getServer().addResponseForRoomExcludingPlayer(gameRoom.getId(), client.getPlayer().getID(), response);
		} else {
			response.setValid(0);
		}
	}

}
