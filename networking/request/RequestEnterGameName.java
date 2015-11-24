package networking.request;

import java.io.IOException;

import dataAccessLayer.record.Player;
import model.Position;
import networking.response.ResponseEnterGameName;
import networking.response.ResponseRenderCharacter;
import utility.DataReader;

/**
 * When the user attempts to join a game lobby, they send 
 * their username and lobby id to the server, and the 
 * server responds to the users already in the lobby 
 * through ResponseEnterGameLobby
 *
 */
public class RequestEnterGameName extends GameRequest {
	private String room_name;
	private ResponseEnterGameName response;
	
	public RequestEnterGameName() {
		responses.add(response = new ResponseEnterGameName());
	}
	@Override
	public void parse() throws IOException {
		room_name = DataReader.readString(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		
		client.setSession(client.getServer().getGameSessionByRoomName(room_name));
		
		if(client.getSession() != null) {
			String username = client.getPlayer().getUsername();
			response.setValid(1);
			response.setUsername(username);
			client.getSession().addResponseForAll(client.getPlayer().getId(), response);
			ResponseRenderCharacter respNewChar = new ResponseRenderCharacter();
			respNewChar.setUsername(username);
			respNewChar.setCarTires(0);
			respNewChar.setCarPaint(0);
			respNewChar.setCarType(0);
			
			client.getSession().addResponseForAll(client.getId(),respNewChar);
			for(core.GameClient otherClient : client.getSession().getGameClients()) {
				respNewChar = new ResponseRenderCharacter();
				respNewChar.setUsername(otherClient.getPlayer().getUsername());
				respNewChar.setCarTires(0);
				respNewChar.setCarPaint(0);
				respNewChar.setCarType(0);
				responses.add(respNewChar);
			}
			
		} else {

			System.out.println("Room " + room_name + " does not exists");
		}
		
		for(ResponseRenderCharacter responseRenderCharacter : client.getSession().getCharacterUpdates()){
			responses.add(responseRenderCharacter);
		}
		client.getSession().addResponseForRenderCharacters(client);	
		response.setValid(0);
		response.setUsername(client.getPlayer().getUsername());
	}

}
