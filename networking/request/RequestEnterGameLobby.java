package networking.request;

import java.io.IOException;

import core.GameSession;
import networking.response.ResponseEnterGameLobby;
import networking.response.ResponseRenderCharacter;
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
		GameSession session = client.getServer().getGameSessionByRoomId(room_id);
		if(session != null) {
			client.setSession(session);		
			if(client.getSession() != null) {
				response.setValid(1);
				response.setUsername(client.getPlayer().getUsername());
				client.getSession().addResponseForAll(client.getPlayer().getId(), response);
				for(ResponseRenderCharacter responseRenderCharacter : client.getSession().getCharacterUpdates()){
					responses.add(responseRenderCharacter);
				}
				client.getSession().addResponseForRenderCharacters(client);
				return;
			}
		}	
		response.setValid(0);
		response.setUsername(client.getPlayer().getUsername());
	}

}
