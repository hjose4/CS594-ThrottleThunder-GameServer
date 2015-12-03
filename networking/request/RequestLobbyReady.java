package networking.request;

// Java Imports
import java.io.IOException;
import java.util.List;

import dataAccessLayer.model.VehicleModel;
import dataAccessLayer.record.Player;
import dataAccessLayer.record.PlayerVehicle;
import json.collections.BaseVehicleCollection;
import json.model.BaseVehicle;
import networking.response.ResponseEnterQueue;
// Custom Imports
//import core.GameServer;
import utility.DataReader;

public class RequestLobbyReady extends GameRequest {

	// Data
	private int baseCarId;
	// Responses

	public RequestLobbyReady() {}

	@Override
	public void parse() throws IOException {
		baseCarId = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		if(client.getSession() != null) {
			if(baseCarId > 0) {
				List<PlayerVehicle> vehicles = VehicleModel.searchForPlayerVehicles(client.getPlayer(), baseCarId);
				if(vehicles.isEmpty()) {
					System.out.println("Player " + client.getPlayer().getUsername() + " does not have a vehicle of type " + baseCarId);
					BaseVehicle baseVehicle = BaseVehicleCollection.getVehicle(baseCarId);
					if(baseVehicle != null) {
						vehicles.add(VehicleModel.createPlayerVehicleFromBaseVehicle(baseVehicle,client.getPlayer()));
					} else {
						//This is even more harsh
						System.out.println("We are missing base vehicle " + baseCarId + " from the database");
					}
				}
				
				if(vehicles.size() > 0) {
					//Lets hope for one car
					PlayerVehicle vehicle = null;
					if(vehicles.size() > 1) {
						//Oh snaps!!
						System.out.println("Too many cars!!!!");
					}
					
					vehicle = vehicles.get(0);
					client.getPlayer().setCarPaint(vehicle.getPaintId());
					client.getPlayer().setCarTire(vehicle.getCarTireId());
					client.getPlayer().setBaseCarId(vehicle.getBaseVehicleId());
				} else {
					System.out.println("Hahaha, I refused to create the car");
					return;
				}
			} else {
				client.getPlayer().setCarPaint(0);
				client.getPlayer().setCarTire(0);
				client.getPlayer().setBaseCarId(0);
			}
			client.getSession().addResponseForRenderCharacters(client);
		} else {
			System.out.println("Client is not in game session: "+this.getClass().getName());
		}		
	}
}
