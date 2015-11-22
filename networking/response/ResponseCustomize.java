package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponseCustomize extends GameResponse {

    private String message;

    public ResponseCustomize() {
        responseCode = Constants.SMSG_CUSTOMIZE;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addString(message);// response can be 0,1,2
        return packet.getBytes();
    }
    
	

	public void setMessage(String message) {
		this.message = message;
	}
}