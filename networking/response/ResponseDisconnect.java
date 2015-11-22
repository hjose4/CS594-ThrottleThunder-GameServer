package networking.response;

import metadata.Constants;
import utility.GamePacket;

public class ResponseDisconnect extends GameResponse {

	private String username;
	
    public ResponseDisconnect() {
        responseCode = Constants.SMSG_DISCONNECT;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);    
        /*Packet construction*/
        packet.addString(username);
        return packet.getBytes();
    }

	public void setUsername(String username) {
		// TODO Auto-generated method stub
		this.username=username;
	}
   
}
