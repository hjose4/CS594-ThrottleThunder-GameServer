package core;

// Java Imports
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import controller.networking.response.GameResponse;
import driver.database.DatabaseDriver;
import driver.database.model.GameRoomModel;
import driver.database.record.GameRoom;
import driver.database.record.Player;
import driver.json.collection.MapManager;
import driver.json.record.MapDetails;
import routing.GameRequestTable;
import utility.JsonFileParser;
import utility.Logger;

/**
 * The GameServer class serves as the main module that runs the server.
 * Incoming connection requests are established and redirected to be managed
 * by another class called the GameClient. Several specialized methods are also
 * stored here to perform other specific needs.
 */
public class GameServer {
	private static GameServer gameServer; // References GameServer instance
	private boolean ready = false; // Used to keep server looping
	private HashMap<Long, GameClient> activeThreads = new HashMap<Long, GameClient>(); // Stores active threads by thread ID
	private HashMap<Integer, Player> activePlayers = new HashMap<Integer, Player>(); // Stores active players by player ID
	private HashMap<Long, GameSession> activeSessions = new HashMap<Long, GameSession>();
	
	/**
	 * Initialize the GameServer by setting up the request types and creating a
	 * connection with the database.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public GameServer() throws ClassNotFoundException, SQLException {
		// Initialize the table with request codes and classes for static retrieval
		GameRequestTable.init();
	}


	/**
	 * Configure the game server by reading values from the configuration file.
	 */
	private void configure() {
		JsonFileParser.parseFile("config.json");
	}


	/**
	 * Search for any other possible configuration variables and mark the server
	 * ready to start.
	 */
	private void getReady() {
		configure();
		ready = true;
	}

	/**
	 * Check whether the server is prepared to run.
	 * 
	 * @return boolean
	 */
	private boolean isReady() {
		return ready;
	}

	/**
	 * Run the game server by waiting for incoming connection requests.
	 * Establishes each connection and stores it into a GameClient thread to
	 * manage incoming and outgoing activity.
	 */
	private void run() {
		DatabaseDriver.init();
		ServerSocket listenSocket;
		int serverPort = Integer.valueOf(JsonFileParser.getServerConfig().get("port"));

		try {
			// Start to listen on the given port for incoming connections
			listenSocket = new ServerSocket(serverPort);
			Logger.logMessage("Server has started on port: " + listenSocket.getLocalPort());
			Logger.logMessage("Waiting for clients...");
			// Loop indefinitely to establish multiple connections
			while (true) {
				try {
					// A client socket will represent a connection between the client and this server
					Socket clientSocket = listenSocket.accept();
					Logger.logMessage("A Connection Established!");

					// Create a thread to represent a client that holds the client socket
					GameClient client = new GameClient(clientSocket, this);
					addToActiveThreads(client);
					// Run the thread
					client.start();
				} catch (IOException e) {
					Logger.logMessage(e.getMessage());
				}
			}
		} catch (IOException e) {
			Logger.logMessage(e.getMessage());
		} catch (Exception e) {
			Logger.logMessage(e.getMessage());
		}
	}

	/**
	 * Get GameSession based on room type
	 *
	 * @param int
	 * @return GameSession
	 */
	public GameSession getGameSessionByRoomType(int roomType){
		for(GameSession gsession : activeSessions.values()){
			if(gsession.getGameRoom().getType() == roomType && !gsession.isFull()){
				return gsession;
			}
		}
		
		return createNewGameSession(roomType);
	}
	
	/**
	 * Creates a new GameSession for the given room type
	 * @param roomType
	 * @return GameSession
	 */
	public GameSession createNewGameSession(int roomType) {
		GameRoom room = new GameRoom();
		MapDetails map = MapManager.getInstance().getMapDetails(roomType);
		if(map != null) {
			room.setType(roomType);
			room.setTimeStarted(new Date());
			room.setMapName(map.getName());
			room.setRoomName(map.getName());
			room.setStatus(0);
		} else {
			Logger.logMessage("Cannot find map " + roomType);
			room.setType(roomType);
			room.setTimeStarted(new Date());
			room.setMapName("");
			room.setRoomName("");
			room.setStatus(0);
		}
		
		GameRoomModel.insertGameRoom(room);
		
		GameSession session = new GameSession(this,room);
		addToActiveSessions(session);
		return session;
	}
	public GameSession getGameSessionByRoomName(String roomName){
		for(GameSession gsession : activeSessions.values()){
			if(gsession.getGameRoom().getRoomName().equalsIgnoreCase(roomName)){
				return gsession;
			}
		}
		return null;
	}
	/**
	 * Get the GameClient thread for the player using the username.
	 *
	 * @param username holds the username
	 * @return the GameClient thread
	 */
	public GameClient getThreadByPlayerUserName(String userName) {
		for (GameClient aClient : activeThreads.values()) {
			if(aClient.getPlayer() == null)
			{
				continue;
			}
			if (aClient.getPlayer().getUsername().equals(userName)) {
				return aClient;
			}
		}

		return null;
	}

	public int getNumberOfCurrentThreads() {
		return activeThreads.size();
	}
	
	public GameRoom getGameRoomFromSessionsById(int room_id) {
		for(GameSession session : activeSessions.values()) {
			if(session.getGameRoom().getId() == room_id) {
				return session.getGameRoom();
			}
		}
		return null;
	}
	
	public GameRoom getGameRoomFromSessionsByName(String room_name) {
		for(GameSession session : activeSessions.values()) {
			if(session.getGameRoom().getRoomName().equalsIgnoreCase(room_name)) {
				return session.getGameRoom();
			}
		}
		return null;
	}

	public void addToActiveThreads(GameClient client) {
		activeThreads.put(client.getId(), client);
	}
	
	public void addToActiveSessions(GameSession gamesession){
		Logger.logMessage("gamesessions added " + gamesession.getGameRoom().getRoomName());
		activeSessions.put(gamesession.getId(), gamesession);
	}
	
	public void createActiveSession(GameRoom gameRoom) {
		GameSession session = new GameSession(this,gameRoom);
		addToActiveSessions(session);
	}
	
	public HashMap<Long, GameClient> getActiveThreads()
	{
		return this.activeThreads;
	}
	
	public HashMap<Long, GameSession> getActiveSessions(){
		return this.activeSessions;
	}

	public List<GameClient> getGameClientsForRoom(int room_id) {
		List<GameClient> list = new ArrayList<GameClient>();
		for (GameClient client : activeThreads.values()) {
			if (client.getSession() != null && client.getSession().getGameRoom().getId() == room_id) {
				list.add(client);
			}
		}

		return list;
	}
	
	public List<Player> getPlayersForRoom(int room_id) {
		List<Player> list = new ArrayList<Player>();
		for (GameClient client : activeThreads.values()) {
			if (client.getSession() != null && client.getSession().getGameRoom().getId() == room_id) {
				list.add(client.getPlayer());
			}
		}

		return list;
	}

	public List<Player> getActivePlayers() {
		Logger.logMessage(activePlayers.values());
		return new ArrayList<Player>(activePlayers.values());
	}

	public Player getActivePlayer(int player_id) {
		return activePlayers.get(player_id);
	}

	public void setActivePlayer(Player player) {
		activePlayers.put(player.getId(), player);
	}

	public void removeActivePlayer(int player_id) {
		activePlayers.remove(player_id);
	}

	public void deletePlayerThreadOutOfActiveThreads(Long threadID) {
		activeThreads.remove(threadID);
	}
	
	public void deleteSessionThreadOutOfActiveThreads(Long threadID) {
		Logger.logMessage("Session is deleted with ID " + threadID);
		activeSessions.remove(threadID);
	}
	
	/**
	 * Push a pending response to a user's queue.
	 * 
	 * @param player_id holds the player ID
	 * @param response is the instance containing the response information
	 */
	/*public void addResponseForUser(int player_id, GameResponse response) {
        GameClient client = getThreadByPlayerID(player_id);

        if (client != null) {
            client.addResponseForUpdate(response);
        } else {
            Logger.logMessage("In addResponseForUser--client is null");
        }
    }*/

	/**
	 * Push a pending response to a user's queue.
	 *
	 * @param username holds the username
	 * @param response is the instance containing the response information
	 */
	public void addResponseForUser(String username, GameResponse response) {
		GameClient client = getThreadByPlayerUserName(username);

		if (client != null) {
			client.addResponseForUpdate(response);
		} else {
			Logger.logMessage("In addResponseForUser--client is null");
		}
	}

	/**
	 * Push a pending response to all users' queue except one user.
	 * 
	 * @param player_id holds the excluding player ID
	 * @param response is the instance containing the response information
	 */
	public void addResponseForAllOnlinePlayers(long player_id, GameResponse response) {

		for (GameClient client : activeThreads.values()) {
			if (client.getPlayer().getId() != player_id) {
				client.addResponseForUpdate(response);
			}
		}
	}
	
	/**
	 * Push a pending response to all users' queue
	 * 
	 * @param response is the instance containing the response information
	 */
	public void addResponseForAllOnlinePlayers(GameResponse response) {

		for (GameClient client : activeThreads.values()) {
			client.addResponseForUpdate(response);
		}
	}

	public void addResponseForRoom(int room_id, GameResponse response) {    	 
		for (GameClient client : getGameClientsForRoom(room_id)) {
			client.addResponseForUpdate(response);
		}
	}
	
	public void addResponseForRoomExcludingPlayer(int room_id, int player_id, GameResponse response) {    	 
		for (GameClient client : getGameClientsForRoom(room_id)) {
			if(client.getPlayer().getId() != player_id)
				client.addResponseForUpdate(response);
		}
	}

	public static GameServer getInstance() {
		return gameServer;
	}

	public static void main(String args[]) throws SQLException, ClassNotFoundException {
		gameServer = new GameServer();

		gameServer.getReady();

		if (gameServer.isReady()) {
			gameServer.run();
		}
	}
}
