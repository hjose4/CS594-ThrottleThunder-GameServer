package networking.request;

import java.io.IOException;

import utility.DataReader;
import networking.response.ResponseMove;

public class RequestMove extends GameRequest {
	
	float steering, wheelforce, brakeforce;
	private ResponseMove responseMove;
	
	public RequestMove() {
		System.out.println("requested move");
        responses.add(responseMove = new ResponseMove());
    }
	
	@Override
	public void parse() throws IOException {
		steering = DataReader.readFloat(dataInput);
		wheelforce = DataReader.readFloat(dataInput);
		brakeforce = DataReader.readFloat(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		
		// Create ResponseMove object
		client.getPlayer().setForces(this.steering, this.wheelforce, this.brakeforce);
		responseMove.setPlayer(this.client.getPlayer());
		responseMove.setSteering(steering);
		responseMove.setWheelforce(wheelforce);
		responseMove.setBrakeforce(brakeforce);
		if(client.getSession() != null)
			client.getSession().addResponseForAll(client.getPlayer().getId(), responseMove);
		else
			System.out.println("Client is not in game session: "+this.getClass().getName());
	}
}