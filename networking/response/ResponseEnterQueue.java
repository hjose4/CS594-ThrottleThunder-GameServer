package networking.response;

// Custom Imports
import java.util.List;
import dataAccessLayer.record.Player;
import metadata.Constants;
import utility.GamePacket;

public class ResponseEnterQueue extends GameResponse {
	private int size;
	private int minSize;
	private List<Player> players;

    public ResponseEnterQueue() {
        responseCode = Constants.SMSG_ENTER_QUEUE;
    }

    @Override
    public byte[] constructResponseInBytes() {
    	GamePacket packet = new GamePacket(responseCode);
        packet.addInt32(size);
        packet.addInt32(minSize);
        packet.addInt32(players.size());
        for(Player player : players) {
        	packet.addString(player.getUsername());
        	//TODO: send car id
        	packet.addInt32(player.isLobbyReady() ? 1 : 0);
        }
        return packet.getBytes();
    }
    
    public void setLobbySize(int size) {
    	this.size = size;
    }

    public void setPlayers(List<Player> players) {
    	this.players = players;
    }

	public void setMinSize(int minNumOfPlayers) {
		this.minSize = minNumOfPlayers;
	}

}