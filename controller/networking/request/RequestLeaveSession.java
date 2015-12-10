package controller.networking.request;

// Java Imports
import java.io.IOException;

import controller.networking.response.GameResponse;
import controller.networking.response.ResponseChat;
// Custom Imports
//import core.GameServer;
import utility.DataReader;

public class RequestLeaveSession extends GameRequest {
    public RequestLeaveSession() {
      
    }

    @Override
    public void parse() throws IOException {
    }

    @Override
    public void doBusiness() throws Exception {
        client.getSession().removeGameClient(client);
    }
}
