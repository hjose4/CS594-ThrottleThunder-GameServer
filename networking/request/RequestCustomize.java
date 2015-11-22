package networking.request;

// Java Imports
import java.io.IOException;
import dataAccessLayer.DatabaseDriver;
import dataAccessLayer.record.BaseVehicle;
import dataAccessLayer.record.Player;
import dataAccessLayer.record.Upgrade;
import networking.response.ResponseCustomize;
// Custom Imports
//import core.GameServer;
import utility.DataReader;

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
		if (client.getServer().getThreadByPlayerUserName(username) != null) {
			Player player = client.getPlayer();
			Upgrade upgrade = new Upgrade(DatabaseDriver.findById(Upgrade.class, carId).getData());
			BaseVehicle vehicle = new BaseVehicle(DatabaseDriver.findById(BaseVehicle.class, carId).getData());
			if(player.getCurrency() >= upgrade.getPrice()) {
				player.setCurrency(player.getCurrency() - upgrade.getPrice());
				player.save(Player.CURRENCY);
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