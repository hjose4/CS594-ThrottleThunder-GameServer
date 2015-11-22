package networking.request;

// Java Imports
import java.io.IOException;
import java.util.ArrayList;

import dataAccessLayer.model.PlayerModel;
import dataAccessLayer.record.Player;
import networking.response.ResponseLogin;
// Custom Imports
//import core.GameServer;
import utility.DataReader;

public class RequestLogin extends GameRequest {

	// Data
	private String username;
	private String password;
	// Responses
	private ResponseLogin responseLogin;

	public RequestLogin() {
		responses.add(responseLogin = new ResponseLogin());

	}

	@Override
	public void parse() throws IOException {
		username = DataReader.readString(dataInput);
		password = DataReader.readString(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		Player player = new Player();
		player.setPassword(password);
		player.setUsername(username);
		ArrayList<Player> players = PlayerModel.searchForPlayers(player);

		if (players.size() > 0) {
			System.out.println("Connected !");
			client.setPlayer(players.remove(0));
			responseLogin.setAnswer(1);
		} else {
			System.out.println("Wrong credentials");
			responseLogin.setAnswer(0);
		}
	}
}
