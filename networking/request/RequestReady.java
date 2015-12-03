package networking.request;

import java.io.IOException;
import dataAccessLayer.record.Player;
import networking.response.ResponseReady;

public class RequestReady extends GameRequest {
	
	private ResponseReady responseReady;
	
	public RequestReady() {
		responses.add(responseReady = new ResponseReady());
    }
	
	@Override
	public void parse() throws IOException {
		
	}

	@Override
	public void doBusiness() throws Exception {
		responseReady.setUsername(this.client.getPlayer().getUsername());
		if(client.getSession() != null) {
			if(!this.client.getPlayer().isReady()){
				this.client.getPlayer().setReady();
				//We need more than one player before we can start
				if(allReady() && client.getSession().getGameClients().size() > 1){
					this.client.getSession().nextPhase();
					for(Player player : client.getSession().getPlayers()) {
						player.setNotReady();
					}
				}
				
				this.client.getSession().addResponseForAll(this.client.getPlayer().getId(), responseReady);
			} else {
				for(Player player : client.getSession().getPlayers()) {
					player.setNotReady();
				}
				this.client.getPlayer().setReady();
			}
		} else {
			responseReady.setUsername("");
			System.out.println("Client is not in game session: "+this.getClass().getName());
		}
	}

	private boolean allReady() {
		for(Player player : this.client.getSession().getPlayers()){
			if(!player.isReady()){
				return false;
			}
		}
		
		return true;
	}

}
