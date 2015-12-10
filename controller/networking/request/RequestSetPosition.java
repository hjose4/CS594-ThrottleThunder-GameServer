package controller.networking.request;

// Java Imports
import java.io.IOException;
import java.util.HashMap;

import controller.networking.response.ResponseSetPosition;
import driver.data.model.Position;
import driver.database.record.Player;

public class RequestSetPosition extends GameRequest {

	// Responses
	private ResponseSetPosition responseSetPosition;

	public RequestSetPosition() {
		responses.add(responseSetPosition = new ResponseSetPosition());
	}

	@Override
	public void parse() throws IOException {
		//Parse nothing
	}

	@Override
	public void doBusiness() throws Exception {
		if(client.getSession() != null)
			responseSetPosition.setStartingPositions(client.getSession().getStartingPositions());
		else {
			System.out.println("Client is not in game session: "+this.getClass().getName());
			responseSetPosition.setStartingPositions(new HashMap<Player,Position>());
		}
	}
}
