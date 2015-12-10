package controller.networking.request;

import java.io.IOException;

import driver.database.DatabaseDriver;
import driver.database.model.PlayerModel;
import driver.database.model.VehicleModel;
import driver.database.record.Player;
import driver.database.record.PlayerVehicle;
import driver.json.collection.CostCollection;
import utility.DataReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import controller.networking.response.ResponseFriendList;
import controller.networking.response.ResponseGaragePurchase;

public class RequestGaragePurchase extends GameRequest {
	private int carId;
	private int type;
	
	private ResponseGaragePurchase response;
	
	public RequestGaragePurchase() {
		response = new ResponseGaragePurchase();
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
		boolean status = false;
		
		// Get the currency the player is having
		Player player = PlayerModel.getPlayerByUsername(client.getPlayer().getUsername());
		int mycurrency = player.getCurrency();
		int cost =0;
	
		if(vehicle != null) {
			
			if(type == 1){
				//armor upgrade
				cost = CostCollection.getCost(vehicle.getArmorUpgrade()+1);
				if(mycurrency > cost)
					status = vehicle.incrementArmorUpgrade();
			}
			if(type ==2){
				//handling upgrade
				cost = CostCollection.getCost(vehicle.getArmorUpgrade()+1);
				if(mycurrency > cost)
					status = vehicle.incrementHealthUpgrade();
			}
			if(type == 3){
				//power upgrade
				cost = CostCollection.getCost(vehicle.getAccelerationUpgrade()+1);
				if(mycurrency > cost)
					status = vehicle.incrementAccelerationUpgrade();
			}
			if(type == 4){
				//max speed upgrade
				cost = CostCollection.getCost(vehicle.getSpeedUpgrade()+1);
				if(mycurrency > cost)
					status = vehicle.incrementSpeedUpgrade();
			}
			if(status){
				//deduct the price for the upgrade
				player.setCurrency(mycurrency-cost);
				player.save("all"); // save the player with the deducted currency
				
				vehicle.save("all");//save the car with all upgrades
				response.setStatus(1);
			}else 
				response.setStatus(0);
			
		}
		
		client.getServer().addResponseForUser(client.getPlayer().getUsername(), response);
	}

}
