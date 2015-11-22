package networking.response;

import utility.GamePacket;
import metadata.Constants;

public class ResponseSetReady extends GameResponse {
	private String username;
	
	public ResponseSetReady() {
        responseCode = Constants.SMSG_SET_READY;
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
