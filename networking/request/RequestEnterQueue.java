package networking.request;

import java.io.IOException;
import java.util.ArrayList;

import core.GameSession;
import dataAccessLayer.record.Player;
import networking.response.ResponseEnterQueue;
import utility.DataReader;

/**
 * 
 * When the user attempts to join a lobby, they send their username 
 * and room id to the server, and the server responds to the users 
 * already in the lobby through ResponseEnterLobby
 *
 */
public class RequestEnterQueue extends GameRequest {
	private int roomType;
	private ResponseEnterQueue response;
	
	public RequestEnterQueue() {
		responses.add(response = new ResponseEnterQueue());
	}
	@Override
	public void parse() throws IOException {
		roomType = DataReader.readInt(dataInput);
		
	}

	@Override
	public void doBusiness() throws Exception {
		GameSession session = client.getServer().getGameSessionByRoomType(roomType);		
		if(session != null) {
			client.setSession(session);		
			if(client.getSession() != null) {
				response.setLobbySize(client.getSession().getMaxNumOfPlayers());
				response.setMinSize(client.getSession().getMinNumOfPlayers());
				response.setPlayers(client.getSession().getPlayers());
				client.getSession().addResponseForAll(client.getPlayer().getId(), response);	
				return;
			} else {
				session = client.getServer().createNewGameSession(roomType);
				client.setSession(session);		
				if(client.getSession() != null) {
					response.setLobbySize(client.getSession().getMaxNumOfPlayers());
					response.setMinSize(client.getSession().getMinNumOfPlayers());
					response.setPlayers(client.getSession().getPlayers());
					client.getSession().addResponseForAll(client.getPlayer().getId(), response);	
					return;
				}
			}
		}
		response.setLobbySize(0);
		response.setMinSize(Integer.MAX_VALUE);
		response.setPlayers(new ArrayList<Player>());
	}

}
