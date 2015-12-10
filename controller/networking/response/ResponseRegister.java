package controller.networking.response;

import core.meta.Constants;
import utility.GamePacket;

public class ResponseRegister extends GameResponse {

    private int number;

    public ResponseRegister() {
        responseCode = Constants.SMSG_REGISTER;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addInt32(number);
        
        return packet.getBytes();
    }
    
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}