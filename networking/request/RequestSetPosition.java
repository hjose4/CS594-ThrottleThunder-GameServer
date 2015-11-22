package networking.request;

// Java Imports
import java.io.IOException;

import networking.response.ResponseSetPosition;
// Custom Imports
//import core.GameServer;

public class RequestSetPosition extends GameRequest {

	// Responses
	private ResponseSetPosition responseSetPosition;

	public RequestSetPosition() {
		responses.add(responseSetPosition = new ResponseSetPosition());
	}

	@Override
	public void parse() throws IOException {
		
	}

	@Override
	public void doBusiness() throws Exception {
		
	}
}
