package networking.request;

import java.io.IOException;

import core.GameClient;
import networking.response.ResponseLogout;

public class RequestLogout extends GameRequest {	
	// Responses
	private ResponseLogout response;

	public RequestLogout() {
		responses.add(response = new ResponseLogout());

	}

	@Override
	public void doBusiness() throws Exception {
		this.response.setUsername(this.client.getPlayer().getUsername());
		if(client.getSession() != null) {
			client.getSession().addResponseForAll(response);
			client.getSession().removeResponseForCharacters(client.getPlayer().getUsername());
			client.getSession().removeGameClient(client);
			client.getSession().endThread();
			client.setSession(null);
		} else {
			//client.getServer().addResponseForAllOnlinePlayers(client.getPlayer().getId(), response);
			client.stopClient();
		}
	}
	
	public void clientCrashed(GameClient client) {
		this.client = client;
		this.response.setUsername(this.client.getPlayer().getUsername());
		if(client.getSession() != null) {
			client.getSession().addResponseForAll(response);
			client.getSession().removeResponseForCharacters(client.getPlayer().getUsername());
			client.getSession().removeGameClient(client);
			client.getSession().endThread();
			client.setSession(null);
		}
		client.stopClient();
	}

	@Override
	public void parse() throws IOException {
		// TODO Auto-generated method stub
		
	}
}
