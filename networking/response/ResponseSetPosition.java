package networking.response;

import java.util.HashMap;

// Custom Imports
import metadata.Constants;
import model.Player;
import model.Position;
import utility.GamePacket;

public class ResponseSetPosition extends GameResponse {
	private HashMap<Player,Position> startingPositions;
    public ResponseSetPosition() {
        responseCode = Constants.SMSG_SET_POSITION;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        
        //Gets all users from session and returns their usernames and positions
        packet.addInt32(startingPositions.size());
        for(Player player : startingPositions.keySet()) {
        	packet.addString(player.getUsername());
        	packet.addFloat(player.getPosition().getX());
    		packet.addFloat(player.getPosition().getY());
    		packet.addFloat(player.getPosition().getZ());
    		packet.addFloat(player.getPosition().getH());
    		packet.addFloat(player.getPosition().getP());
    		packet.addFloat(player.getPosition().getR());        	
        }
        return packet.getBytes();
    }
    
    public void setStartingPositions(HashMap<Player,Position> positions) {
    	this.startingPositions = positions;
    }
}
