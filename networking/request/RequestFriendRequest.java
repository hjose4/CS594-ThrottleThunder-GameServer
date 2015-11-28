package networking.request;

// Java Imports
import java.io.IOException;
import networking.response.ResponseFriendRequest;
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
    	
        response.setUsernameFrom(client.getPlayer().getUsername());
        response.setStatus(0);
        client.getServer().addResponseForUser(username,response); 
       
    }
}