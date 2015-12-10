package controller.networking.request;

// Java Imports
import java.io.IOException;
import java.util.ArrayList;

import controller.networking.response.ResponseEnterQueue;
import core.GameSession;
import driver.database.record.Player;
import utility.DataReader;

public class RequestEnterGameLobby extends GameRequest {

	// Data
	private int gameId;
	// Responses
	private ResponseEnterQueue responseEnterQueue;

	public RequestEnterGameLobby() {

	}

	@Override
	public void parse() throws IOException {
		gameId = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		GameSession session = client.getServer().getGameRoomFromSessionsById(gameId).getGameSession();
		
		if(session != null) {
			client.setSession(session);		
			if(client.getSession() != null) {
				responseEnterQueue.setLobbySize(client.getSession().getMaxNumOfPlayers());
				responseEnterQueue.setMinSize(client.getSession().getMinNumOfPlayers());
				responseEnterQueue.setPlayers(client.getSession().getPlayers());
				client.getSession().addResponseForAll(client.getPlayer().getId(), responseEnterQueue);	
				return;
			}
		}
		responseEnterQueue.setLobbySize(0);
		responseEnterQueue.setMinSize(Integer.MAX_VALUE);
		responseEnterQueue.setPlayers(new ArrayList<Player>());
	}
}
