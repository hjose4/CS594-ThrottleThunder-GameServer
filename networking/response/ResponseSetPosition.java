package networking.response;

// Custom Imports
import metadata.Constants;
import utility.GamePacket;

public class ResponseSetPosition extends GameResponse {

    public ResponseSetPosition() {
        responseCode = Constants.SMSG_SET_POSITION;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        
        //Gets all users from session and returns their usernames and positions
        
        return packet.getBytes();
    }
}
