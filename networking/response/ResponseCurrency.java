package networking.response;

import utility.GamePacket;
import metadata.Constants;

public class ResponseCurrency extends GameResponse {
	private int currency;
	
	public ResponseCurrency() {
        responseCode = Constants.SMSG_CURRENCY;
    }
	
	@Override
	public byte[] constructResponseInBytes() {
		// TODO Auto-generated method stub
		GamePacket packet = new GamePacket(responseCode);
		packet.addInt32(currency);
        return packet.getBytes();
	}

	public void setCurrency(int currency) {
		this.currency = currency;
	}

}
