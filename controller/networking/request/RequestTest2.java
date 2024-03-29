package controller.networking.request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import controller.networking.response.ResponseRenderCharacter;
import controller.networking.response.ResponseSetPosition;
import core.GameSession;
import driver.data.model.Position;
import driver.database.record.Player;
import utility.DataReader;

/**
 * 
 * When the user attempts to join a lobby, they send their username 
 * and room id to the server, and the server responds to the users 
 * already in the lobby through ResponseEnterLobby
 *
 */
public class RequestTest2 extends GameRequest {
	private ResponseRenderCharacter response;
	
	public RequestTest2() {
		response = new ResponseRenderCharacter();
	}
	@Override
	public void parse() throws IOException {
		
	}

	@Override
	public void doBusiness() throws Exception {
		int roomType = 0;
		if(client.getSession() != null) {
				//Render Character
				client.getSession().addResponseForRenderCharacters(client);
				client.getPlayer().setReady();
				if(client.getSession().getGameClients().size() == 2 && allReady()){
					client.getSession().nextPhase();
					client.getSession().nextPhase();
				}
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
