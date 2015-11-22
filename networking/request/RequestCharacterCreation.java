package networking.request;

import java.io.IOException;
import dataAccessLayer.VehicleModel;
import dataAccessLayer.Player;
import dataAccessLayer.PlayerVehicle;
import networking.response.ResponseCharacterCreation;
import utility.DataReader;

public class RequestCharacterCreation extends GameRequest {
	// Data
		private String vehicleName;
		private int baseId;
		// Responses
		private ResponseCharacterCreation response;

		public RequestCharacterCreation() {
			responses.add(response = new ResponseCharacterCreation());

		}

		@Override
		public void parse() throws IOException {
			vehicleName = DataReader.readString(dataInput);
			baseId = DataReader.readInt(dataInput);
		}

		@Override
		public void doBusiness() throws Exception {
			Player player = client.getPlayer();
			PlayerVehicle vehicle = VehicleModel.createPlayerVehicleFromBaseVehicle(baseId);
			
			if(player != null && vehicle != null) {
				vehicle.setName(vehicleName);
				if(VehicleModel.insertVehicle(vehicle)) {					
					response.setFlag(1);
					response.setPlayerVehicles(VehicleModel.searchForPlayerVehiclesByPlayerId(player.getID()));					
				}  else {
					response.setFlag(0);
				}
			}  else {
				response.setFlag(0);
			}
			
		}
}
