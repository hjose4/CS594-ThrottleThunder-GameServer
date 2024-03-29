package controller.networking.response;

import utility.GamePacket;
import java.util.List;

import core.meta.Constants;
import driver.database.record.Player;

public class ResponseRankings extends GameResponse {
	private List<Player> rankings;
	
	public ResponseRankings() {
        responseCode = Constants.SMSG_RANKINGS;
    }
	
	@Override
	public byte[] constructResponseInBytes() {
		// TODO Auto-generated method stub
		GamePacket packet = new GamePacket(responseCode);
		if(rankings != null) {
			packet.addInt32(rankings.size());
			for(int i = 0; i < rankings.size(); i++) {
				packet.addString(rankings.get(i).getUsername());
				packet.addInt32((i+1));
			}
		} else {
			packet.addInt32(0);
		}
        return packet.getBytes();
	}
	
	public void setRankings(List<Player> rankings) {
		this.rankings = rankings;
	}
}
