package controller.networking.response;

import driver.data.meta.Constants;
import utility.GamePacket;

public class ResponseLogout extends GameResponse {
	private String username;

    public ResponseLogout() {
        responseCode = Constants.SMSG_DISCONNECT;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);    
        packet.addString(username);
        return packet.getBytes();
    }
    
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}