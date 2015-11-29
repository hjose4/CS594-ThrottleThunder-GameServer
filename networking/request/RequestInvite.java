package networking.request;

// Java Imports
import java.io.IOException;
import networking.response.ResponseInvite;
// Custom Imports
//import core.GameServer;
import utility.DataReader;

public class RequestInvite extends GameRequest {

	// Data
	private String username;
	// Responses
	private ResponseInvite responseInvite;

	public RequestInvite() {
		// responses.add(responseString = new ResponseString());
		responseInvite = new ResponseInvite();

	}

	@Override
	public void parse() throws IOException {
		username = DataReader.readString(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		if(client.getSession() != null) {
			responseInvite.setRoomId(client.getSession().getGameRoom().getId());
			client.getServer().addResponseForUser(username, responseInvite);
		}else{
			System.out.println("Client is not in game session: "+this.getClass().getName());
		}
		
	}
}