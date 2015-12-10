package driver.database;

import java.lang.reflect.Constructor;
import java.sql.*;
import java.util.*;

import driver.database.record.Friendship;
import driver.database.record.GameRoom;
import driver.database.record.Player;
import driver.database.record.PlayerVehicle;
import driver.database.record.Ranking;
import utility.JsonFileParser;

//Singleton
public class DatabaseDriver {

	private static HashMap<Class<? extends ObjectModel>, String> table_map; //Mapping between classes in ObjectModel and database tables

	private static Connection conn = null;
	private static DatabaseDriver Instance = null;
	public String userName = "";
	public String password = "";
	public String serverName = "";
	public String databaseName = "";
	public int portNumber = 3306;

	public static DatabaseDriver getInstance() {
		if (Instance == null) {
			Instance = new DatabaseDriver();
		}
		return Instance;
	}

	public static void init() {
		getInstance();
	}

	private static void init_table_map() {
		table_map=new HashMap<>();
		table_map.put(PlayerVehicle.class, "player_vehicles");
		table_map.put(GameRoom.class, "games");
		table_map.put(Player.class, "players");
		table_map.put(Friendship.class, "friend_relationships");
		table_map.put(Ranking.class, "game_rankings");
	}

	private static String getTable(Class<? extends ObjectModel> type) {
		return table_map.get(type);
	}

	protected DatabaseDriver() {
		init_table_map();

		HashMap<String,String> config = JsonFileParser.getDatabaseConfig();
		userName = config.get("username");
		password = config.get("password");
		serverName = config.get("host");
		portNumber = Integer.valueOf(config.get("port"));
		databaseName = config.get("database");

		System.out.println("username: " + userName);
		System.out.println("password: " + password);
		System.out.println("server: " + serverName);
		System.out.println("port: " + portNumber);

		connect();
	}

	// close the connection
	public void close() {
		try {
			getInstance().close();
		} catch (Exception e) {
			System.out.println("exception in close :" + e);
		}
	}

	protected void connect() {
		try {
			Properties connectionProps = new Properties();

			// setproperty only accept the string.

			connectionProps.setProperty("user", this.userName);
			connectionProps.setProperty("password", this.password);

			conn = DriverManager.getConnection(
					"jdbc:mysql://" + this.serverName + ":" + this.portNumber + "/" + databaseName, connectionProps);

		} catch (Exception e) {
			System.out.println("Connection failure with the database :" + e);
			e.printStackTrace();
			System.exit(1);
		}
	}

	protected void checkConnection() {
		try {
			if (conn.isClosed() || !conn.isValid(1)) {
				connect();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean update(Class<? extends ObjectModel> type, int id, String field, Object value) {
		int ret = 0;
		try {
			checkConnection();
			String query = "UPDATE "+getTable(type)+" SET "+field+"='"+value.toString()+"' WHERE id="+id+";";
			Statement stmt = conn.createStatement();
			//afficher la requete
			//System.out.println("Requete MAJ : " + query);
			//executer la modification
			ret = stmt.executeUpdate(query);
			if(stmt!=null) stmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return ret > 0;
	}

	public boolean update(Class<? extends ObjectModel> type, int id, HashMap<String,String> values) {
		int ret = 0;
		try {
			checkConnection();
			String query = "UPDATE "+getTable(type)+" SET ";
			for(String col : values.keySet()) {
				query += col + " = ?, ";
			}
			query.substring(0, query.length()-2);
			query += " WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			int c = 1;
			for(String value : values.values()) {
				if(value.equals("id")) {
					continue;
				}
				stmt.setString(c, value);
				c++;
			}
			stmt.setInt(c, id);
			ret = stmt.executeUpdate();
			if(stmt!=null) stmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return ret > 0;
	}

	private ArrayList<HashMap<String, String>> select(String requete) throws SQLException {
		checkConnection();
		ArrayList<HashMap<String, String>> table = new ArrayList<>();
		HashMap<String, String> ligne = new HashMap<>();

		// System.out.println("Requete Recherche : " + requete);
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(requete);

		ResultSetMetaData rsetMeta = rset.getMetaData();
		int nbColonne = rsetMeta.getColumnCount();

		ArrayList<String> fields = new ArrayList<>();

		for (int i = 1; i <= nbColonne; i++) {
			fields.add(rsetMeta.getColumnLabel(i));
		}

		while (rset.next()) 
		{
			ligne = new HashMap<>();
			for (int i = 0; i < nbColonne; i++) {
				ligne.put(fields.get(i), rset.getString(i + 1));
			}
			table.add(ligne);
		}
		return table;
	}

	private ArrayList<HashMap<String, String>> select(Class<? extends ObjectModel> type, HashMap<String, String> data){
		checkConnection();
		String query = "SELECT * FROM "+getTable(type)+" WHERE ";
		boolean first=true;
		ArrayList<HashMap<String, String>> raw_results;
		ArrayList<ObjectModel> results = new ArrayList<>();

		for (Map.Entry<String, String> entry : data.entrySet())
		{
			if(first){
				first=false;
			}
			else{
				query = query + " AND ";
			}
			query = query + entry.getKey() + "='" + entry.getValue() + "' ";
		}
		try{
			raw_results = select(query);
			return raw_results;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<ObjectModel> find(Class<? extends ObjectModel> type, HashMap<String, String> param){
		try {
			checkConnection();
			ArrayList<ObjectModel> results = new ArrayList<>();
			String table = table_map.get(type);

			ArrayList<HashMap<String, String>> raw_results = select(type, param);

			for(HashMap<String, String> data : raw_results){

				Constructor<? extends ObjectModel> constr = type.getConstructor(HashMap.class);
				results.add((ObjectModel)constr.newInstance(data));
			}

			return results;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	//renvoie tous les objets ou l'attribut field=value
	public ArrayList<ObjectModel> find(Class<? extends ObjectModel> type, String field, Object value)
	{
		HashMap<String, String> critere = new HashMap<>();
		critere.put(field, value.toString());
		return find(type,critere);
	}

	public ObjectModel findById(Class<? extends ObjectModel> type, Object id) throws SQLException {
		ArrayList<ObjectModel> d = find(type,"id",id);
		if(!d.isEmpty())
			return (ObjectModel) d.get(0);
		else throw new SQLException("No object of type "+type.getSimpleName()+"found for id "+id);
	}

	public boolean alreadyExists(Class<? extends ObjectModel> type, Object id){
		try{
			findById(type, id);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}

	//query of object creation, returns id given to the object by the DB
	public int insert(ObjectModel obj){            
		try {
			checkConnection();
			String table = DatabaseDriver.table_map.get(obj.getClass());
			HashMap<String, String> data = obj.getData();

			ArrayList<String> field_names=new ArrayList<>();
			ArrayList<String> values=new ArrayList<>();

			String query = "INSERT INTO "+table+"(";
			int id=-1;

			//we extract fields names and their values
			for (Map.Entry<String, String> entry : data.entrySet()) {
				if(entry.getKey().equals("id")) continue;
				field_names.add(entry.getKey());
				values.add(entry.getValue());
			}

			//we write the SQL query
			for (String field : field_names){
				query=query.concat(field+",");
			}
			query=query.substring(0,query.length()-1);
			query=query.concat(") values (");
			for (String s : values){
				query = query.concat("'"+s+"',");
			}
			query=query.substring(0,query.length()-1);
			query =query.concat(");");

			//display query
			//System.out.println("Requete creation : " + query);

			//execute insertion query
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);

			//getting id of the created object
			ArrayList<HashMap<String, String>> result = select("SELECT LAST_INSERT_ID() FROM "+table);
			id=Integer.valueOf(result.get(0).get("LAST_INSERT_ID()"));
			if(!stmt.isClosed()) stmt.close();
			return id;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return -1;
	}

	//query of object creation, returns id given to the object by the DB
	public int insert(Class<? extends ObjectModel> type, HashMap<String,String> data){            
		try {
			checkConnection();
			String table = DatabaseDriver.table_map.get(type);

			ArrayList<String> field_names=new ArrayList<>();
			ArrayList<String> values=new ArrayList<>();

			String query = "INSERT INTO "+table+"(";
			int id=-1;

			//we extract fields names and their values
			for (Map.Entry<String, String> entry : data.entrySet()) {
				if(entry.getKey().equals("id")) continue;
				field_names.add(entry.getKey());
				values.add(entry.getValue());
			}

			//we write the SQL query
			for (String field : field_names){
				query=query.concat(field+",");
			}
			query=query.substring(0,query.length()-1);
			query=query.concat(") values (");
			for (String s : values){
				query = query.concat("'"+s+"',");
			}
			query=query.substring(0,query.length()-1);
			query =query.concat(");");

			//display query
			//System.out.println("Requete creation : " + query);

			//execute insertion query
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);

			//getting id of the created object
			ArrayList<HashMap<String, String>> result = select("SELECT LAST_INSERT_ID() FROM "+table);
			id=Integer.valueOf(result.get(0).get("LAST_INSERT_ID()"));
			if(!stmt.isClosed()) stmt.close();
			return id;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return -1;
	}
	
	public boolean remove(Class<? extends ObjectModel> type, int id) {
		int ret = 0;
		try {
			checkConnection();
			String query = "DELETE FROM "+getTable(type);
			query += " WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			ret = stmt.executeUpdate();
			if(stmt!=null) stmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return ret > 0;
	}

}