package networking.request;

import java.io.IOException;

import dataAccessLayer.DatabaseDriver;
import dataAccessLayer.Player;
import networking.response.ResponseLogin;
import networking.response.ResponseLogout;
import utility.DataReader;

public class RequestLogout extends GameRequest {	
	// Responses
	private ResponseLogout response;

	public RequestLogout() {
		responses.add(response = new ResponseLogout());

	}

	@Override
	public void doBusiness() throws Exception {
		Player player = client.getPlayer();
		response.setUsername(player.getUsername());
		client.getServer().addResponseForAllOnlinePlayers(player.getID(), response);
	}

	@Override
	public void parse() throws IOException {
		// TODO Auto-generated method stub
		
	}
}
