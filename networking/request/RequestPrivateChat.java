package networking.request;

// Java Imports
import java.io.IOException;
import networking.response.ResponsePrivateChat;
// Custom Imports
//import core.GameServer;
import utility.DataReader;

public class RequestPrivateChat extends GameRequest {

	// Data
	private String message;
	private String username;
	// Responses
	private ResponsePrivateChat response;

	public RequestPrivateChat() {
		responses.add(response = new ResponsePrivateChat());

	}

	@Override
	public void parse() throws IOException {
		username = DataReader.readString(dataInput);
		message = DataReader.readString(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		System.out.println(message);
		//Set username of the client that sent the message
		ResponsePrivateChat responsePrivateChat = new ResponsePrivateChat();
		responsePrivateChat.setUsername(client.getPlayer().getUsername());
		responsePrivateChat.setMessage(message);
		responsePrivateChat.setFlag(1);
		client.getServer().addResponseForUser(username, responsePrivateChat);
		
		response.setUsername(username);
		response.setMessage(message);
		response.setFlag(0);


	}
}
