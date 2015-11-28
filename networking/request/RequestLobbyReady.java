package networking.request;

// Java Imports
import java.io.IOException;
import java.util.ArrayList;

import dataAccessLayer.model.PlayerModel;
import dataAccessLayer.record.Player;
import networking.response.ResponseLobbyReady;
// Custom Imports
//import core.GameServer;
import utility.DataReader;

public class RequestLobbyReady extends GameRequest {

	// Data
	private String username;
	private int carType;
	private int carPaint;
	private int carTires;
	// Responses
	private ResponseLobbyReady responseLobbyReady;

	public RequestLobbyReady() {
		responses.add(responseLobbyReady = new ResponseLobbyReady());

	}

	@Override
	public void parse() throws IOException {
		carType = DataReader.readInt(dataInput);
		carPaint = DataReader.readInt(dataInput);
		carTires = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		responseLobbyReady.setUsername(client.getPlayer().getUsername());
		responseLobbyReady.setCarType(carType);
		responseLobbyReady.setCarPaint(carPaint);
		responseLobbyReady.setCarTires(carTires);
		client.getSession().addResponseForAll(responseLobbyReady);
	}
}
