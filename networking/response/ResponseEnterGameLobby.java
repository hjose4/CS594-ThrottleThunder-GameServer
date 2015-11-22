package networking.response;

import metadata.Constants;
import utility.GamePacket;

public class ResponseEnterGameLobby extends GameResponse {
	private String username;
	private int valid;
	public ResponseEnterGameLobby() {
		responseCode = Constants.SMSG_ENTER_GAME_LOBBY;
	}

	@Override
	public byte[] constructResponseInBytes() {
		GamePacket packet = new GamePacket(responseCode);
		packet.addString(username);
		packet.addInt32(valid);
		return packet.getBytes();
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	public int getValid() {
		return this.valid;
	}
}
