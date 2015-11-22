package networking.request;

// Java Imports
import java.io.IOException;
import java.util.HashMap;

import dataAccessLayer.DatabaseDriver;
import dataAccessLayer.Player;
// Custom Imports
//import core.GameServer;
import networking.response.ResponseRegister;
import utility.DataReader;

/**
 * Client registers a new account with the server which 
 * includes a username and password. The server also 
 * keeps the user�s email for recovering account 
 * information. The server validates this and responds 
 * with ResponseRegistration.
 *
 */
public class RequestRegister extends GameRequest {

	// Data
	private String username;
	private String password;
	// Responses
	private ResponseRegister responseRegister;

	public RequestRegister() {
		responses.add(responseRegister = new ResponseRegister());
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
		
		Player player = new Player(values);
		int player_id = DatabaseDriver.insert(player);
		if (player_id != 0) {
			responseRegister.setNumber(1);
		} else {
			responseRegister.setNumber(0);
		}

	}
}