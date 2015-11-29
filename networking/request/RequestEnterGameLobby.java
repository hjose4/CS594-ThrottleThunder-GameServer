package networking.request;

// Java Imports
import java.io.IOException;
import java.util.ArrayList;

import core.GameSession;
import dataAccessLayer.record.Player;
import networking.response.ResponseEnterQueue;
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
		responseEnterQueue.setPlayers(new ArrayList<Player>());
	}
}
