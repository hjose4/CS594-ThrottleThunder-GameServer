package controller.networking.response;

import core.meta.Constants;
import utility.GamePacket;

public class ResponseReady extends GameResponse {
	String username;
	
	public ResponseReady() {
        responseCode = Constants.SMSG_READY;
    }
	
	@Override
	public byte[] constructResponseInBytes() {
		// TODO Auto-generated method stub
		GamePacket packet = new GamePacket(responseCode);
		packet.addString(username);
        return packet.getBytes();
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
