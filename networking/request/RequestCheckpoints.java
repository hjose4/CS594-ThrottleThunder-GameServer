package networking.request;

import java.io.IOException;

import utility.DataReader;
import networking.response.ResponseMove;

public class RequestCheckpoints extends GameRequest {
	
	int laps;
	int checkpoints;
	float distance;
	
	public RequestCheckpoints() {
		
    }
	
	@Override
	public void parse() throws IOException {
		laps = DataReader.readInt(dataInput);
		checkpoints = DataReader.readInt(dataInput);
		distance = DataReader.readFloat(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		
//		client.getPlayer().setLaps(laps);
//		client.getPlayer().setCheckpoints(checkpoints);
	}
}