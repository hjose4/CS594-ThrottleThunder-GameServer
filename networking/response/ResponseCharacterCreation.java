package networking.response;

import java.util.List;

import dataAccessLayer.BaseVehicle;
import dataAccessLayer.PlayerVehicle;
import metadata.Constants;
import utility.GamePacket;

public class ResponseCharacterCreation extends GameResponse {
	private int flag;
	List<PlayerVehicle> playerVehicles;
	
    public ResponseCharacterCreation() {
        responseCode = Constants.SMSG_CREATE_CHARACTER;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addInt32(flag);
        packet.addInt32(playerVehicles.size());
        for(PlayerVehicle vehicle : playerVehicles) {
        	packet.addString(vehicle.getName());
        	packet.addInt32(vehicle.getId());
        	packet.addInt32(vehicle.getBaseVehicleId());
        }
        
        return packet.getBytes();
    }

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public List<PlayerVehicle> getPlayerVehicles() {
		return playerVehicles;
	}

	public void setPlayerVehicles(List<PlayerVehicle> playerVehicles) {
		this.playerVehicles = playerVehicles;
	}
    
    
    
    
}
