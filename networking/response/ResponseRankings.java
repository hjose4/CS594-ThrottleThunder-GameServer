package networking.response;

import utility.GamePacket;

import java.util.HashMap;

import dataAccessLayer.record.Player;
import metadata.Constants;

public class ResponseRankings extends GameResponse {
	private HashMap<Player,Integer> rankings;
	
	public ResponseRankings() {
        responseCode = Constants.SMSG_RANKINGS;
    }
	
	@Override
	public byte[] constructResponseInBytes() {
		// TODO Auto-generated method stub
		GamePacket packet = new GamePacket(responseCode);
		packet.addInt32(rankings.size());
		for(Player player : rankings.keySet()) {
			packet.addString(player.getUsername());
			packet.addInt32(rankings.get(player));
		}
        return packet.getBytes();
	}
	
	public void setRankings(HashMap<Player,Integer> rankings) {
		this.rankings = rankings;
	}
}
