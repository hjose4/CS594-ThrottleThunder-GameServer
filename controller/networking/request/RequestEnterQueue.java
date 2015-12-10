package controller.networking.request;

import java.io.IOException;
import java.util.ArrayList;

import controller.networking.response.ResponseEnterQueue;
import core.GameSession;
import driver.database.record.Player;
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
		System.out.println("Requesting for room type: " + roomType);
		//Check if the client is in a session
		if(client.getSession() != null) {
			client.getSession().removeGameClient(client);
		}
		
		GameSession session = client.getServer().getGameSessionByRoomType(roomType);		
		if(session != null) {
			session.addGameClient(client);
			if(client.getSession() != null) {
				client.getPlayer().setNotReady();
				client.getPlayer().setLobbyNotReady();
				response.setLobbySize(client.getSession().getMaxNumOfPlayers());
				response.setMinSize(client.getSession().getMinNumOfPlayers());
				response.setPlayers(client.getSession().getPlayers());
				client.getSession().addResponseForAll(client.getPlayer().getId(), response);	
				return;
			}
		}
		
		if(session == null) {
			session = client.getServer().createNewGameSession(roomType);
			session.addGameClient(client);	
			if(client.getSession() != null) {
				client.getPlayer().setNotReady();
				client.getPlayer().setLobbyNotReady();
				response.setLobbySize(client.getSession().getMaxNumOfPlayers());
				response.setMinSize(client.getSession().getMinNumOfPlayers());
				response.setPlayers(client.getSession().getPlayers());
				client.getSession().addResponseForAll(client.getPlayer().getId(), response);	
				return;
			}
		}
		
		response.setLobbySize(0);
		response.setMinSize(Integer.MAX_VALUE);
		response.setPlayers(new ArrayList<Player>());
	}

}
