package controller.networking.request;

// Java Imports
import java.io.IOException;

import controller.networking.response.ResponseFriendRequest;
import driver.database.model.FriendshipModel;
import driver.database.model.PlayerModel;
import driver.database.record.Player;
import utility.DataReader;

public class RequestFriendRequest extends GameRequest {
    private String username;
    private ResponseFriendRequest response;

    public RequestFriendRequest() {
    	response = new ResponseFriendRequest();
      
    }

    @Override
    public void parse() throws IOException {
        username = DataReader.readString(dataInput);
    }

    @Override
    public void doBusiness() throws Exception {
    	Player targetedPlayer = PlayerModel.getPlayerByUsername(username);
    	//Check if the player exists, make sure it is not sending to self, and make sure they are not already friends
    	if(targetedPlayer != null && !username.equals(client.getPlayer().getUsername()) && FriendshipModel.getFriendship(client.getPlayer(), targetedPlayer) == null) {
	        response.setUsernameFrom(client.getPlayer().getUsername());
	        client.getServer().addResponseForUser(username,response); 
    	}       
    }
}