package controller.networking.response;

import driver.data.meta.Constants;
import utility.GamePacket;

public class ResponseInvite extends GameResponse {
	private String username;
	
	public ResponseInvite() {
        responseCode = Constants.SMSG_INVITE;
    }
	
	@Override
	public byte[] constructResponseInBytes() {
		System.out.println("Sending Response Invite");
        GamePacket packet = new GamePacket(responseCode);
        packet.addString(username);
        return packet.getBytes();
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
