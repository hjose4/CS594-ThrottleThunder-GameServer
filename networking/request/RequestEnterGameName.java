package networking.request;

import java.io.IOException;
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
		room_name = DataReader.readString(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		client.setSession(client.getServer().getGameSessionByRoomName(room_name));
		if(client.getSession() != null) {
			response.setValid(1);
			response.setUsername(client.getPlayer().getUsername());
			client.getSession().addResponseForAll(client.getPlayer().getId(), response);
		} else {
			response.setValid(0);
		}
	}

}
