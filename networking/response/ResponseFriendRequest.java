package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponseFriendRequest extends GameResponse {

    private String usernameFrom;
    public ResponseFriendRequest() {
        responseCode = Constants.SMSG_FRIEND_UPDATE;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addString(usernameFrom);
        return packet.getBytes();
    }

	public String getUsernameFrom() {
		return usernameFrom;
	}


	public void setUsernameFrom(String usernameFrom) {
		this.usernameFrom = usernameFrom;
	}
}