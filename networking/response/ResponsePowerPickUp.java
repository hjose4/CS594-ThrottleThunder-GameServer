package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponsePowerPickUp extends GameResponse {
	private String username;
	private int powerId;
	
    public ResponsePowerPickUp() {
        responseCode = Constants.SMSG_POWER_PICKUP;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addString(username);
     	packet.addInt32(powerId);
        return packet.getBytes();
    }

	public void setUsername(String username) {
		this.username = username;
	}
    
	public void setPowerId(int powerId){
		this.powerId = powerId;
	}
}