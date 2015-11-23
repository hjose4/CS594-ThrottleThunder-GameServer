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
		// TODO Auto-generated method stub
		GamePacket packet = new GamePacket(responseCode);
		// add the data need to pass to the client here
        return packet.getBytes();
	}

	public void setPrize(int currencyGained) {
		this.prize = currencyGained;
	}

}
