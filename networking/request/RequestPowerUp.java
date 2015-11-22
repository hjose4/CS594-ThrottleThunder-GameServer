package networking.request;

// Java Imports
import java.io.IOException;

// Custom Imports
//import core.GameServer;
import networking.response.*;
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
    	client.getServer().addResponseForRoom(client.getPlayer().getRoom().getId(), responsePowerUp);
    }
}
