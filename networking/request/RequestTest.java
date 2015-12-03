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
public class RequestTest extends GameRequest {
	private ResponseRenderCharacter response;
	
	public RequestTest() {
		response = new ResponseRenderCharacter();
	}
	@Override
	public void parse() throws IOException {
		
	}

	@Override
	public void doBusiness() throws Exception {
		int roomType = 0;
		GameSession session = client.getServer().getGameSessionByRoomType(roomType);		
		if(session != null) {
			client.setSession(session);		
			if(client.getSession() != null) {
				//Render Character
				response.setUsername(client.getPlayer().getUsername());
				response.setCarPaint(0);
				response.setCarTires(0);
				response.setCarType(0);
				client.getSession().addResponseForAll(client, response);
				
				//Set Position
				ResponseSetPosition responseSet = new ResponseSetPosition();
				HashMap<Player,Position> positions = new HashMap<>();
				
				System.out.println("Test Complete");
				
				for(Player player : client.getSession().getPlayers())
				{
					if(player.getId() != client.getPlayer().getId())
					{
						//Render character for players that are already in the game
						response.setUsername(player.getUsername());
						response.setCarPaint(0);
						response.setCarTires(0);
						response.setCarType(0);
						client.addResponseForUpdate(response);
					}
					
					positions.put(player, player.getPosition());
				}
				
				responseSet.setStartingPositions(positions);
				client.getSession().addResponseForAll(responseSet);
			}
		}	
	}

}
