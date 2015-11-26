package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponseFriendRequest extends GameResponse {

    private String usernameFrom;
    
    private int status;

    public ResponseFriendRequest() {
        responseCode = Constants.SMSG_FRIEND_UPDATE;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addString(usernameFrom);
        packet.addInt32(status);
        return packet.getBytes();
    }

	public String getUsernameFrom() {
		return usernameFrom;
	}

	public int getStatus(){return status;}


	public void setUsernameFrom(String usernameFrom) {
		this.usernameFrom = usernameFrom;
	}
    
	public void setStatus(int status){
		this.status = status; 
	}
    
}