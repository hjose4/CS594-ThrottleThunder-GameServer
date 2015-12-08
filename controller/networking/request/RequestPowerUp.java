package controller.networking.request;

// Java Imports
import java.io.IOException;

import controller.networking.response.*;
import utility.DataReader;

public class RequestPowerUp extends GameRequest {

    // Data
    private int powerId;
 
    // Responses
    private ResponsePowerUp responsePowerUp;
                 
    public RequestPowerUp() {
       
      responses.add(responsePowerUp = new ResponsePowerUp());
    }

    @Override
    public void parse() throws IOException{
        powerId = DataReader.readInt(dataInput);
    }

    @Override
    public void doBusiness() {
    	//do the prizes business here
    	responsePowerUp.setUsername(client.getPlayer().getUsername());
    	responsePowerUp.setPowerId(powerId);
    	
    	if(client.getSession() != null)
    		client.getSession().addResponseForAll(client.getPlayer().getId(), responsePowerUp);
    	else
    		System.out.println("Client is not in game session: "+this.getClass().getName());
    }
}
