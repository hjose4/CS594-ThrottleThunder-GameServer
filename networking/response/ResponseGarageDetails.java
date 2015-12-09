package networking.response;

import metadata.Constants;
import utility.GamePacket;

public class ResponseGarageDetails extends GameResponse {
	private int armor;
	private int health;
	private int acceleartion;
	
	public ResponseGarageDetails() {
        responseCode = Constants.SMSG_GARAGE_DETAILS;
    }
	
	@Override
	public byte[] constructResponseInBytes() {
		System.out.println("Sending ResponseGaragePurchase");
        GamePacket packet = new GamePacket(responseCode);
//        packet.addString(username);
        return packet.getBytes();
	}
	
	public void setArmor(int armor) {
		this.armor = armor;
	}



	public void setHealth(int health) {
		this.health = health;
	}
	

	public void setAcceleration(int acceleration) {
		this.acceleartion = acceleartion;
	}
}
