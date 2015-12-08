package driver.data.model;

import java.util.List;

public class Position {
	private float x;
	private float y;
	private float z;
	private float h;
	private float p;
	private float r;
	private float steering;
	private float wheelforce;
	private float brakeforce;
	
	public Position(){
		this.x=0f;
		this.y=0f;
		this.z=0f;
		this.h=0f;
		this.p=0f;
		this.r=0f;
	}
	
	public Position(float x, float y, float z, float h, float p, float r){
		this.x=x;
		this.y=y;
		this.z=z;
		this.h=h;
		this.p=p;
		this.r=r;
	}
	
	public Position(float x, float y, float z, float h, float p, float r, float steering, float wheelforce, float brakeforce){
		this.x=x;
		this.y=y;
		this.z=z;
		this.h=h;
		this.p=p;
		this.r=r;
		this.steering = steering;
		this.wheelforce = wheelforce;
		this.brakeforce = brakeforce;
	}
	
	public Position(List<Float> items) {
		this();
		
		int size = items.size();
		switch(size) {
			case 6:
				setR(items.get(5));
			case 5:
				setP(items.get(4));
			case 4:
				setH(items.get(3));
			case 3:
				setZ(items.get(2));
			case 2:
				setY(items.get(1));
			case 1:
				setX(items.get(0));
				break;
		}
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
}
