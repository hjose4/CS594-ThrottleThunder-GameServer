package networking.request;

// Java Imports
import java.io.IOException;
import java.util.ArrayList;
import dataAccessLayer.Player;
import dataAccessLayer.PlayerModel;
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
		Player player = new Player();
		player.setPassword(password);
		player.setUsername(username);
		ArrayList<Player> players = PlayerModel.searchForPlayers(player);

		if (players.size() > 0) {
			System.out.println("Connected !");
			client.setPlayer(players.remove(0));
			responseAuth.setAnswer((short) 1);
		} else {
			System.out.println("Wrong credentials");
			responseAuth.setAnswer((short) 0);
		}
	}
}
