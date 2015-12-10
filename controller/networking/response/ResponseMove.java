package controller.networking.response;

import utility.GamePacket;
import core.meta.Constants;
import driver.database.record.Player;

public class ResponseMove extends GameResponse {
	
	private Player player;
	
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

	public Player getPlayer() {
		return player;
	}

}
