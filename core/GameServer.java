package core;

// Java Imports
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Custom Imports
import configuration.GameServerConf;
import metadata.GameRequestTable;
import dataAccessLayer.GameRoom;
import dataAccessLayer.Player;
import dataAccessLayer.DatabaseDriver;
import networking.response.GameResponse;
import utility.ConfFileParser;

/**
 * The GameServer class serves as the main module that runs the server.
 * Incoming connection requests are established and redirected to be managed
 * by another class called the GameClient. Several specialized methods are also
 * stored here to perform other specific needs.
 */
public class GameServer {
	private static GameServer gameServer; // References GameServer instance
	private GameServerConf configuration; // Stores server config. variables
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
		configuration = new GameServerConf();

		// Initialize the table with request codes and classes for static retrieval
		GameRequestTable.init();
	}


	/**
	 * Configure the game server by reading values from the configuration file.
	 */
	private void configure() {
		ConfFileParser confFileParser = new ConfFileParser("gameServer.conf");
		configuration.setConfRecords(confFileParser.parse());
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
	 * @return the ready status
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
		int serverPort = configuration.getPortNumber();

		try {
			// Start to listen on the given port for incoming connections
			listenSocket = new ServerSocket(serverPort);
			System.out.println("Server has started on port: " + listenSocket.getLocalPort());
			System.out.println("Waiting for clients...");
			// Loop indefinitely to establish multiple connections
			while (true) {
				try {
					// A client socket will represent a connection between the client and this server
					Socket clientSocket = listenSocket.accept();
					System.out.println("A Connection Established!");

					// Create a thread to represent a client that holds the client socket
					GameClient client = new GameClient(clientSocket, this);
					addToActiveThreads(client);
					// Run the thread
					client.start();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Get the GameClient thread for the player using the player ID.
	 *
	 * @param playerID holds the player ID
	 * @return the GameClient thread
	 */
	/*public GameClient getThreadByPlayerID(int playerID) {
        for (GameClient aClient : activeThreads.values()) {
            if (aClient.getPlayer().getID() == playerID) {
                return aClient;
            }
        }

        return null;
    }*/
	public GameSession getGameSessionByRoomId(int roomId){
		for(GameSession gsession : activeSessions.values()){
			if(gsession.getGameroom().getId() == roomId){
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

	public void addToActiveThreads(GameClient client) {
		activeThreads.put(client.getId(), client);
	}
	
	public void addToActiveSessions(GameSession gamesession){
		if(!gamesession.isAlive()) {
			gamesession.start();
		}
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
			if (client.getPlayer().getRoom() != null && client.getPlayer().getRoom().getId() == room_id) {
				list.add(client);
			}
		}

		return list;
	}
	
	public List<Player> getPlayersForRoom(int room_id) {
		List<Player> list = new ArrayList<Player>();
		for (GameClient client : activeThreads.values()) {
			if (client.getPlayer().getRoom() != null && client.getPlayer().getRoom().getId() == room_id) {
				list.add(client.getPlayer());
			}
		}

		return list;
	}

	public List<Player> getActivePlayers() {
		System.out.println(activePlayers.values());
		return new ArrayList<Player>(activePlayers.values());
	}

	public Player getActivePlayer(int player_id) {
		return activePlayers.get(player_id);
	}

	public void setActivePlayer(Player player) {
		activePlayers.put(player.getID(), player);
	}

	public void removeActivePlayer(int player_id) {
		activePlayers.remove(player_id);
	}

	public void deletePlayerThreadOutOfActiveThreads(Long threadID) {
		activeThreads.remove(threadID);
	}
	
	public void deleteSessionThreadOutOfActiveThreads(Long threadID) {
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
            System.out.println("In addResponseForUser--client is null");
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
			System.out.println("In addResponseForUser--client is null");
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
			if (client.getId() != player_id) {
				client.addResponseForUpdate(response);
			}
		}
	}

	public void addResponseForRoom(int room_id, GameResponse response) {    	 
		for (GameClient client : getGameClientsForRoom(room_id)) {
			client.addResponseForUpdate(response);
		}
	}
	
	public void addResponseForRoomExcludingPlayer(int room_id, int player_id, GameResponse response) {    	 
		for (GameClient client : getGameClientsForRoom(room_id)) {
			if(client.getPlayer().getID() != player_id)
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
