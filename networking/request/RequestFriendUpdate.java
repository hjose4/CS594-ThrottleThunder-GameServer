package networking.request;

// Java Imports
import java.io.IOException;

import core.GameServer;
import dataAccessLayer.model.FriendshipModel;
import dataAccessLayer.model.PlayerModel;
import networking.response.GameResponse;
import networking.response.ResponseChat;
import networking.response.ResponseFriendRequest;
import utility.DataReader;

public class RequestFriendUpdate extends GameRequest {

	// Data
	private String username;
	private int status; // 0 when accept;1 when remove friend

	// Responses
	private ResponseFriendRequest responseFriendUpdate;

	public RequestFriendUpdate() {

		// responses.add(responseString = new ResponseString());
		responseFriendUpdate = new ResponseFriendRequest();

	}

	@Override
	public void parse() throws IOException {
		username = DataReader.readString(dataInput);
		status = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		responseFriendUpdate.setUsernameFrom(client.getPlayer().getUsername());
		responseFriendUpdate.setStatus(status);

		if (status == 0) {
			//Accept
			FriendshipModel.createFriendship(client.getPlayer(),PlayerModel.getPlayerByUsername(username));
		} else if (status == 1) {
			//Remove
			//TODO
		}		

		client.getServer().addResponseForUser(username, responseFriendUpdate);
	}
}