package controller.networking.response;

import driver.data.meta.Constants;
import utility.GamePacket;

public class ResponsePowerUp extends GameResponse {
	private String username;
	private int powerId;
	
    public ResponsePowerUp() {
        responseCode = Constants.SMSG_POWER_UP;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addString(username);
        packet.addInt32(powerId);
        return packet.getBytes();
    }

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPowerId(int powerId) {
		this.powerId = powerId;
	}
    
}