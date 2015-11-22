package networking.response;

import metadata.Constants;
import utility.GamePacket;

public class ResponseDisconnected extends GameResponse {
	private String username;

    public ResponseDisconnected() {
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
