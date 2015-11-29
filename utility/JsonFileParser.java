package utility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import model.MapDetail;
import model.MapManager;
import model.Position;
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
					mapManager.addMapDetails(new MapDetail(details.getInt("type"),details.getString("name"),details.getInt("required"),positions));					
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
