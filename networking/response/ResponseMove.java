package networking.response;

import utility.GamePacket;
import dataAccessLayer.record.Player;
import metadata.Constants;

public class ResponseMove extends GameResponse {
	
	private Player player;
	private int forward;
	private int backward;
	private int right;
	private int left;
	
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
		packet.addInt32(this.forward);
		packet.addInt32(this.backward);
		packet.addInt32(this.right);
		packet.addInt32(this.left);
		return packet.getBytes();
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public int getForward() {
		return forward;
	}

	public void setForward(int forward) {
		this.forward = forward;
	}

	public int getBackward() {
		return backward;
	}

	public void setBackward(int backward) {
		this.backward = backward;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}


}
