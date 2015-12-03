package networking.request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import core.GameSession;
import dataAccessLayer.record.Player;
import model.Position;
import networking.response.ResponseRenderCharacter;
import networking.response.ResponseSetPosition;
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
		GameSession session = client.getSession();		
		if(session != null) {
			//Render Character - Send to others
			response.setUsername(client.getPlayer().getUsername());
			response.setCarPaint(1);
			response.setCarTires(1);
			response.setCarType(1);
			client.getSession().addResponseForAll(client.getPlayer(), response);
			
			//Set Position
			ResponseSetPosition responseSet = new ResponseSetPosition();
			HashMap<Player,Position> positions = new HashMap<>();
			
			System.out.println("Test Complete");
			
			for(Player player : client.getSession().getPlayers()) {
				if(player.getId() != client.getPlayer().getId()) {
					//Render character for players that are already in the game - send to client
					response = new ResponseRenderCharacter();
					response.setUsername(player.getUsername());
					response.setCarPaint(1);
					response.setCarTires(1);
					response.setCarType(1);
					client.addResponseForUpdate(response);
				}
				
				positions.put(player, player.getPosition());
			}
			
			responseSet.setStartingPositions(positions);
			//Send set position
			client.getSession().addResponseForAll(responseSet);
		}	
	}

}