package networking.response;

import metadata.Constants;
import utility.GamePacket;

public class ResponseInvite extends GameResponse {
	private int roomId;
	
	public ResponseInvite() {
        responseCode = Constants.SMSG_INVITE;
    }
	
	@Override
	public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addInt32(roomId);
        return packet.getBytes();
	}
	
	public void setRoomId(int roomId){
		this.roomId = roomId;
	}
}
