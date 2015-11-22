package networking.request;

// Java Imports
import java.io.IOException;

import dataAccessLayer.DatabaseDriver;
import networking.response.GameResponse;
import networking.response.ResponseCustomize;
// Custom Imports
//import core.GameServer;
import utility.DataReader;
import dataAccessLayer.Player;
import dataAccessLayer.Vehicle;
import dataAccessLayer.Upgrade;

public class RequestCustomize extends GameRequest {

	// Data
	private String username;
	private int statisticType;
	private int carId;
	// Responses
	private ResponseCustomize responseCustomize;
	private int player_id;

	public RequestCustomize() {
		responseCustomize = new ResponseCustomize();

	}

	@Override
	public void parse() throws IOException {
		username = DataReader.readString(dataInput);
		statisticType = DataReader.readInt(dataInput);
		carId = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		
//		responseAuth.setAnswer((short) 1);
		
		if (client.getServer().getThreadByPlayerUserName(username) != null) {
			Player player = client.getPlayer();
			Upgrade upgrade = new Upgrade(DatabaseDriver.findById(Upgrade.class, carId).getData());
			Vehicle vehicle = new Vehicle(DatabaseDriver.findById(Vehicle.class, carId).getData());
			if(player.getCurrency() >= upgrade.getPrice()) {
				player.setCurrency(player.getCurrency() - upgrade.getPrice());
				player.update();
				upgrade.setVehicleId(vehicle.getId());
				DatabaseDriver.insert(upgrade);
				responseCustomize.setMessage("");
			} else {
				
			}

//			responseCustomize.setMessage(msg);
//			client.getServer().addResponseForUser(username, (GameResponse) responseCustomize); 
//			client.getPlayer().setCar_id(carId);// sets the car for the Player
		}

	}
}