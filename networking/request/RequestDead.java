package networking.request;

import java.io.IOException;
import networking.response.ResponseDead;

public class RequestDead extends GameRequest {
	
	private ResponseDead responseDead;
	
	public RequestDead() {
        responses.add(responseDead = new ResponseDead());
    }
	
	@Override
	public void parse() throws IOException {
		//parse the datainput here
		//x = DataReader.readFloat(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		//do the rankings business here
		
		client.getSession().clientDead(client.getPlayer());
		responseDead.setUsername(client.getPlayer().getUsername());
		client.getSession().addResponseForAll(client.getPlayer().getId(), responseDead);
	}

}
