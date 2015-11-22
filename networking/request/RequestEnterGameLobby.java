package networking.request;

import java.io.IOException;

import dataAccessLayer.DatabaseDriver;
import dataAccessLayer.GameRoom;
import networking.response.ResponseEnterGameLobby;
import utility.DataReader;

/**
 * When the user attempts to join a game lobby, they send 
 * their username and lobby id to the server, and the 
 * server responds to the users already in the lobby 
 * through ResponseEnterGameLobby
 *
 */
public class RequestEnterGameLobby extends GameRequest {
	private int room_id;
	private ResponseEnterGameLobby response;
	
	public RequestEnterGameLobby() {
		responses.add(response = new ResponseEnterGameLobby());
	}
	@Override
	public void parse() throws IOException {
		String username = DataReader.readString(dataInput);
		int lobbyId = DataReader.readInt(dataInput);
		room_id = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		GameRoom gameRoom = new GameRoom(DatabaseDriver.findById(GameRoom.class, room_id).getData());
		
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
