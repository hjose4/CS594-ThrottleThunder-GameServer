package networking.request;

import java.io.IOException;
import dataAccessLayer.record.Player;
import networking.response.ResponseSetReady;

public class RequestReady extends GameRequest {
	
	private ResponseSetReady responseReady;
	
	public RequestReady() {
		responses.add(responseReady = new ResponseSetReady());
    }
	
	@Override
	public void parse() throws IOException {
		
	}

	@Override
	public void doBusiness() throws Exception {
		if(client.getSession() != null) {
			if(!this.client.getPlayer().isReady()){
				this.client.getPlayer().setReady();
				if(allReady()){
					this.client.getSession().nextPhase();
					for(Player player : client.getSession().getPlayers()) {
						player.setNotReady();
					}
				}
			}
			responseReady.setUsername(this.client.getPlayer().getUsername());
			this.client.getSession().addResponseForAll(this.client.getPlayer().getId(), responseReady);
		} else {
			responseReady.setUsername("");
			System.out.println("Client is not in game session: "+this.getClass().getName());
		}
	}

	private boolean allReady() {
		if(client.getSession() != null) {
			for(Player player : this.client.getSession().getPlayers()){
				if(!player.isReady()){
					return false;
				}
			}
		} else {
			return false;
		}
		
		return true;
	}

}
