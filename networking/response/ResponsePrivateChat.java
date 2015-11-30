package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponsePrivateChat extends GameResponse {

	private String username;
    private String message;
    private int flag; 

	public ResponsePrivateChat() {
        responseCode = Constants.SMSG_PRIVATE_CHAT;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addString(username);
        packet.addString(message);
        packet.addInt32(flag);
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
		this.username= username;
	}
	
	public String getUsername()
	{
		return this.username; 
	}
	
    public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
}
