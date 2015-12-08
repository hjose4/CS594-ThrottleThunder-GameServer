package controller.networking.request;

import java.io.IOException;

import driver.database.model.VehicleModel;
import driver.database.record.PlayerVehicle;
import utility.DataReader;

public class RequestGaragePurchase extends GameRequest {
	private int carId;
	private int typeId;
	private int type;

	@Override
	public void parse() throws IOException {
		// TODO Auto-generated method stub
		carId = DataReader.readInt(dataInput);
		type = DataReader.readInt(dataInput);
		typeId = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		PlayerVehicle vehicle = VehicleModel.getPlayerVehicleById(carId);
		if(vehicle != null) {
			
		}

	}

}
