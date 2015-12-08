package driver.json.record;

public class BaseVehicle {
	private int type;
	private String name;
	
	public BaseVehicle(int id, String name) {
		// TODO Auto-generated constructor stub
		this.type = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getCarType() {
		return type;
	}
}
