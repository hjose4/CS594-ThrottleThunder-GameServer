package networking.response;

import java.util.HashMap;

import dataAccessLayer.record.Player;
// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponseResults extends GameResponse {
	private HashMap<Player,Integer> playerRankings;
    public ResponseResults() {
        responseCode = Constants.SMSG_RESULTS;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addInt32(playerRankings.size());
      /*Construct packet*/
        for(Player player : playerRankings.keySet()) {
			packet.addString(player.getUsername());
			packet.addInt32(playerRankings.get(player));
		}
        return packet.getBytes();
    }
    
    public void setRankings(HashMap<Player,Integer> rankings) {
		this.playerRankings = rankings;
	}
}