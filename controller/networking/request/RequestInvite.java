package controller.networking.request;

// Java Imports
import java.io.IOException;

import controller.networking.response.ResponseInvite;
// Custom Imports
//import core.GameServer;
import utility.DataReader;

public class RequestInvite extends GameRequest {

	// Data
	private String username;
	private int flag = 0;
	private ResponseInvite responseInvite;

	public RequestInvite() {
		// responses.add(responseString = new ResponseString());
		responseInvite = new ResponseInvite();

	}

	@Override
	public void parse() throws IOException {
		username = DataReader.readString(dataInput);
		//flag = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		if(flag == 0) {
			//Sending Invite
			responseInvite.setUsername(client.getPlayer().getUsername());
			client.getServer().addResponseForUser(username, responseInvite);
		} else {
			//Responding to Invite
		}
	}
}