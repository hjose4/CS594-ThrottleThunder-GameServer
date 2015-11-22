package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponseLogin extends GameResponse {

    private short answer;

    public ResponseLogin() {
        responseCode = Constants.SMSG_LOGIN;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addShort16(answer);
        return packet.getBytes();
    }
    
	public short getAnswer() {
		return answer;
	}

	public void setAnswer(short answer) {
		this.answer = answer;
	}
}
