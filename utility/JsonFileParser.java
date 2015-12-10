package utility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import driver.data.model.Position;
import driver.json.collection.BaseVehicleCollection;
import driver.json.collection.CostCollection;
import driver.json.collection.MapManager;
import driver.json.collection.UpgradesCollection;
import driver.json.record.MapDetails;
import utility.vendors.douglascrockford.json.*;

public class JsonFileParser {
	private static HashMap<String,String> serverConfig = new HashMap<>();
	private static HashMap<String,String> databaseConfig = new HashMap<>();
	public static void parseFile(String filename) {
		try {
			Scanner scan = new Scanner(new FileReader(filename));
			String jsonStr = "";
			while(scan.hasNext()) {
				jsonStr += scan.nextLine();
			}
			scan.close();

			JSONObject rootObject = new JSONObject(jsonStr); // Parse the JSON to a JSONObject

			//Parse Server Config
			JSONObject serverConfig = rootObject.getJSONObject("server");
			if(serverConfig != null) {
				for(String name : JSONObject.getNames(serverConfig))
					JsonFileParser.serverConfig.put(name,serverConfig.getString(name));
			}

			//Parse Database Config
			JSONObject databaseConfig = rootObject.getJSONObject("database");
			if(databaseConfig != null) {
				for(String name : JSONObject.getNames(databaseConfig))
					JsonFileParser.databaseConfig.put(name,databaseConfig.getString(name));
			}

			//Parse MapDetails
			JSONArray rows = rootObject.getJSONArray("maps"); // Get all JSONArray rows
			if(rows != null) {
				MapManager mapManager = MapManager.getInstance();
				for(int i=0; i < rows.length(); i++) { // Loop over each each row
					JSONObject details = rows.getJSONObject(i); // Get row object
					List<Position> positions = new ArrayList<>();
					JSONArray jsonList = details.getJSONArray("positions");
					for(int r = 0; r < jsonList.length(); r++) {
						JSONArray jsonCol = jsonList.getJSONArray(r);
						List<Float> items = new ArrayList<>();
						for(int c = 0; c < jsonCol.length(); c++) {
							items.add((float)jsonCol.getDouble(c));
						}
						positions.add(new Position(items));
					}
					mapManager.addMapDetails(new MapDetails(details.getInt("type"),details.getString("name"),details.getInt("mode"),details.getInt("required"),positions));					
				}
			}
			
			//Parse Vehicle Models
			rows = rootObject.getJSONArray("vehicles");
			if(rows != null) {
				for(int i = 0; i < rows.length(); i++) {
					JSONObject model = rows.getJSONObject(i);
					System.out.println("Creating Car: Type - " + model.getInt("type") + " Name - " + model.getString("name"));
					BaseVehicleCollection.addVehicle(model.getInt("type"), model.getString("name"));
				}
			}
			
			//Parse Upgrades 
			JSONArray upgrades = rootObject.getJSONArray("upgrades");
			if(upgrades != null) {
				for(int i = 0; i < upgrades.length(); i++) {
					JSONObject model = upgrades.getJSONObject(i);
					UpgradesCollection.addUpgradeType(model.getInt("type"), model.getString("name"), model.getInt("max"));
				}
			}
			
			JSONObject costs = rootObject.getJSONObject("upgrade_cost");
			if(costs != null) {
				for(int i = 0; i < 8; i++){
					CostCollection.addCost(i, costs.getInt(Integer.toString(i)));
				}
			}
			
			
		} catch (JSONException e) {
			// JSON Parsing error
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static HashMap<String,String> getServerConfig() {
		return serverConfig;
	}
	
	public static HashMap<String,String> getDatabaseConfig() {
		return databaseConfig;
	}
}
