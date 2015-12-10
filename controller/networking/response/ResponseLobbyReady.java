package controller.networking.response;

import core.meta.Constants;
import utility.GamePacket;

public class ResponseLobbyReady extends GameResponse {

    private String username;
    private int carType;
    private int carPaint;
    private int carTires;

	public ResponseLobbyReady() {
        responseCode = Constants.SMSG_LOGIN;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addString(username);
        packet.addInt32(carType);
        packet.addInt32(carPaint);
        packet.addInt32(carTires);
        return packet.getBytes();
    }
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getCarType() {
		return carType;
	}

	public void setCarType(int carType) {
		this.carType = carType;
	}

	public int getCarPaint() {
		return carPaint;
	}

	public void setCarPaint(int carPaint) {
		this.carPaint = carPaint;
	}

	public int getCarTires() {
		return carTires;
	}

	public void setCarTires(int carTires) {
		this.carTires = carTires;
	}
}
