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
				System.out.println("Player " + client.getPlayer().getUsername() + " is ready");
				this.client.getPlayer().setReady();
			}
			
			//We need everyone ready and min num of players connected
			if(allReady() && client.getSession().getGameClients().size() >= client.getSession().getMinNumOfPlayers()){
				System.out.println("Okay looks like we can move on");
				this.client.getSession().nextPhase();
				for(Player player : client.getSession().getPlayers()) {
					player.setNotReady();
				}
			}
			
			this.client.getSession().addResponseForAll(this.client.getPlayer().getId(), responseReady);
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
