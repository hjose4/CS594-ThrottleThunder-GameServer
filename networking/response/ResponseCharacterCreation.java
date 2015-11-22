package networking.response;

import java.util.List;

import dataAccessLayer.Vehicle;
import metadata.Constants;
import utility.GamePacket;

public class ResponseCharacterCreation extends GameResponse {
	private int flag;
	List<Vehicle> playerVehicles;
	
    public ResponseCharacterCreation() {
        responseCode = Constants.SMSG_CREATE_CHARACTER;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addInt32(flag);
        packet.addInt32(playerVehicles.size());
        for(Vehicle vehicle : playerVehicles) {
        	packet.addString(vehicle.getName());
        	packet.addInt32(vehicle.getId());
        //	packet.addInt32(vehicle.getBaseVehicleId());
        }
        
        return packet.getBytes();
    }

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public List<Vehicle> getPlayerVehicles() {
		return playerVehicles;
	}

	public void setPlayerVehicles(List<Vehicle> playerVehicles) {
		this.playerVehicles = playerVehicles;
	}
    
    
    
    
}
