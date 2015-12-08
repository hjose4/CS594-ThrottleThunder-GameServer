package networking.response;

import utility.GamePacket;
import core.GameClient;
import metadata.Constants;

public class ResponsePrizes extends GameResponse {
	private int prize;
	public ResponsePrizes() {
        responseCode = Constants.SMSG_PRIZES;
    }
	
	@Override
	public byte[] constructResponseInBytes() {
		GamePacket packet = new GamePacket(responseCode);
		packet.addInt32(prize);
        return packet.getBytes();
	}

	public void setPrize(int currencyGained) {
		this.prize = currencyGained;
	}

}
