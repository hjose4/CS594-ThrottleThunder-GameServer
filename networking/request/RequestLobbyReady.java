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
		responseLobbyReady = new ResponseLobbyReady();
	}

	@Override
	public void parse() throws IOException {
		carType = DataReader.readInt(dataInput);
		carPaint = DataReader.readInt(dataInput);
		carTires = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		if(client.getSession() != null) {
//			if(!this.client.getPlayer().isReady()){
//				this.client.getPlayer().setReady();
//				//We need more than one player before we can start
//				System.out.println("Number of clients: " + client.getSession().getGameClients().size());
//				if(allReady() && client.getSession().getGameClients().size() > 1){
//					this.client.getSession().nextPhase();
//					for(Player player : client.getSession().getPlayers()) {
//						player.setNotReady();
//					}
//				}
//			}
			responseLobbyReady.setUsername(client.getPlayer().getUsername());
			responseLobbyReady.setCarType(carType);
			responseLobbyReady.setCarPaint(carPaint);
			responseLobbyReady.setCarTires(carTires);
			client.getSession().addResponseForAll(responseLobbyReady);
		} else {
			System.out.println("Client is not in game session: "+this.getClass().getName());
		}
		
	}
}
