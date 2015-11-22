package networking.request;

import java.io.IOException;

import utility.DataReader;
import networking.response.ResponseSetRank;

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
		responseSetRank.setRank(rank);
		client.getSession().updatePlayerRanking(client.getPlayer(), rank);
		//client.getPlayer().getRoom()client;
	}

}
