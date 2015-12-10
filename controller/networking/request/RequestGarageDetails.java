package controller.networking.request;

import java.io.IOException;

import driver.database.DatabaseDriver;
import driver.database.model.PlayerModel;
import driver.database.model.VehicleModel;
import driver.database.record.Player;
import driver.database.record.PlayerVehicle;
import driver.json.collection.BaseVehicleCollection;
import driver.json.record.BaseVehicle;
import utility.DataReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
		System.out.println("Details Car ID: " + carId + ", type: " + type);
		carId += 1;
		Player player =client.getPlayer();
		BaseVehicle base = BaseVehicleCollection.getVehicle(carId);
		List<PlayerVehicle> playerVehicles = VehicleModel.searchForPlayerVehicles(player, base);
		if(!playerVehicles.isEmpty()) {
			PlayerVehicle vehicle = playerVehicles.get(0);
			response.setArmor(vehicle.getHealthUpgrade());
			response.setHealth(vehicle.getArmorUpgrade());
			response.setHandling(vehicle.getHandlingUpgrade());
			response.setSpeed(vehicle.getSpeedUpgrade());			
		} else {
			response.setArmor(0);
			response.setHealth(0);
			response.setHandling(0);
			response.setSpeed(0);	
		}
	}

}
