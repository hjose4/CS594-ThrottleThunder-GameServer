package networking.request;

import java.io.IOException;

import dataAccessLayer.model.GameRoomModel;
import dataAccessLayer.record.GameRoom;
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
		room_id = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		GameRoom gameRoom = GameRoomModel.getGameRoomById(room_id);
		
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
