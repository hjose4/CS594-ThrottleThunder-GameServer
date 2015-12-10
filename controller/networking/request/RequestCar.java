package controller.networking.request;

import java.io.IOException;
import java.util.List;

import controller.networking.response.ResponseCar;
import driver.database.model.VehicleModel;
import driver.database.record.Player;
import driver.database.record.PlayerVehicle;
import driver.json.collection.BaseVehicleCollection;
import driver.json.record.BaseVehicle;
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
			System.out.println("Car ID: " + carId + ", paintId: " + paintId + ", tiresId " + tiresId) ;
			carId += 1;
			Player player =client.getPlayer();
			BaseVehicle base = BaseVehicleCollection.getVehicle(carId);
			List<PlayerVehicle> playerVehicles = VehicleModel.searchForPlayerVehicles(player, base);
			if(!playerVehicles.isEmpty()) {
				PlayerVehicle vehicle = playerVehicles.get(0);
				vehicle.setTire(tiresId);
				vehicle.setPaint(paintId);
				vehicle.save("all");
				response.setStatus(1);
			} else
				response.setStatus(0);
		}
}
