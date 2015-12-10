package driver.json.record;

public class UpgradeType {
	private int type;
	private String name;
	private int max;
	
	public UpgradeType(int type, String name, int max) {
		this.type = type;
		this.name = name;
		this.max = max;
	}

	public int getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	//cur is the current status, and up is the upgrade status 
	public boolean isValidUpgrade(int cur, int up) {
		return ((cur>=0)&&(up>=cur)&&(up<=max));
	}
}
