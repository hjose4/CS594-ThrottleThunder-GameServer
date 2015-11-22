package networking.request;

import java.io.IOException;
import networking.response.ResponseEnterLobby;
import utility.DataReader;

/**
 * 
 * When the user attempts to join a lobby, they send their username 
 * and room id to the server, and the server responds to the users 
 * already in the lobby through ResponseEnterLobby
 *
 */
public class RequestEnterLobby extends GameRequest {
	private int roomId;
	private ResponseEnterLobby response;
	
	public RequestEnterLobby() {
		responses.add(response = new ResponseEnterLobby());
	}
	@Override
	public void parse() throws IOException {
		roomId = DataReader.readInt(dataInput);
		
	}

	@Override
	public void doBusiness() throws Exception {
		client.setSession(client.getServer().getGameSessionByRoomId(roomId));		
		if(client.getSession() != null) {
			response.setValid(1);
			response.setUsername(client.getPlayer().getUsername());
			client.getSession().addResponseForAll(client.getPlayer().getId(), response);
		} else {
			response.setValid(0);
		}
	}

}
