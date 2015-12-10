package controller.networking.request;

// Java Imports
import java.io.IOException;

import controller.networking.response.ResponseRegister;
import driver.database.model.PlayerModel;
import driver.database.record.Player;
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
		Player player = new Player();
		player.setUsername(username);
		player.setPassword(password);
		if (PlayerModel.searchForPlayers(player).size() == 0 && PlayerModel.insertPlayer(player)) {
			responseRegister.setNumber(1);
			client.setPlayer(player);
		} else {
			responseRegister.setNumber(0);
		}

	}
}