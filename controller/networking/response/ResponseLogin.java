package controller.networking.response;

import core.meta.Constants;
import utility.GamePacket;

public class ResponseLogin extends GameResponse {

    private int answer;

    public ResponseLogin() {
        responseCode = Constants.SMSG_LOGIN;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addInt32(answer);
        return packet.getBytes();
    }
    
	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}
}
