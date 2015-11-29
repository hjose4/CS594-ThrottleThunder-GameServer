package networking.response;

// Custom Imports
import java.util.List;
import dataAccessLayer.record.Player;
import metadata.Constants;
import utility.GamePacket;

public class ResponseEnterQueue extends GameResponse {
	private int size;
	private List<Player> players;

    public ResponseEnterQueue() {
        responseCode = Constants.SMSG_ENTER_QUEUE;
        System.out.println("created responseEnterQueue()");
    }

    @Override
    public byte[] constructResponseInBytes() {
    	GamePacket packet = new GamePacket(responseCode);
        packet.addInt32(size);

        packet.addInt32(players.size());
        for(Player player : players) {
        	packet.addString(player.getUsername());
        	packet.addBoolean(player.isReady());
        }
        return packet.getBytes();
    }
    
    public void setLobbySize(int size) {
    	this.size = size;
    }

    public void setPlayers(List<Player> players) {
    	this.players = players;
    }

}