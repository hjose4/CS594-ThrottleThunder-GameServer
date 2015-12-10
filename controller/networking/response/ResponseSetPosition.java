package controller.networking.response;

import java.util.HashMap;

import core.meta.Constants;
import driver.data.model.Position;
import driver.database.record.Player;
import utility.GamePacket;

public class ResponseSetPosition extends GameResponse {
	private HashMap<Player,Position> startingPositions;
    public ResponseSetPosition() {
        responseCode = Constants.SMSG_SET_POSITION;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        System.out.println("Sending Protocol: " + responseCode + " class: " + this.getClass().getName());
        //Gets all users from session and returns their usernames and positions
        packet.addInt32(startingPositions.size());
        for(Player player : startingPositions.keySet()) {
        	Position position = startingPositions.get(player);
        	packet.addString(player.getUsername());
        	packet.addFloat(position.getX());
    		packet.addFloat(position.getY());
    		packet.addFloat(position.getZ());
    		packet.addFloat(position.getH());
    		packet.addFloat(position.getP());
    		packet.addFloat(position.getR());        	
        }
        return packet.getBytes();
    }
    
    public void setStartingPositions(HashMap<Player,Position> positions) {
    	this.startingPositions = positions;
    }
}
