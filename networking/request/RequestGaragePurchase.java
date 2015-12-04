package networking.request;

import java.io.IOException;

import dataAccessLayer.model.VehicleModel;
import dataAccessLayer.record.PlayerVehicle;
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
		Arraylist<Upgrade> upgrades = new Arraylist<Upgrade>();
		if(vehicle != null) {
			if(typeId == 0){
				ArrayList<PlayerVehicleUpgrade> vehicleUpgrades = PlayerVehicleUpgradeModel.getUpgradesByVehicleId(carId);
				if(vehicleUpgrades!=null){
					for(PlayerVehicleUpgrade vehicleUpgrade : vehicleUpgrades)
						Upgrade upgrade = UpgradeModel.getUpgradeById(vehicleUpgrades.getId());
						upgrades.add(upgrade);
				}
			}
			if(typeId == 1){
				// Set paint
				int paintId = type;
				vehicle.setPaint(type);
				
						
			}
			
			if(typeId == 2){
				//Add response to all active players
				int tireId = type;
				vehicle.setTire(tireId)
				
			}
		}

	}

}
