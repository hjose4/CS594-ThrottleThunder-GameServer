package networking.request;

import java.io.IOException;

import dataAccessLayer.DatabaseDriver;
import dataAccessLayer.model.CostModel;
import dataAccessLayer.model.PlayerModel;
import dataAccessLayer.model.VehicleModel;
import dataAccessLayer.record.Cost;
import dataAccessLayer.record.Player;
import dataAccessLayer.record.PlayerVehicle;
import networking.response.ResponseFriendList;
import networking.response.ResponseGarageDetails;
import networking.response.ResponseGaragePurchase;
import utility.DataReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class RequestGarageDetails extends GameRequest {
	private int carId;
	private int type;
	
	private ResponseGarageDetails response;
	
	public RequestGarageDetails() {
		response = new ResponseGarageDetails();
	}

	@Override
	public void parse() throws IOException {
		// TODO Auto-generated method stub
		carId = DataReader.readInt(dataInput);
		type = DataReader.readInt(dataInput);
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
		
		client.getServer().addResponseForUser(client.getPlayer().getUsername(), response);
	}

}
