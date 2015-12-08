package controller.networking.request;

import java.io.IOException;

import controller.networking.response.ResponseMove;
import utility.DataReader;

public class RequestMove extends GameRequest {
	
	float steering, wheelforce, brakeforce;
	float x, y, z, h, p, r;
	private ResponseMove responseMove;
	
	public RequestMove() {
        responseMove = new ResponseMove();
    }
	
	@Override
	public void parse() throws IOException {
		steering = DataReader.readFloat(dataInput);
		wheelforce = DataReader.readFloat(dataInput);
		brakeforce = DataReader.readFloat(dataInput);
		x = DataReader.readFloat(dataInput);
		y = DataReader.readFloat(dataInput);
		z = DataReader.readFloat(dataInput);
		h = DataReader.readFloat(dataInput);
		p = DataReader.readFloat(dataInput);
		r = DataReader.readFloat(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		
		// Create ResponseMove object
		client.getPlayer().setForces(this.steering, this.wheelforce, this.brakeforce);
		client.getPlayer().setPosition(this.x, this.y, this.z, this.h, this.p, this.r);
		responseMove.setPlayer(this.client.getPlayer());
		if(client.getSession() != null)
			client.getSession().addResponseForAll(client.getPlayer().getId(), responseMove);
		else
			System.out.println("Client is not in game session: "+this.getClass().getName());
	}
}