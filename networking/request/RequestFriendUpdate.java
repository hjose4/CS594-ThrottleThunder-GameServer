package networking.request;

// Java Imports
import java.io.IOException;
import dataAccessLayer.model.FriendshipModel;
import dataAccessLayer.model.PlayerModel;
import dataAccessLayer.record.Player;
import networking.response.ResponseFriendList;
import utility.DataReader;

public class RequestFriendUpdate extends GameRequest {

	// Data
	private String username;
	private int status; // 0 when accept;1 when remove friend

	public RequestFriendUpdate() {

	}

	@Override
	public void parse() throws IOException {
		username = DataReader.readString(dataInput);
		status = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		Player targetPlayer = PlayerModel.getPlayerByUsername(username);
		if (status == 0) {
			//Accept
			FriendshipModel.createFriendship(client.getPlayer(),targetPlayer);
		} else if (status == 1) {
			//Remove
			FriendshipModel.removeFriendship(client.getPlayer(), targetPlayer);
		}		

		ResponseFriendList response;
		
		//Target player friend list
		response = new ResponseFriendList();
		response.setFriends(FriendshipModel.getFriends(targetPlayer));
		client.getServer().addResponseForUser(targetPlayer.getUsername(), response);
	}
}