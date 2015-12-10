package controller.networking.request;

// Java Imports
import java.io.IOException;

import controller.networking.response.GameResponse;
import controller.networking.response.ResponseChat;
// Custom Imports
//import core.GameServer;
import utility.DataReader;

public class RequestChat extends GameRequest {

    // Data
    private String message;
    // Responses
    private ResponseChat responseChat;

    public RequestChat() {
    	 
      //  responses.add(responseString = new ResponseString());
    	responseChat = new ResponseChat();
      
    }

    @Override
    public void parse() throws IOException {
        message = DataReader.readString(dataInput);
    }

    @Override
    public void doBusiness() throws Exception {
        System.out.println(message);
        responseChat.setUsername(client.getPlayer().getUsername()); 
        responseChat.setMessage(message); 
        client.getServer().addResponseForAllOnlinePlayers((GameResponse) responseChat); 
    }
}
