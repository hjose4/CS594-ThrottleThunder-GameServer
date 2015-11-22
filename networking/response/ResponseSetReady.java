package networking.response;

import utility.GamePacket;
import metadata.Constants;

public class ResponseSetReady extends GameResponse {
	
	public ResponseSetReady() {
        responseCode = Constants.SMSG_SET_READY;
    }
	
	@Override
	public byte[] constructResponseInBytes() {
		// TODO Auto-generated method stub
		GamePacket packet = new GamePacket(responseCode);
        return packet.getBytes();
	}
}
