package controller.networking.request;

import java.io.IOException;

import driver.database.DatabaseDriver;
import driver.database.model.PlayerModel;
import driver.database.model.VehicleModel;
import driver.database.record.Player;
import driver.database.record.PlayerVehicle;
import utility.DataReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import controller.networking.response.ResponseFriendList;
import controller.networking.response.ResponseGarageDetails;
import controller.networking.response.ResponseGaragePurchase;

public class RequestGarageDetails extends GameRequest {
	private int carId;
	private int type;
	
	private ResponseGarageDetails response;
	
	public RequestGarageDetails() {
		responses.add(response = new ResponseGarageDetails());
	}

	@Override
	public void parse() throws IOException {
		// TODO Auto-generated method stub
		carId = DataReader.readInt(dataInput);
		type = DataReader.readInt(dataInput); //Do we need this
	}

	@Override
	public void doBusiness() throws Exception {
		PlayerVehicle vehicle = VehicleModel.getPlayerVehicleById(carId);
		if(vehicle!=null){
			response.setArmor(vehicle.getHealthUpgrade());
			response.setHealth(vehicle.getArmorUpgrade());
			response.setAcceleration(vehicle.getAccelerationUpgrade());
			response.setSpeed(vehicle.getSpeedUpgrade());
			
		}
	}

}
