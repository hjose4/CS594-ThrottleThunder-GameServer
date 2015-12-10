package controller.networking.request;

// Java Imports
import java.io.IOException;

import controller.networking.response.GameResponse;

public class RequestHeartbeat extends GameRequest {
	public RequestHeartbeat() {
		
	}

	@Override
	public void parse() throws IOException {
		
	}

	@Override
	public void doBusiness() throws Exception {
		
		for (GameResponse reponse : client.getUpdates()) {
			responses.add(reponse);	
		}
		//TODO: this might not be needed
		//client.clearUpdateBuffer();
	}
}
