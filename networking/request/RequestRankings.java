package networking.request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dataAccessLayer.record.Player;
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
		if(client.getSession() != null) {
			List<Player> rankings = client.getSession().getRankings();
			responseRankings.setRankings(rankings);
		} else {
			responseRankings.setRankings(new ArrayList<Player>());
			System.out.println("Client is not in game session: "+this.getClass().getName());
		}
	}

}
