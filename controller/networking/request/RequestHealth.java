package controller.networking.request;

// Java Imports
import java.io.IOException;

import controller.networking.response.ResponseChangeHealth;
import core.meta.Constants;
// Custom Imports
//import core.GameServer;
import utility.DataReader;

public class RequestHealth extends GameRequest {

	// Data
	private int health;
	// Responses
	private ResponseChangeHealth responseChangeHealth;

	public RequestHealth() {
		responseChangeHealth = new ResponseChangeHealth();

	}

	@Override
	public void parse() throws IOException {
		health = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
        responseChangeHealth.setUsername(client.getPlayer().getUsername()); 
        responseChangeHealth.setHealthChange(health);
        if(client.getSession() != null) {
        	if(client.getSession().getMapDetails().getMode() == Constants.DD) {
        		client.getSession().updatePlayerRanking(client.getPlayer(), health,System.currentTimeMillis());
        	}
        	client.getSession().addResponseForAll(responseChangeHealth);
        }
        else
        	System.out.println("Client is not in game session: "+this.getClass().getName());
    }
}