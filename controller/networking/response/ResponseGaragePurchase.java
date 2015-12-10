package controller.networking.response;

import core.meta.Constants;
import utility.GamePacket;

public class ResponseGaragePurchase extends GameResponse {
	private int status;
	
	public ResponseGaragePurchase() {
        responseCode = Constants.SMSG_GARAGE_PURCHASE;
    }
	
	@Override
	public byte[] constructResponseInBytes() {
		System.out.println("Sending ResponseGaragePurchase");
        GamePacket packet = new GamePacket(responseCode);
        packet.addInt32(status);
        return packet.getBytes();
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
