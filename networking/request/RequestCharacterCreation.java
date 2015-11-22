package networking.request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataAccessLayer.DatabaseDriver;
import dataAccessLayer.ObjectModel;
import dataAccessLayer.Vehicle;
import dataAccessLayer.Player;
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
			DatabaseDriver db = DatabaseDriver.getInstance();
			Vehicle vehicle = new Vehicle(DatabaseDriver.findById(Vehicle.class, this.baseId).getData());
			
			vehicle.setName(vehicleName);
			if(player != null && vehicle != null && Integer.valueOf(DatabaseDriver.insert(vehicle)) > 0) {
				response.setFlag(1);
				List<Vehicle> playervehicles = new ArrayList<Vehicle>();
				List<ObjectModel> found = DatabaseDriver.find(Vehicle.class, "player_id", player.getID());
				for(int i = 0; i<found.size(); i++)
				{
					playervehicles.add((Vehicle)found.get(i));
				}
				response.setPlayerVehicles(playervehicles);
			} else {
				response.setFlag(0);
			}
		}
}
