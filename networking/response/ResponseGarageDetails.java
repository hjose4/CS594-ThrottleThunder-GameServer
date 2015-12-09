package networking.response;

import metadata.Constants;
import utility.GamePacket;

public class ResponseGarageDetails extends GameResponse {
	private int armor;
	private int health;
	private int acceleration;
	private int speed;

	public ResponseGarageDetails() {
        responseCode = Constants.SMSG_GARAGE_DETAILS;
    }
	
	@Override
	public byte[] constructResponseInBytes() {
		System.out.println("Sending ResponseGarageDetails");
        GamePacket packet = new GamePacket(responseCode);
        packet.addInt32(armor);
        packet.addInt32(health);
        packet.addInt32(acceleration);
        packet.addInt32(speed);
        return packet.getBytes();
	}
	
	public void setArmor(int armor) {
		this.armor = armor;
	}



	public void setHealth(int health) {
		this.health = health;
	}
	

	public void setAcceleration(int acceleration) {
		this.acceleration = acceleration;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
