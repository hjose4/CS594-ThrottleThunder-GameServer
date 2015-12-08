package networking.response;

import utility.GamePacket;
import metadata.Constants;

public class ResponseTime extends GameResponse {
	private int type;
	private int time;
	
	public ResponseTime() {
        responseCode = Constants.SMSG_TIME;
    }
	
	@Override
	public byte[] constructResponseInBytes() {
		// TODO Auto-generated method stub
		GamePacket packet = new GamePacket(responseCode);
		packet.addInt32(type);
		packet.addInt32(time);
		return packet.getBytes();
	}

	public void setData(int type, int time){
		this.type = type;
		this.time = time;
	}
}
