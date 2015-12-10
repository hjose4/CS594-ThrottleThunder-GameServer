package controller.networking.request;

// Java Imports
import java.io.IOException;

import controller.networking.response.ResponseFriendList;
import driver.database.model.FriendshipModel;

public class RequestFriendList extends GameRequest {
    private ResponseFriendList responseFriendList;

    public RequestFriendList() {
    	responses.add(responseFriendList = new ResponseFriendList());      
    }

    @Override
    public void parse() throws IOException {}

    @Override
    public void doBusiness() throws Exception { 
       responseFriendList.setFriends(FriendshipModel.getFriends(client.getPlayer()));
    }
}