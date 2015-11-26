package networking.response;

import utility.GamePacket;
import dataAccessLayer.record.Player;
import metadata.Constants;

public class ResponseMove extends GameResponse {
	
	private Player player;
	private float steering;
	private float wheelforce;
	private float brakeforce;
	private float x, y, z, h, p, r;
	
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
		packet.addFloat(player.getPosition().getX());
		packet.addFloat(player.getPosition().getY());
		packet.addFloat(player.getPosition().getZ());
		packet.addFloat(player.getPosition().getH());
		packet.addFloat(player.getPosition().getP());
		packet.addFloat(player.getPosition().getR());
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

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}

	public float getP() {
		return p;
	}

	public void setP(float p) {
		this.p = p;
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public Player getPlayer() {
		return player;
	}

}
