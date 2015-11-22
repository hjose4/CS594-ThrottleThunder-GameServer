package networking.response;

import utility.GamePacket;
import metadata.Constants;

public class ResponseTime extends GameResponse {
	private int type;
	private long time;
	
	public ResponseTime() {
        responseCode = Constants.SMSG_TIME;
    }
	
	@Override
	public byte[] constructResponseInBytes() {
		// TODO Auto-generated method stub
		GamePacket packet = new GamePacket(responseCode);
		packet.addInt32(type);
		packet.addLong(time);
		return packet.getBytes();
	}

	public void setData(int type, long time){
		this.type = type;
		this.time = time;
	}
}
