package networking.response;

import utility.GamePacket;
import metadata.Constants;

public class ResponseEmote extends GameResponse {
	
	private String username;
	private int emote;

	public ResponseEmote() {
        responseCode = Constants.SMSG_EMOTE;
    }
	
	@Override
	public byte[] constructResponseInBytes() {
		// TODO Auto-generated method stub
		GamePacket packet = new GamePacket(responseCode);
		packet.addString(this.username);
		packet.addInt32(this.emote);
		return packet.getBytes();
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getEmote() {
		return emote;
	}

	public void setEmote(int emote) {
		this.emote = emote;
	}
}
