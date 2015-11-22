package networking.request;

import java.io.IOException;
import java.util.HashMap;
import dataAccessLayer.Player;
import networking.response.ResponseRankings;

public class RequestRankings extends GameRequest {
	
	private ResponseRankings responseRankings;
	
	public RequestRankings() {
        responses.add(responseRankings = new ResponseRankings());
    }
	
	@Override
	public void parse() throws IOException {
		//parse the datainput here
		//x = DataReader.readFloat(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		//do the rankings business here
		HashMap<Player,Integer> rankings = client.getPlayer().getRoom().getRankings();
		responseRankings.setRankings(rankings);
	}

}
