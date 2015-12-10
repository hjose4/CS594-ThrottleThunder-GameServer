package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponseCar extends GameResponse {

    private int status; 

	public ResponseCar() {
        responseCode = Constants.SMSG_CAR;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addInt32(status);
        return packet.getBytes();
    }
    
    public void setStatus(int status) {
		this.status = status;
	}
}
