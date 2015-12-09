package networking.request;

import java.io.IOException;

import dataAccessLayer.DatabaseDriver;
import dataAccessLayer.model.CostModel;
import dataAccessLayer.model.PlayerModel;
import dataAccessLayer.model.PlayerVehicleUpgradeModel;
import dataAccessLayer.model.UpgradeModel;
import dataAccessLayer.model.VehicleModel;
import dataAccessLayer.record.Cost;
import dataAccessLayer.record.Player;
import dataAccessLayer.record.PlayerVehicle;
import dataAccessLayer.record.PlayerVehicleUpgrade;
import dataAccessLayer.record.Upgrade;
import networking.response.ResponseFriendList;
import networking.response.ResponseGaragePurchase;
import utility.DataReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class RequestGaragePurchase extends GameRequest {
	private int carId;
	private int type;
	private int value;
	
	private ResponseGaragePurchase response;
	
	public RequestGaragePurchase() {
		response = new ResponseGaragePurchase();
	}

	@Override
	public void parse() throws IOException {
		// TODO Auto-generated method stub
		carId = DataReader.readInt(dataInput);
		type = DataReader.readInt(dataInput);
		value = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		PlayerVehicle vehicle = VehicleModel.getPlayerVehicleById(carId);
		int status = 0;
		if(vehicle != null) {
			int statlevel = 0;
			
			if(type == 1){
				//armor upgrade
				int armor = value;
				statlevel = armor+1;
			}
			if(type ==2){
				//health upgrade
				int health = value;
				statlevel = health+1;
			}
			if(type == 3){
				//acceleration upgrade
				int acceleration = value;
				statlevel =acceleration+1;
			}
			if(type == 4){
				//max speed upgrade
				int speed = value;
				statlevel =speed+1;
			}
			if(statlevel<=7){
				//check the price for the upgrade
				Cost cost = CostModel.getCostBystatlevel(statlevel);
				int currencydeduct = cost.getPrice();
				
				// Get the currency the player is having
				Player player = PlayerModel.getPlayerByUsername(client.getPlayer().getUsername());
				int mycurrency = player.getCurrency();
				
				
				if(mycurrency>currencydeduct){
					player.setCurrency(mycurrency-currencydeduct);
					player.save("all"); // save the player with the deducted currency
					status = 1; // successful upgrade
				}
				if(status ==1){
					// Update the upgrades table and the vehicle-upgrade table
					
					
				}
			}
			
		}
		response.setStatus(status);
		client.getServer().addResponseForUser(client.getPlayer().getUsername(), response);
	}

}
