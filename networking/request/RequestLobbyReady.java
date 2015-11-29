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
	private int carType;
	private int carPaint;
	private int carTires;
	// Responses

	public RequestLobbyReady() {}

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
			client.getPlayer().setCar_id(carType);
			client.getPlayer().setCarPaint(carPaint);
			client.getPlayer().setCarTire(carTires);
		} else {
			System.out.println("Client is not in game session: "+this.getClass().getName());
		}
		
	}
}
