package networking.response;

import utility.GamePacket;
import metadata.Constants;
import dataAccessLayer.Player;

public class ResponseMove extends GameResponse {
	
	private Player player;
	private String keys;
	
	public ResponseMove() {
        responseCode = Constants.SMSG_MOVE;
    }
	
	@Override
	public byte[] constructResponseInBytes() {
		// TODO Auto-generated method stub
		GamePacket packet = new GamePacket(responseCode);
		packet.addString(player.getUsername());
		packet.addFloat(player.getPosition().getX());
		packet.addFloat(player.getPosition().getY());
		packet.addFloat(player.getPosition().getZ());
		packet.addFloat(player.getPosition().getH());
		packet.addFloat(player.getPosition().getP());
		packet.addFloat(player.getPosition().getR());
		packet.addString(this.keys);
		return packet.getBytes();
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void setKeys(String keys) {
		this.keys = keys;
	}

}
