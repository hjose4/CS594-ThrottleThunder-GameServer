package networking.request;

import java.io.IOException;

import utility.DataReader;
import networking.response.ResponseCollision;

public class RequestCollision extends GameRequest {
	
	// Data
	private String username;
    private int damage;
    // Responses
	private ResponseCollision responseCollision;
	
	public RequestCollision() {
        responses.add(responseCollision = new ResponseCollision());
    }
	
	@Override
	public void parse() throws IOException {
		//parse the datainput here
		//x = DataReader.readFloat(dataInput);
		username = DataReader.readString(dataInput);
		damage = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		//do the Collision business here
		responseCollision.setUsername(username);
		responseCollision.setDamage(damage);
		client.getServer().addResponseForRoom(client.getPlayer().getRoom().getId(), responseCollision);
	}

}
