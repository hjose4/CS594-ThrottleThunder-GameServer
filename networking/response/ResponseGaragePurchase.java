package networking.response;

import metadata.Constants;
import utility.GamePacket;

public class ResponseGaragePurchase extends GameResponse {
	private String username;
	private int status;
	
	public ResponseGaragePurchase() {
        responseCode = Constants.SMSG_GARAGE_PURCHASE;
    }
	
	@Override
	public byte[] constructResponseInBytes() {
		System.out.println("Sending ResponseGaragePurchase");
        GamePacket packet = new GamePacket(responseCode);
        packet.addString(username);
        return packet.getBytes();
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
