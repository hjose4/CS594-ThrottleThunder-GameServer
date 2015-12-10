package controller.networking.response;

import core.meta.Constants;
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
        packet.addInt32(0);
        return packet.getBytes();
    }

	public String getUsernameFrom() {
		return usernameFrom;
	}


	public void setUsernameFrom(String usernameFrom) {
		this.usernameFrom = usernameFrom;
	}
}