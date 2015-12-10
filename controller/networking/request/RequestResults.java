package controller.networking.request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import controller.networking.response.ResponseResults;
import driver.database.model.PlayerModel;
import driver.database.model.RankingModel;
import driver.database.record.Player;
import driver.database.record.Ranking;
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
		ArrayList<Ranking> rankings = RankingModel.searchForRankingsForGameId(gameId);
		HashMap<Player,Integer> results = new HashMap<>();
		for(Ranking model : rankings) {
			results.put(PlayerModel.getPlayerById(model.getPlayerId()), model.getRanking()); 	
		}
		responseResults.setRankings(results);
	}
}