package controller.networking.request;

import java.io.IOException;

import controller.networking.response.ResponseCharacterCreation;
import driver.database.model.VehicleModel;
import driver.database.record.Player;
import driver.database.record.PlayerVehicle;
import driver.json.collection.BaseVehicleCollection;
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
			PlayerVehicle vehicle = VehicleModel.createPlayerVehicleFromBaseVehicle(BaseVehicleCollection.getVehicle(baseId), client.getPlayer());
			
			if(player != null && vehicle != null) {
				vehicle.setName(vehicleName);
				if(vehicle.save("all")) {					
					response.setFlag(1);
					response.setPlayerVehicles(VehicleModel.searchForPlayerVehiclesByPlayerId(player.getId()));					
				}  else {
					response.setFlag(0);
					response.setPlayerVehicles(VehicleModel.searchForPlayerVehiclesByPlayerId(player.getId()));
				}
			}  else {
				response.setFlag(0);
				response.setPlayerVehicles(VehicleModel.searchForPlayerVehiclesByPlayerId(player.getId()));
			}
			
		}
}
