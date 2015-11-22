package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponseChat extends GameResponse {

    private String message;
    private String username; 

    public ResponseChat() {
        responseCode = Constants.SMSG_CHAT;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addString(username);
        packet.addString(message);
        return packet.getBytes();
    }
    
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getUsername()
	{
		return this.username;
	}
}
