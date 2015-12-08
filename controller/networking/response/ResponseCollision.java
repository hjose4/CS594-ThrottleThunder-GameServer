package networking.response;

import utility.GamePacket;
import metadata.Constants;

public class ResponseCollision extends GameResponse {
	private String username;
	private int damage;

	public ResponseCollision() {
        responseCode = Constants.SMSG_COLLISION;
    }
	
	@Override
	public byte[] constructResponseInBytes() {
		// TODO Auto-generated method stub
		GamePacket packet = new GamePacket(responseCode);
		packet.addString(username);
		packet.addInt32(damage);
		// add the data need to pass to the client here
        return packet.getBytes();
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

}
