package core;

// Java Imports
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import controller.networking.request.GameRequest;
import controller.networking.request.RequestLogout;
import controller.networking.response.GameResponse;
import driver.data.meta.Constants;
import driver.database.record.Player;
import routing.GameRequestTable;
import utility.DataReader;
import utility.Logger;

/**
 * The GameClient class is an extension of the Thread class that represents an
 * individual client. Not only does this class holds the connection between the
 * client and server, it is also in charge of managing the connection to
 * actively receive incoming requests and send outgoing responses. This thread
 * lasts as long as the connection is alive.
 */
public class GameClient extends Thread {

	private GameServer server; // References GameServer instance
	private Socket mySocket; // Socket being used for this client
	private InputStream inputStream;
	private OutputStream outputStream; // For use with outgoing responses
	private DataInputStream dataInputStream; // For use with incoming requests
	private DataInputStream dataInput;
	private boolean isPlaying;
	private Queue<GameResponse> updates; // Temporarily store responses for client
	private Player player; 
	private GameSession session;

	/**
	 * Initialize the GameClient using the client socket and creating both input
	 * and output streams.
	 * 
	 * @param Socket
	 *            holds reference of the socket being used
	 * @param GameServer
	 *            holds reference to the server instance
	 * @throws IOException
	 */
	public GameClient(Socket clientSocket, GameServer server) throws IOException {
		mySocket = clientSocket;
		this.server = server;
		updates = new LinkedList<GameResponse>();

		inputStream = mySocket.getInputStream();
		outputStream = mySocket.getOutputStream();
		dataInputStream = new DataInputStream(inputStream);
		player = null;
	}

	/**
	 * Holds the main loop that processes incoming requests by first identifying
	 * its type, then interpret the following data in each determined request
	 * class. Queued up responses created from each request class will be sent
	 * after the request is finished processing.
	 * 
	 * The loop exits whenever the isPlaying flag is set to false. One of these
	 * occurrences is triggered by a timeout. A timeout occurs whenever no
	 * activity is picked up from the client such as being disconnected.
	 */
	@Override
	public void run() {
		isPlaying = true;
		long lastActivity = System.currentTimeMillis();
		short requestCode = -1;

		while (isPlaying) {
			try {
				// Extract the size of the package from the data stream
				short requestLength = DataReader.readShort(dataInputStream);
				if (requestLength > 0) {
					lastActivity = System.currentTimeMillis();
					// Separate the remaining package from the data stream
					byte[] buffer = new byte[requestLength];
					inputStream.read(buffer, 0, requestLength);
					dataInput = new DataInputStream(new ByteArrayInputStream(buffer));
					// Extract the request code number
					requestCode = DataReader.readShort(dataInput);
					if(requestCode != 301 && requestCode != 107 && requestCode != 113 && requestCode != 133 && requestCode != 123) {
						Logger.logMessage("Requesting : " +requestCode);
					}
					// Preventing response to be sent if user not authenticated
					if (requestCode == Constants.CMSG_LOGIN ||requestCode == Constants.CMSG_REGISTER || player != null) {  
						// Determine the type of request
						GameRequest request = GameRequestTable.get(requestCode);
						// If the request exists, process like following:
						if (request != null) {
							request.setGameClient(this);
							// Pass input stream to the request object
							request.setDataInputStream(dataInput);
							// Parse the input stream
							request.parse();
							// Interpret the data
							request.doBusiness();
							// Retrieve any responses created by the request
							// object
							for (GameResponse response : request.getResponses()) {
								// Transform the response into bytes and pass it
								// into the output stream	
								outputStream.write(response.constructResponseInBytes());
							}
						}
					}
				} else {
					// If there was no activity for the last moments, exit loop
					if ((System.currentTimeMillis() - lastActivity) / 1000 >= Constants.TIMEOUT_SECONDS || (getSession() != null && getSession().isClientLoading(this) && (System.currentTimeMillis() - player.lastReady) / 1000 >= Constants.LOADING_TIMEOUT_SECONDS )) {
						isPlaying = false;
					}
				}
			} catch (Exception e) {
				System.err.println("Request [" + requestCode + "] Error:");
				System.err.println(e.getMessage());
				System.err.println("---");
				e.printStackTrace();
			}

		}

		Logger.logMessage(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()));
		Logger.logMessage("The client stops playing.");
		if(player != null) {
			RequestLogout request = new RequestLogout();
			request.clientCrashed(this);
		}

		// Remove this GameClient from the server
		server.deletePlayerThreadOutOfActiveThreads(getId());
	}
	
	/**
	 * Stops the GameClient from continuing its loop
	 */
	public void stopClient() {
		isPlaying = false;
	}

	/**
	 * Returns the connected GameServer
	 * @return GameServer
	 */
	public GameServer getServer() {
		return server;
	}

	/**
	 * Returns the connected GameSession
	 * @return GameSession
	 */
	public GameSession getSession() {
		return session;
	}
	
	/**
	 * Connects the GameClient to a GameSession
	 * @param GameSession
	 */
	public void setSession(GameSession session) {
		if(session == null){
			this.session = null;
			return;
		}
		int status = session.addGameClient(this);
		if(status == 1) {
			this.session = session;
		} else if(status == 0) {
			//The session is full
			this.session = null;
			Logger.logMessage("There are no more positions open");
		}
	}

	/**
	 * Returns the Player object for the client connection
	 * @return Player
	 */
	public Player getPlayer() { return player; }

	/**
	 * Sets the Player object for the client connection
	 * @param Player
	 * @return
	 */
	public Player setPlayer(Player player) { return this.player = player; }

	/**
	 * Queue a response for the next heartbeat
	 * @param GameResponse
	 * @return boolean
	 */
	public boolean addResponseForUpdate(GameResponse response) {
		return updates.add(response);
	}

	/**
	 * Get all pending responses for this client.
	 * 
	 * @return Queue<GameResponse>
	 */
	public Queue<GameResponse> getUpdates() {
		Queue<GameResponse> responseList = null;

		synchronized (this) {
			responseList = updates;
			updates = new LinkedList<GameResponse>();
		}

		return responseList;
	}

	/**
	 * Returns the socket output stream
	 * @return OutputStream
	 */
	public OutputStream getOutputStream() {
		return outputStream;
	}

	/**
	 * Remove all responses for this client.
	 */
	public void clearUpdateBuffer() {
		updates.clear();
	}

	/**
	 * Returns the client's IP address
	 * @see java.net.InetAddress.getHostAddress()
	 * @return String
	 */
	public String getIP() {
		return mySocket.getInetAddress().getHostAddress();
	}

	@Override
	public String toString() {
		String str = "";

		str += "-----" + "\n";
		str += getClass().getName() + "\n";
		str += "\n";

		for (Field field : getClass().getDeclaredFields()) {
			try {
				str += field.getName() + " - " + field.get(this) + "\n";
			} catch (Exception ex) {
				Logger.logMessage(ex.getMessage());
			}
		}

		str += "-----";

		return str;
	}
}
