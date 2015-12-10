package controller.networking.request;

import java.io.IOException;

import controller.networking.response.ResponseCar;
import driver.database.model.VehicleModel;
import driver.database.record.Player;
import driver.database.record.PlayerVehicle;
import driver.json.collection.BaseVehicleCollection;
import utility.DataReader;

public class RequestCar extends GameRequest {
	// Data
		private int carId;
		private int paintId;
		private int tiresId;
		// Responses
		private ResponseCar response;

		public RequestCar() {
			responses.add(response = new ResponseCar());

		}

		@Override
		public void parse() throws IOException {
			carId = DataReader.readInt(dataInput);
			paintId = DataReader.readInt(dataInput);
			tiresId = DataReader.readInt(dataInput);
		}

		@Override
		public void doBusiness() throws Exception {
			
		}
}
