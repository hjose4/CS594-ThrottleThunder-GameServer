package networking.request;

// Java Imports
import java.io.IOException;
import networking.response.ResponseFriendRequest;
import utility.DataReader;

public class RequestFriendRequest extends GameRequest {

    // Data
    private String username;
    private int status;
    
    // Responses
    private ResponseFriendRequest responseFriendUpdate;

    public RequestFriendRequest() {
    	responseFriendUpdate = new ResponseFriendRequest();
      
    }

    @Override
    public void parse() throws IOException {
        username = DataReader.readString(dataInput);
    }

    @Override
    public void doBusiness() throws Exception {
    	
        responseFriendUpdate.setUsernameFrom(client.getPlayer().getUsername());
        //TODO
        //Player.getPlayer(username).addRequest(client.getPlayer().getUsername());
        client.getServer().addResponseForUser(username,responseFriendUpdate); 
       
    }
}