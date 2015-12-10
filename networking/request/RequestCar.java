package networking.request;

import java.io.IOException;

import dataAccessLayer.model.VehicleModel;
import dataAccessLayer.record.Player;
import dataAccessLayer.record.PlayerVehicle;
import json.collections.BaseVehicleCollection;
import networking.response.ResponseCar;
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
