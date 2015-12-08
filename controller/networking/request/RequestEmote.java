package networking.request;

// Java Imports
import java.io.IOException;
import networking.response.ResponseEmote;
import utility.DataReader;

public class RequestEmote extends GameRequest {

	// Data
	private int emote;
	// Responses
	private ResponseEmote responseEmote;

	public RequestEmote() {
		responses.add(responseEmote = new ResponseEmote());

	}

	@Override
	public void parse() throws IOException {
		emote = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		responseEmote.setUsername(client.getPlayer().getUsername());
		responseEmote.setEmote(emote);
		
		client.getSession().addResponseForAll(responseEmote);
	}
}
