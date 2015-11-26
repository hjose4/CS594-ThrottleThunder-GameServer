package networking.request;

// Java Imports
import java.io.IOException;

import networking.response.GameResponse;
import networking.response.ResponseChat;
import networking.response.ResponseInvite;
// Custom Imports
//import core.GameServer;
import utility.DataReader;

public class RequestInvite extends GameRequest {

	// Data
	private String username;

	// Responses
	private ResponseInvite responseInvite;

	public RequestInvite() {

		// responses.add(responseString = new ResponseString());
		responseInvite = new ResponseInvite();

	}

	@Override
	public void parse() throws IOException {
		username = DataReader.readString(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
//		boolean newgroup = true;
//		System.out.println(username);
//		responseInvite.setUsernameFrom(client.getPlayer().getUsername());
//		responseInvite.setUsernameTo(username);
//		if (client.getPlayer().getGroup() == null
//				|| client.getPlayer().getGroup().isFinished()) {
//			Group group = new Group(client.getPlayer());
//		}
//		client.getServer().addResponseForUser(username,
//				(GameResponse) responseInvite);
	}
}