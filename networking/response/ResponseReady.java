package networking.response;

import utility.GamePacket;
import metadata.Constants;

public class ResponseReady extends GameResponse {
	
	public ResponseReady() {
        responseCode = Constants.SMSG_READY;
    }
	
	@Override
	public byte[] constructResponseInBytes() {
		// TODO Auto-generated method stub
		GamePacket packet = new GamePacket(responseCode);
		// add the data need to pass to the client here
        return packet.getBytes();
	}
	
}
