package networking.request;

import java.io.IOException;

import utility.DataReader;
import networking.response.ResponseMove;

public class RequestMove extends GameRequest {
	
	float x, y, z, h, p, r;
	int forward, backward, right, left;
	private ResponseMove responseMove;
	
	public RequestMove() {
        responses.add(responseMove = new ResponseMove());
    }
	
	@Override
	public void parse() throws IOException {
		x = DataReader.readFloat(dataInput);
		y = DataReader.readFloat(dataInput);
		z = DataReader.readFloat(dataInput);
		h = DataReader.readFloat(dataInput);
		p = DataReader.readFloat(dataInput);
		r = DataReader.readFloat(dataInput);
		forward = DataReader.readInt(dataInput);
		backward = DataReader.readInt(dataInput);
		right = DataReader.readInt(dataInput);
		left = DataReader.readInt(dataInput);
	}

	@Override
	public void doBusiness() throws Exception {
		
		// Create ResponseMove object
		client.getPlayer().setPosition(this.x, this.y, this.z, this.h, this.p, this.r);
		responseMove.setPlayer(this.client.getPlayer());
		responseMove.setForward(forward);
		responseMove.setBackward(backward);
		responseMove.setRight(right);
		responseMove.setLeft(left);
		if(client.getSession() != null)
			client.getSession().addResponseForAll(client.getPlayer().getId(), responseMove);
		else
			System.out.println("Client is not in game session: "+this.getClass().getName());
	}
}