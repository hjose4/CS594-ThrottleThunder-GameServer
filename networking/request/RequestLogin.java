package networking.request;

// Java Imports
import java.io.IOException;
import java.util.HashMap;

import dataAccessLayer.DatabaseDriver;
import dataAccessLayer.GameRoom;
import dataAccessLayer.Player;
import networking.response.ResponseLogin;
// Custom Imports
//import core.GameServer;
import utility.DataReader;

public class RequestLogin extends GameRequest {

	// Data
	private String username;
	private String password;
	// Responses
	private ResponseLogin responseAuth;

	public RequestLogin() {
		responses.add(responseAuth = new ResponseLogin());

	}

	@Override
	public void parse() throws IOException {
		username = DataReader.readString(dataInput);
		password = DataReader.readString(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		HashMap<String, String> values = new HashMap<String, String>(); 
		values.put("username", username);
		values.put("password", password);
	
		Player player = new Player(DatabaseDriver.find(Player.class, values).remove(0).getData());

		if (player.getID() > 0) {
			System.out.println("Connected !");
			client.setPlayer(player);
			responseAuth.setAnswer((short) 1);
		} else {
			System.out.println("Wrong credentials");
			responseAuth.setAnswer((short) 0);
		}
	}
}
