package networking.request;

import java.io.IOException;
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
		client.setSession(client.getServer().getGameSessionByRoomId(room_id));		
		if(client.getSession() != null) {
			response.setValid(1);
			response.setUsername(client.getPlayer().getUsername());
			client.getSession().addResponseForAll(client.getPlayer().getId(), response);
		} else {
			response.setValid(0);
		}
	}

}
