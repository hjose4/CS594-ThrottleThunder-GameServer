package networking.request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import dataAccessLayer.ObjectModel;
import dataAccessLayer.Player;
import dataAccessLayer.Rankings;
import dataAccessLayer.DatabaseDriver;
import networking.response.ResponseResults;
import utility.DataReader;

public class RequestResults extends GameRequest {

	// Data
	private int gameId;
	// Responses
	private ResponseResults responseResults;

	public RequestResults() {
		responses.add(responseResults = new ResponseResults());
	}

	@Override
	public void parse() throws IOException {
		gameId = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		
		HashMap<String, String> values1 = new HashMap<String, String>(); 
		values1.put("game_id", gameId+"");
		HashMap<Player, Integer> results = new HashMap<Player, Integer>(); 
	//	Rankings rank = new Rankings(values1);

		ArrayList<ObjectModel> ranks = dataAccessLayer.DatabaseDriver.find(Rankings.class, values1);
		for(ObjectModel model : ranks)
		{
		 results.put(new Player(dataAccessLayer.DatabaseDriver.findById(Player.class, model.get("player_id")).getData()), Integer.valueOf(model.get("ranking"))); 	
		}
		responseResults.setRankings(results);
	}
}