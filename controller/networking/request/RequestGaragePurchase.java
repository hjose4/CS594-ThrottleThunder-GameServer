package controller.networking.request;

import java.io.IOException;

import driver.database.DatabaseDriver;
import driver.database.model.PlayerModel;
import driver.database.model.VehicleModel;
import driver.database.record.Player;
import driver.database.record.PlayerVehicle;
import driver.json.collection.BaseVehicleCollection;
import driver.json.collection.CostCollection;
import driver.json.record.BaseVehicle;
import utility.DataReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.sun.media.jfxmedia.logging.Logger;

import controller.networking.response.ResponseCurrency;
import controller.networking.response.ResponseFriendList;
import controller.networking.response.ResponseGaragePurchase;

public class RequestGaragePurchase extends GameRequest {
	private int carId;
	private int type;

	private ResponseGaragePurchase response;

	public RequestGaragePurchase() {
		responses.add(response = new ResponseGaragePurchase());
	}

	@Override
	public void parse() throws IOException {
		// TODO Auto-generated method stub
		carId = DataReader.readInt(dataInput);
		type = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		System.out.println("Purchase Car ID: " + carId + ", type: " + type);
		carId += 1;
		Player player =client.getPlayer();
		int mycurrency = player.getCurrency();
		BaseVehicle base = BaseVehicleCollection.getVehicle(carId);
		List<PlayerVehicle> playerVehicles = VehicleModel.searchForPlayerVehicles(player, base);
		boolean status = false;

		// Get the currency the player is having

		Integer cost =0;

		response.setStatus(0);
		if(!playerVehicles.isEmpty()) {
			PlayerVehicle vehicle = playerVehicles.get(0);
			if(type == 1){
				//armor upgrade
				cost = CostCollection.getCost(vehicle.getArmorUpgrade()+1);
				if(cost != null && mycurrency > cost)
					status = vehicle.incrementArmorUpgrade();
			}
			if(type ==2){
				//handling upgrade
				cost = CostCollection.getCost(vehicle.getHandlingUpgrade()+1);
				if(cost != null && mycurrency > cost)
					status = vehicle.incrementHealthUpgrade();
			}
			if(type == 3){
				//power upgrade
				cost = CostCollection.getCost(vehicle.getHealthUpgrade()+1);
				if(cost != null && mycurrency > cost)
					status = vehicle.incrementHealthUpgrade();
			}
			if(type == 4){
				//max speed upgrade
				cost = CostCollection.getCost(vehicle.getSpeedUpgrade()+1);
				if(cost != null && mycurrency > cost)
					status = vehicle.incrementSpeedUpgrade();
			}
			if(status){
				System.out.println("Status updated");
				response.setStatus(1);
				
				//deduct the price for the upgrade
				player.setCurrency(mycurrency-cost);
				player.save("all"); // save the player with the deducted currency

				vehicle.save("all");//save the car with all upgrades
				
			}
		}
		
		ResponseCurrency currencyResponse = new ResponseCurrency();
		currencyResponse.setCurrency(player.getCurrency());
		responses.add(currencyResponse);
	}

}
