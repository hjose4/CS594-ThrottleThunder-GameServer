package model;

public class Position {
	private float x;
	private float y;
	private float z;
	private float h;
	private float p;
	private float r;
	private int rank;
	
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
	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
