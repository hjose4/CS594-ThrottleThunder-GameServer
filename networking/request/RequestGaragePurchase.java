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
	private int typeId;
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
		typeId = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		PlayerVehicle vehicle = VehicleModel.getPlayerVehicleById(carId);
		ArrayList<Upgrade> upgrades = new ArrayList<Upgrade>();
		int status = 0;
		if(vehicle != null) {
			if(typeId == 0){
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
					
					Player player = PlayerModel.getPlayerByUsername(client.getPlayer().getUsername());
					int mycurrency = player.getCurrency();
					int statlevel = 0;
					
					if(type == 1){
						//armor upgrade
						statlevel = armor+1;
					}
					if(type ==2){
						//health upgrade
						statlevel = health+1;
					}
					if(type == 3){
						//acceleration upgrade
						statlevel =acceleration+1;
					}
					if(statlevel<=7){
						Cost cost = CostModel.getCostBystatlevel(statlevel);
						int currencydeduct = cost.getPrice();
						if(mycurrency>currencydeduct){
							player.setCurrency(mycurrency-currencydeduct);
							player.save("all");
							status = 1;
						}
					}
				}
			}
			if(typeId == 1){
				// Set paint
				int paintId = type;
				vehicle.setPaint(paintId);
				vehicle.save("all");
				status =1;
				
						
			}
			
			if(typeId == 2){
				//Add response to all active players
				int tireId = type;
				vehicle.setTire(tireId);
				vehicle.save("all");
				status = 1;
				
			}
			
		}
		response.setStatus(status);
		client.getServer().addResponseForUser(client.getPlayer().getUsername(), response);
	}

}
