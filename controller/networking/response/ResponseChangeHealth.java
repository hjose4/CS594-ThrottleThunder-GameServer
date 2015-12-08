package controller.networking.response;

import driver.data.meta.Constants;
import utility.GamePacket;

public class ResponseChangeHealth extends GameResponse {

    private String username;
    private int healthChange;

	public ResponseChangeHealth() {
        responseCode = Constants.SMSG_HEALTH;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addString(username);
        packet.addInt32(healthChange);
        return packet.getBytes();
    }
    
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getUsername()
	{
		return this.username;
	}
	
    public int getHealthChange() {
		return healthChange;
	}

	public void setHealthChange(int healthChange) {
		this.healthChange = healthChange;
	}
}