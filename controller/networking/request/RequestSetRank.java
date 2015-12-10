package controller.networking.request;

import java.io.IOException;

import controller.networking.response.ResponseSetRank;
import utility.DataReader;

public class RequestSetRank extends GameRequest {
	
	private int rank;
	private ResponseSetRank responseSetRank;
	
	public RequestSetRank() {
        responses.add(responseSetRank = new ResponseSetRank());
    }
	
	@Override
	public void parse() throws IOException {
		//parse the datainput here
		//x = DataReader.readFloat(dataInput);
		DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		//do the rankings business here
		if(client.getSession() != null) {
			responseSetRank.setRank(client.getSession().getRankings().indexOf(client.getPlayer()));
		} else {
			responseSetRank.setRank(0);
		}
		
		//client.getPlayer().getRoom()client;
	}

}
