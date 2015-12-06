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
import networking.response.ResponseGarageDetails;
import networking.response.ResponseGaragePurchase;
import utility.DataReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class RequestGarageDetails extends GameRequest {
	private int carId;
	private int typeId;
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
		typeId = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		PlayerVehicle vehicle = VehicleModel.getPlayerVehicleById(carId);
		ArrayList<Upgrade> upgrades = new ArrayList<Upgrade>();
		int status = 0;
		if(vehicle != null) {
			//Find the upgrades for the car: Each vehicle can have a number of records as upgrade
			//Use player-vehicle relationship table to get the upgradeId
				ArrayList<PlayerVehicleUpgrade> vehicleUpgrades = PlayerVehicleUpgradeModel.searchForUpgrades(carId);
				if(vehicleUpgrades!=null){
					for(PlayerVehicleUpgrade vehicleUpgrade : vehicleUpgrades){
						Upgrade upgrade = UpgradeModel.getUpgradeById(vehicleUpgrade.getUpgradeId());
						upgrades.add(upgrade);
					}
					ArrayList<Integer> armors = new ArrayList<Integer>();
					ArrayList<Integer> healths = new ArrayList<Integer>();
					ArrayList<Integer> accelerations = new ArrayList<Integer>();
					for (Upgrade upgrade : upgrades)
					{
						armors.add((int) upgrade.getArmor());
						healths.add((int) upgrade.getHealth());
						accelerations.add((int) upgrade.getAcceleration());
						
					}
					Integer armor = Collections.max(armors);
					Integer health = Collections.max(healths);
					Integer acceleration = Collections.max(accelerations);
					
					response.setArmor(armor);
					response.setHealth(health);
					response.setAcceleration(acceleration);
			
				}
		}
		
		
		client.getServer().addResponseForUser(client.getPlayer().getUsername(), response);
	}

}
