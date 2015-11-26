package networking.response;

import utility.GamePacket;
import dataAccessLayer.record.Player;
import metadata.Constants;

public class ResponseMove extends GameResponse {
	
	private Player player;
	private float steering;
	private float wheelforce;
	private float brakeforce;
	
	public ResponseMove() {
        responseCode = Constants.SMSG_MOVE;
    }
	
	@Override
	public byte[] constructResponseInBytes() {
		// TODO Auto-generated method stub
		GamePacket packet = new GamePacket(responseCode);
		packet.addString(player.getUsername());
		packet.addFloat(player.getPosition().getSteering());
		packet.addFloat(player.getPosition().getWheelforce());
		packet.addFloat(player.getPosition().getBrakeforce());
		return packet.getBytes();
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public float getSteering() {
		return steering;
	}

	public void setSteering(float steering) {
		this.steering = steering;
	}

	public float getWheelforce() {
		return wheelforce;
	}

	public void setWheelforce(float wheelforce) {
		this.wheelforce = wheelforce;
	}

	public float getBrakeforce() {
		return brakeforce;
	}

	public void setBrakeforce(float brakeforce) {
		this.brakeforce = brakeforce;
	}

}
