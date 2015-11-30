package networking.request;

import java.io.IOException;
import java.util.ArrayList;

import core.GameSession;
import dataAccessLayer.record.Player;
import networking.response.ResponseRenderCharacter;
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
				response.setUsername(client.getPlayer().getUsername());
				response.setCarPaint(0);
				response.setCarTires(0);
				response.setCarType(0);
				client.getSession().addResponseForAll(client.getId(), response);
				
				System.out.println("Test Complete");
				
				for(Player player : client.getSession().getPlayers())
				{
					if(player.getId() != client.getPlayer().getId())
					{
						response.setUsername(player.getUsername());
						response.setCarPaint(0);
						response.setCarTires(0);
						response.setCarType(0);
						client.addResponseForUpdate(response);
					}
				}
			}
		}	
	}

}
