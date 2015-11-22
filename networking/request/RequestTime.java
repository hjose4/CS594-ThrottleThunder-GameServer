package networking.request;

import java.io.IOException;

import utility.DataReader;
import networking.response.ResponseTime;

public class RequestTime extends GameRequest {
	private ResponseTime responseTime;
	
	public RequestTime() {
        responses.add(responseTime = new ResponseTime());
    }
	
	@Override
	public void parse() throws IOException {
		//parse the datainput here
		//x = DataReader.readFloat(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		//do the rankings business here
		responseTime.setData(1, 100000);
		//client.getPlayer().getRoom()client;
	}

}
