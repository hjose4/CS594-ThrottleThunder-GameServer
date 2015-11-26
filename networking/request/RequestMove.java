package networking.request;

import java.io.IOException;

import utility.DataReader;
import networking.response.ResponseMove;

public class RequestMove extends GameRequest {
	
	float steering, wheelforce, brakeforce;
	float x, y, z, h, p, r;
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
		responseMove.setSteering(steering);
		responseMove.setWheelforce(wheelforce);
		responseMove.setBrakeforce(brakeforce);
		if(client.getSession() != null)
			client.getSession().addResponseForAll(client.getPlayer().getId(), responseMove);
		else
			System.out.println("Client is not in game session: "+this.getClass().getName());
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public float getSteering() {
		return steering;
	}

	public void setSteering(float steering) {
		this.steering = steering;
	}

	public float getWheelforce() {
		return wheelforce;
	}

	public void setWheelforce(float wheelforce) {
		this.wheelforce = wheelforce;
	}

	public float getBrakeforce() {
		return brakeforce;
	}

	public void setBrakeforce(float brakeforce) {
		this.brakeforce = brakeforce;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}

	public float getP() {
		return p;
	}

	public void setP(float p) {
		this.p = p;
	}
}