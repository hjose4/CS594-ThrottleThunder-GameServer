package controller.networking.request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.networking.response.ResponseRankings;
import driver.database.record.Player;

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
			responseRankings.setRankings(client.getSession().getRankings());
		} else {
			responseRankings.setRankings(new ArrayList<Player>());
			System.out.println("Client is not in game session: "+this.getClass().getName());
		}
	}

}
