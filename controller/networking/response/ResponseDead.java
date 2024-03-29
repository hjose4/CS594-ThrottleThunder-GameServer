package controller.networking.response;

import core.meta.Constants;
import utility.GamePacket;

public class ResponseDead extends GameResponse {
	private String username;
	
	public ResponseDead() {
        responseCode = Constants.SMSG_DEAD;
    }
	
	@Override
	public byte[] constructResponseInBytes() {
		// TODO Auto-generated method stub
		GamePacket packet = new GamePacket(responseCode);
		packet.addString(username);
		// add the data need to pass to the client here
        return packet.getBytes();
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
