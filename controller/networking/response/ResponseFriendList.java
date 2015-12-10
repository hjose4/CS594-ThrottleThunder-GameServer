package controller.networking.response;

import java.util.List;

import core.meta.Constants;
import driver.database.record.Player;
import utility.GamePacket;

public class ResponseFriendList extends GameResponse {
	List<Player> friends;
	public ResponseFriendList(){
		responseCode = Constants.SMSG_FRIEND_LIST;
	}

	@Override
	public byte[] constructResponseInBytes() {
		GamePacket packet = new GamePacket(responseCode);

		packet.addInt32(friends.size());
		for (Player p : friends) {
			packet.addString(p.getUsername());
		}
		return packet.getBytes();
	}

	public void setFriends(List<Player> friends) {
		this.friends = friends;
	}

}