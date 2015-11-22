package dataAccessLayer;

import java.lang.reflect.Constructor;
import java.sql.*;
import java.util.*;

import utility.ConfFileParser;
import configuration.GameServerConf;

//Singleton
public class DatabaseDriver {
	//protected final String BASE_VEHICLE = "base_vehicles";
	//protected final String DD_GAME_RANKINGS = "dd_game_rankings";
	//protected final String FRIENDS_RELATIONSHIP = "friends_relationship";
	//protected final String GAMES = "games";
	//protected final String PLAYER_POWERUPS = "player_powerups";
	//protected final String PLAYER_VEHICLES = "player_vehicles";
	//protected final String PLAYERS = "players";
	//protected final String POWERUPS = "powerups";
	protected final String RR_GAME_RANKINGS = "rr_game_rankings";
	protected final String UPGRADES = "upgrades";
	
	private static HashMap<Class<? extends ObjectModel>, String> table_map; //Mapping between classes in ObjectModel and database tables
	
	private static Connection conn = null;
	private static DatabaseDriver Instance = null;
	
	protected GameServerConf configuration;

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
        table_map.put(BaseVehicle.class, "base_vehicles");
        table_map.put(PlayerVehicle.class, "player_vehicles");
        table_map.put(Upgrade.class, "upgrades");
        table_map.put(GameRoom.class, "games");
        table_map.put(Player.class, "players");
        table_map.put(Friendship.class, "friend_relationships");
        table_map.put(BaseVehicle.class, "player_vehicles");
    }
	
	private static String getTable(Class<? extends ObjectModel> type) {
        return table_map.get(type);
    }

	protected DatabaseDriver() {
		init_table_map();
		configuration = new GameServerConf();
		ConfFileParser confFileParser = new ConfFileParser("gameServer.conf");
		configuration.setConfRecords(confFileParser.parse());

		userName = configuration.getDatabaseUsername();
		password = configuration.getDatabasePassword();
		serverName = configuration.getDatabaseHost();
		portNumber = configuration.getDatabasePort();
		databaseName = configuration.getDatabaseName();

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
			if (conn.isClosed()) {
				connect();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	// Creating a new player in the database
	public int createPlayer(String username, String password) {
		try {
			checkConnection();
			String selectSQL = "INSERT INTO " + PLAYERS + " (user_name, user_password) VALUES (?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			return getPlayerID(username);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	// Player authentication
	public int checkPlayerAuth(String username, String password) {
		int ret = -1;
		try {
			checkConnection();

			String selectSQL = "SELECT id FROM " + PLAYERS + " WHERE user_name=? AND user_password =? LIMIT 0,1";
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				ret = rs.getInt("id");
				break;
			}
			rs.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

	// Get player ID from username (on creating)
	public int getPlayerID(String username) {
		int ret = 0;
		try {
			checkConnection();
			String selectSQL = "SELECT id FROM " + PLAYERS + " WHERE user_name = ? LIMIT 0,1";
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				ret = rs.getInt("id");
				break;
			}
			rs.close();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public Player getPlayerByUsername(String name) {
		Player ret = null;
		try {
			checkConnection();
			String selectSQL = "SELECT * FROM " + PLAYERS + " WHERE user_name = ? LIMIT 0,1";
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, name);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				ret = new Player(rs.getString("user_name"), rs.getInt("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public Player getPlayerById(int id) {
		Player ret = null;
		try {
			checkConnection();
			String selectSQL = "SELECT * FROM " + PLAYERS + " WHERE id = ? LIMIT 0,1";
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				ret = new Player(rs.getString("user_name"), rs.getInt("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	// Get friend ids for player
	public List<Integer> getFriendIdsForPlayer(int playerId) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		try {
			checkConnection();

			String selectSQL = "(SELECT player_id as 'id' FROM " + FRIENDS_RELATIONSHIP
					+ " WHERE friend_id = ?) UNION ALL (SELECT friend_id as 'id' FROM " + FRIENDS_RELATIONSHIP
					+ " WHERE player_id = ?) GROUP BY id";
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setInt(1, playerId);
			preparedStatement.setInt(2, playerId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt("id"));
			}
			rs.close();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// Get friends for player
	public List<Player> getFriendsForPlayer(int playerId) {
		ArrayList<Player> list = new ArrayList<Player>();
		try {
			checkConnection();

			String selectSQL = "SELECT p.id,p.user_name FROM " + PLAYERS + " p LEFT JOIN " + FRIENDS_RELATIONSHIP
					+ " f1 ON (p.id = f1.player_id) LEFT JOIN " + FRIENDS_RELATIONSHIP
					+ " f2 ON (p.id = f2.friend_id) WHERE f1.friend_id = ? OR f2.player_id = ? GROUP BY p.id";
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setInt(1, playerId);
			preparedStatement.setInt(2, playerId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				list.add(new Player(rs.getString("p.user_name"), rs.getInt("p.id")));
			}
			rs.close();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int addFriends(int playerId, int friendId) {
		try {
			checkConnection();
			String selectSQL = "INSERT INTO " + FRIENDS_RELATIONSHIP + " (player_id,friend_id) VALUES(?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setInt(1, playerId);
			preparedStatement.setInt(2, friendId);
			int ret = preparedStatement.executeUpdate();
			preparedStatement.close();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// Create game
	public int createGame(int gameType, long timestamp, String mapName) {
		try {
			checkConnection();
			String selectSQL = "INSERT INTO " + GAMES + " (type,time_started,map_name) VALUES(?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setInt(1, gameType);
			preparedStatement.setLong(2, timestamp);
			preparedStatement.setString(2, mapName);
			int ret = preparedStatement.executeUpdate();
			preparedStatement.close();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int updateGame(Room room) {
		try {
			checkConnection();
			String selectSQL = "UPDATE " + GAMES + " SET type = ?, time_started = ?, map_name = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setInt(1, room.getType());
			preparedStatement.setLong(2, room.getTimeStarted());
			preparedStatement.setString(2, room.getMapName());
			int ret = preparedStatement.executeUpdate();
			preparedStatement.close();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<Player> getPlayersForGameId(int gameId) {
		ArrayList<Player> list = new ArrayList<Player>();
		try {
			checkConnection();
			String selectSQL = "SELECT p.id, p.username FROM " + PLAYERS + " p LEFT JOIN " + DD_GAME_RANKINGS
					+ " d ON (p.id = d.player_id) LEFT JOIN " + RR_GAME_RANKINGS
					+ " r ON (p.id = r.player_id) WHERE d.game_id = ? OR r.game_id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setInt(1, gameId);
			preparedStatement.setInt(2, gameId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				list.add(new Player(rs.getString("user_name"), rs.getInt("id")));
			}
			rs.close();
			preparedStatement.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Integer> getPlayerIdsForGameId(int gameId) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		try {
			checkConnection();
			String selectSQL = "(SELECT player_id FROM " + DD_GAME_RANKINGS
					+ " game_id = ?) UNION ALL (SELECT player_id FROM " + RR_GAME_RANKINGS
					+ " WHERE game_id = ?) GROUP BY player_id";
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setInt(1, gameId);
			preparedStatement.setInt(2, gameId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt("p.id"));
			}
			rs.close();
			preparedStatement.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Room getGameByName(String name) {
		Room ret = null;
		try {
			checkConnection();
			String selectSQL = "SELECT * FROM " + GAMES + " WHERE map_name = ? LIMIT 0,1";
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, name);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				ret = new Room(rs.getInt("id"), rs.getInt("type"), rs.getLong("time_started"),
						rs.getString("map_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public Room getGameById(int id) {
		Room ret = null;
		try {
			checkConnection();
			String selectSQL = "SELECT * FROM " + GAMES + " WHERE id = ? LIMIT 0,1";
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				ret = new Room(rs.getInt("id"), rs.getInt("type"), rs.getLong("time_started"),
						rs.getString("map_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public int updatePlayerDRank(int ranking, int gameId, int playerId) {
		int ret = 0;
		try {
			checkConnection();
			String selectSQL = "UPDATE " + DD_GAME_RANKINGS + " SET ranking = ? WHERE player_id = ? AND game_id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setInt(1, ranking);
			preparedStatement.setInt(2, playerId);
			preparedStatement.setInt(3, gameId);
			ret = preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public int updatePlayerRRank(int ranking, int gameId, int playerId) {
		int ret = 0;
		try {
			checkConnection();
			String selectSQL = "UPDATE " + RR_GAME_RANKINGS + " SET ranking = ? WHERE player_id = ? AND game_id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setInt(1, ranking);
			preparedStatement.setInt(2, playerId);
			preparedStatement.setInt(3, gameId);
			ret = preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public int addPlayerToDGame(int gameId, int playerId) {
		int ret = 0;
		try {
			checkConnection();
			String selectSQL = "INSERT INTO " + DD_GAME_RANKINGS + " (game_id,player_id) VALUES (?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setInt(3, playerId);
			preparedStatement.setInt(2, gameId);
			ret = preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public int addPlayerToRGame(int gameId, int playerId) {
		int ret = 0;
		try {
			checkConnection();
			String selectSQL = "INSERT INTO " + RR_GAME_RANKINGS + " (game_id,player_id) VALUES (?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setInt(3, playerId);
			preparedStatement.setInt(2, gameId);
			ret = preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	*/
	
	public BaseVehicle getCar(String type) {
		return null;
	}
        
	public static boolean update(Class<? extends ObjectModel> type, int id, String field, Object value) {
        int ret = 0;
        try {
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
	
	public static boolean update(Class<? extends ObjectModel> type, int id, HashMap<String,String> values) {
        int ret = 0;
        try {
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
	
	private static ArrayList<HashMap<String, String>> select(String requete) throws SQLException {
        
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
    
	private static ArrayList<HashMap<String, String>> select(Class<? extends ObjectModel> type, HashMap<String, String> data){
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
	
	public static ArrayList<ObjectModel> find(Class<? extends ObjectModel> type, HashMap<String, String> param){
        try {
            ArrayList<ObjectModel> results = new ArrayList<>();
            String table = table_map.get(type);
            
            ArrayList<HashMap<String, String>> raw_results = DatabaseDriver.select(type, param);
            
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
    public static ArrayList<ObjectModel> find(Class<? extends ObjectModel> type, String field, Object value)
    {
        HashMap<String, String> critere = new HashMap<>();
        critere.put(field, value.toString());
        return find(type,critere);
    }

	public static ObjectModel findById(Class<? extends ObjectModel> type, Object id) throws SQLException {
		ArrayList<ObjectModel> d = find(type,"id",id);
        if(!d.isEmpty())
        	return (ObjectModel) d.get(0);
        else throw new SQLException("No object of type "+type.getSimpleName()+"found for id "+id);
	}
	
	public static boolean alreadyExists(Class<? extends ObjectModel> type, Object id){
		try{
			findById(type, id);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
        
    //query of object creation, returns id given to the object by the DB
	public static int insert(ObjectModel obj){            
        try {
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
		public static int insert(Class<? extends ObjectModel> type, HashMap<String,String> data){            
	        try {
	            String table = DatabaseDriver.table_map.get(type.getClass());

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
	
}