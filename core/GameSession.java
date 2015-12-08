package core;

import utility.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import controller.networking.response.GameResponse;
import controller.networking.response.ResponseDead;
import controller.networking.response.ResponseEnterQueue;
import controller.networking.response.ResponsePrizes;
import controller.networking.response.ResponseRenderCharacter;
import controller.networking.response.ResponseSetPosition;
import controller.networking.response.ResponseTime;
import driver.data.meta.Constants;
import driver.data.model.Position;
import driver.database.record.GameRoom;
import driver.database.record.Player;
import driver.database.record.Ranking;
import driver.json.collection.MapManager;
import driver.json.record.MapDetails;

public class GameSession extends Thread {
	private int phase = 0;
	private boolean updateRankings = true;
	private MapDetails mapDetails;
	private GameRoom gameroom;
	private GameServer server;
	private boolean isRunning;
	private long[] powerups;
	private List<GameClient> clients;
	private HashMap<Player, Double> playerRankings;
	private List<Player> sortedRankings = null;
	private HashMap<Player, Position> startingPositions;
	private List<Position> availablePositions;
	private List<Player> deadPlayerList;
	private List<ResponseRenderCharacter> renderCharacterResponses;

	/**
	 * Creates an instance of GameSession
	 * @param GameServer
	 * @param GameRoom
	 */
	public GameSession(GameServer server, GameRoom gameRoom) {
		this.gameroom = gameRoom;
		this.server = server;
		mapDetails = MapManager.getInstance().getMapDetails(gameroom.getMapName());
		availablePositions = mapDetails.getPositions();
		startingPositions = new HashMap<Player, Position>();
		deadPlayerList = new ArrayList<Player>();
		clients = new ArrayList<>();
		playerRankings = new HashMap<Player,Double>();
		renderCharacterResponses = new ArrayList<ResponseRenderCharacter>();
		Logger.logMessage("session is created with name " + gameRoom.getRoomName());
	}

	@Override
	public void run() {
		Logger.logMessage("Starting game thread loop");
		isRunning = true;
		long currentTime, gameRunTime, referTime, gameStartedTime, eliminateTime = 0;
		referTime = 0L;
		gameStartedTime = System.currentTimeMillis();
		while (isRunning) { 
			currentTime = System.currentTimeMillis();
			gameRunTime = currentTime - gameStartedTime;
			if (phase == 1) {
				// Start countdown
				if (gameStartedTime + Constants.COUNTDOWN_TIME - currentTime > 0 && (referTime == 0 || gameRunTime - referTime >= Constants.SEND_TIME)) {
					Logger.logMessage("countdown time : " + (int)(gameStartedTime + Constants.COUNTDOWN_TIME - currentTime));
					sendAllResponseTime(0, (int)(gameStartedTime + Constants.COUNTDOWN_TIME - currentTime));					
					referTime += Constants.SEND_TIME;
				} else if (gameStartedTime + Constants.COUNTDOWN_TIME - currentTime <= 0) {
					phase += 1;
					referTime += Constants.SEND_TIME;
					eliminateTime = Constants.PEACE_TIME + Constants.ELIMINATION_TIME;
				}
			} else {
				// Start game timer
				// send responseTime approximately every 1000 milliseconds
				if (gameRunTime - referTime >= Constants.SEND_TIME) {
					referTime += Constants.SEND_TIME;
					sendAllResponseTime(1, (int)(gameRunTime));
					if(mapDetails.getMode() == Constants.RR){
						eliminateTime -= Constants.SEND_TIME;
						Logger.logMessage("elimination time : " + eliminateTime);
						sendAllResponseTime(2, (int)(eliminateTime));
						if(eliminateTime <= 0){
							if(doElimination()){
								eliminateTime = Constants.ELIMINATION_TIME;
							}else{
								endGame();
							}
						}
					} 
				}
			}

			//One player left
			if(playerRankings.size() - deadPlayerList.size() <=1) {
				endGame(); 
			}				

		}
		Logger.logMessage("Game Over : GameId - " + getId());

		// Send out prizes

		//-- set currency and save rankings
		deadPlayerList.add(getRankings().get(0));
		for (int i=0; i<deadPlayerList.size(); i++) {
			int currencyGained = 25 + (100 / (deadPlayerList.size() - i));

			for(GameClient client : clients) {
				if(client.getPlayer().getId() == deadPlayerList.get(i).getId()) {
					ResponsePrizes prize = new ResponsePrizes();
					Player player = client.getPlayer();
					
					//Send Prize
					prize.setPrize(currencyGained);
					client.addResponseForUpdate(prize);	
					
					//Update Player Currency
					player.setLastPrize(currencyGained);
					player.setCurrency(player.getCurrency()+currencyGained);
					player.save(Player.CURRENCY);
					
					//Save Ranking
					Ranking ranking = new Ranking();
					ranking.setGameId(gameroom.getId());
					ranking.setPlayerId(player.getId());
					ranking.setRanking(i+1);
					ranking.save("all");					
					break;
				}
			}			
		}

		//Commented out for testing purpose
//		for(GameClient client : clients) {
//			client.setSession(null);
//		}
//		
//		clients.clear();
//		availablePositions.clear();
//		playerRankings.clear();
		server.deleteSessionThreadOutOfActiveThreads(getId());
	}

	/**
	 * Checks if it is time to do elimination and handles elimination
	 * @return boolean
	 */
	private boolean doElimination() {
		if(playerRankings.size() - deadPlayerList.size() <= 1){
			return false;
		}
		List<Player> ranking = getRankings();
		Player killThis = ranking.get(playerRankings.size()-deadPlayerList.size()-1);
		deadPlayerList.add(killThis);
		Logger.logMessage("elminated " + killThis.getUsername());
		ResponseDead responseDead = new ResponseDead();
		responseDead.setUsername(killThis.getUsername());
		addResponseForAll(responseDead);
		return true;	
	}

	/**
	 * We only want to add clients to the session as long as we have space for them
	 * 
	 * @param GameClient
	 * @return boolean
	 */
	public int addGameClient(GameClient client) {
		for(GameClient _client : clients) {
			if(_client.getPlayer().getId() == client.getPlayer().getId()) {
				Logger.logMessage("Client is already in room");
				return -1;
			}
		}
		
		Position position = null;
		if (phase == 0 && availablePositions.size() > 0 && clients.size() < mapDetails.getMaxNumOfPlayers() && (position = availablePositions.remove(0)) != null) {
			clients.add(client);
			startingPositions.put(client.getPlayer(), position);
			client.getPlayer().setPosition(position);
			playerRankings.put(client.getPlayer(), Double.valueOf(startingPositions.size()));
			Logger.logMessage("Number of clients: " + getGameClients().size());
			return 1;
		}
		return 0;
	}

	/**
	 * Add player into the dead player list
	 * @param Player
	 */
	public void clientDead(Player player) {
		deadPlayerList.add(player);
	}

	/**
	 * Remove GameClient from session
	 * @param GameClient
	 */
	public void removeGameClient(GameClient client) {
		clients.remove(client);

		//Check if the player is alive
		boolean isAlive = true;
		for(Player player : deadPlayerList) {
			//Player is dead, so its okay
			if(player.getId() == client.getPlayer().getId()) {
				isAlive = false;
				break;
			}
		}

		//Player is not dead - no points for him/her #Wendy
		if(isAlive) {
			playerRankings.remove(client.getPlayer());
			if (startingPositions.get(client.getPlayer()) != null) {
				availablePositions.add(startingPositions.get(client.getPlayer()));
				startingPositions.remove(client.getPlayer());
			}
		}
		
		if(phase == 0) {
			ResponseEnterQueue response = new ResponseEnterQueue();
			response.setLobbySize(getMaxNumOfPlayers());
			response.setMinSize(getMinNumOfPlayers());
			response.setPlayers(getPlayers());			
			addResponseForAll(response);
		}
		
		if(clients.size() == 0) {
			server.deleteSessionThreadOutOfActiveThreads(getId());
		}
	}

	/**
	 * Returns the list of GameClients
	 * @return List<GameClient>
	 */
	public List<GameClient> getGameClients() {
		return clients;
	}

	/**
	 * Returns the list of Players
	 * @return List<Player>
	 */
	public List<Player> getPlayers() {
		List<Player> players = new ArrayList<Player>();
		for (GameClient client : clients) {
			players.add(client.getPlayer());
		}
		return players;
	}
	
	/**
	 * Checks to see if the client is on the loading screen
	 * @param GameClient
	 * @return boolean
	 */
	public boolean isClientLoading(GameClient client) {
		return client.getPlayer().isLobbyReady() && client.getPlayer().isReady() && phase == 1;
	}

	/**
	 * Returns the game room
	 * @return GameRoom
	 */
	public GameRoom getGameRoom() {
		return gameroom;
	}

	/**
	 * Set the game room
	 * @param GameRoom
	 */
	public void setGameroom(GameRoom gameroom) {
		this.gameroom = gameroom;
	}
	
	/**
	 * Returns the max number of players
	 * @return int
	 */
	public int getMaxNumOfPlayers() {
		return mapDetails.getMaxNumOfPlayers();
	}
	
	/**
	 * Returns the min number of players
	 * @return int
	 */
	public int getMinNumOfPlayers() {
		return mapDetails.getMinNumOfPlayers();
	}

	/**
	 * Causes the thread loop to end
	 */
	public void endGame(){
		isRunning = false;
	}
	
	/**
	 * returns the list of players starting positions
	 * @return HashMap<Player,Position>
	 */
	public HashMap<Player,Position> getStartingPositions() {
		HashMap<Player,Position> ret = new HashMap<>(startingPositions.size());
		for(Player key : startingPositions.keySet()) {
			Position pos = startingPositions.get(key);
			Position value = new Position(pos.getX(),pos.getY(),pos.getZ(),pos.getH(),pos.getP(),pos.getR(),pos.getSteering(),pos.getWheelforce(),pos.getBrakeforce());
			ret.put(key, value);
		}
		return ret;
	}

	/**
	 * 0 = start countdown 1 = game time 2 = elimination countdown
	 * 
	 * @param int
	 * @param int
	 */
	public void sendAllResponseTime(int type, int time) {
		ResponseTime responseTime = new ResponseTime();
		responseTime.setData(type, time);
		addResponseForAll(responseTime);
	}

	/**
	 * Enters the next phase of the game
	 */
	public void nextPhase() {
		//send set_position response here
		//remember to edit all gameclients.player.position
		Logger.logMessage("Entering next phase");
		switch(phase) {
		case 0:
			for(ResponseRenderCharacter responseRenderCharacter : getCharacterUpdates()){
				addResponseForAll(responseRenderCharacter);
			}
			Logger.logMessage("Sending positions");
			ResponseSetPosition responseSetPosition = new ResponseSetPosition();
			responseSetPosition.setStartingPositions(startingPositions);
			addResponseForAll(responseSetPosition);
			availablePositions = new ArrayList<Position>();
			phase += 1;
			break;
		case 1:
			Logger.logMessage("Starting game");
			gameroom.setTimeStarted(new Date());				
			initPowerUp(gameroom.getTimeStarted());
			gameroom.save(GameRoom.TIME_STARTED);
			this.start();
			break;
		}
	}

	private void initPowerUp(long l) {
		this.powerups = new long[Constants.NUMBER_OF_POWERUPS];
		for (int i = 0; i < Constants.NUMBER_OF_POWERUPS; i++) {
			powerups[i] = l - Constants.RESPAWN_TIME;
		}
	}
	
	/**
	 * 
	 * @param int
	 * @return boolean
	 */
	public boolean getPowerups(int powerId) {
		long cur = System.currentTimeMillis();
		if (cur - powerups[powerId] >= Constants.RESPAWN_TIME) {
			powerups[powerId] = cur;
			return true;
		} else {
			return false;
		}
	}

	public boolean updatePlayerRanking(Player player, double pointValue) {
		playerRankings.put(player, pointValue);
		updateRankings = true;
		return true;
	}

	/**
	 * Returns the list of player rankings
	 * @return List<Player>
	 */
	public List<Player> getRankings() {
		if(sortedRankings == null || updateRankings) {
			updateRankings = false;
			sortedRankings = new ArrayList<Player>();
			HashMap<Player, Double> que = new HashMap<Player, Double>(playerRankings.size());
			for (Player player : playerRankings.keySet())
				que.put(player, playerRankings.get(player));

			while (que.size() > 0) {
				Player maxPlayer = null;
				for (Player player : que.keySet()) {
					if (maxPlayer == null || que.get(player) > que.get(maxPlayer)) {
						maxPlayer = player;
					}
				}
				sortedRankings.add(maxPlayer);
				que.remove(maxPlayer);
			}
		}
		return sortedRankings;
	}
	
	public void addResponseForAll(GameClient gameClient, GameResponse response) {
		for (GameClient client : clients) {
			if (client.getId() != gameClient.getId()) {
				client.addResponseForUpdate(response);
			}
		}
	}
	
	public void addResponseForAll(Player player, GameResponse response) {
		for (GameClient client : clients) {
			if (client.getPlayer().getId() != player.getId()) {
				client.addResponseForUpdate(response);
			}
		}
	}

	public void addResponseForAll(int player_id, GameResponse response) {
		for (GameClient client : clients) {
			if (client.getPlayer().getId() != player_id) {
				client.addResponseForUpdate(response);
			}
		}
	}

	public void addResponseForAll(GameResponse response) {
		for (GameClient client : clients) {
			client.addResponseForUpdate(response);
		}
	}

	public List<ResponseRenderCharacter> getCharacterUpdates(){
		return this.renderCharacterResponses;
	}

	//add ResponseRenderCharacter for all other clients in the session and add this response to renderCharacterResponses
	public boolean addResponseForRenderCharacters(GameClient gclient) {
		ResponseRenderCharacter responseRenderCharacter = new ResponseRenderCharacter();
		responseRenderCharacter.setUsername(gclient.getPlayer().getUsername());
		//input correct carPaint, carTires, and carType below
		responseRenderCharacter.setCarPaint(gclient.getPlayer().getCarPaint());
		responseRenderCharacter.setCarTires(gclient.getPlayer().getCarTire());
		responseRenderCharacter.setCarType(gclient.getPlayer().getBaseCarId());
		return renderCharacterResponses.add(responseRenderCharacter);
	}

	public boolean removeResponseForCharacters(String username){
		for(ResponseRenderCharacter response : renderCharacterResponses){
			if(response.getUsername().equals(username)){
				return renderCharacterResponses.remove(response);
			}
		}
		return false;
	}

	/**
	 * Returns if the room is full
	 * @return boolean
	 */
	public boolean isFull() {
		return availablePositions.size() == 0;
	}

	/**
	 * Returns the MapDetails object
	 * @return MapDetails
	 */
	public MapDetails getMapDetails() {
		return mapDetails;
	}
}
