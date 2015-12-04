package core;

import networking.response.GameResponse;
import networking.response.ResponseDead;
import networking.response.ResponsePrizes;
import networking.response.ResponseRenderCharacter;
import networking.response.ResponseSetPosition;
import networking.response.ResponseTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import dataAccessLayer.record.GameRoom;
import dataAccessLayer.record.Player;
import json.collections.MapManager;
import json.model.MapDetails;
import metadata.Constants;
import model.Position;

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
		System.out.println("session is created with name " + gameRoom.getRoomName());
	}

	@Override
	public void run() {
		System.out.println("Starting game thread loop");
		isRunning = true;
		long currentTime, gameRunTime, referTime, gameStartedTime, eliminateTime = 0;
		referTime = 1000L;
		gameStartedTime = gameroom.getTimeStarted();
		while (isRunning) { 	
			currentTime = System.currentTimeMillis();
			gameRunTime = currentTime - gameStartedTime;
			if (phase == 1) {
				// Start countdown
				if (gameroom.getTimeStarted() + Constants.COUNTDOWN_TIME - currentTime > 0
						&& gameRunTime - referTime >= Constants.SEND_TIME) {
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
					System.out.println("Game Clock");
					referTime += Constants.SEND_TIME;
					sendAllResponseTime(1, (int)(gameRunTime));
					if(mapDetails.getMode() == Constants.RR){
						eliminateTime -= Constants.SEND_TIME;
						System.out.println("elimination time : " + eliminateTime);
						sendAllResponseTime(2, (int)(eliminateTime));
						if(eliminateTime <= 0){
							if(doElimination()){
								eliminateTime = Constants.ELIMINATION_TIME;
							}else{
								endGame();
							}
						}
					} else {
						System.out.println("not an RR game");
					}
				}
			}

			//One player left
			if(playerRankings.size() - deadPlayerList.size() <=1) {
				endGame(); 
			}				

		}
		System.out.println("Game Over : GameId - " + getId());

		// Send out prizes

		//-- set currency
		deadPlayerList.add(getRankings().get(0));
		for (int i=0; i<deadPlayerList.size(); i++) {
			int currencyGained = 25 + (100 / (deadPlayerList.size() - i));

			for(GameClient client : clients) {
				if(client.getPlayer().getId() == deadPlayerList.get(i).getId()) {
					ResponsePrizes prize = new ResponsePrizes();
					prize.setPrize(currencyGained);
					client.getPlayer().setLastPrize(currencyGained);
					client.addResponseForUpdate(prize);					
					break;
				}
			}

			//send player currency

			int finalCurrency = deadPlayerList.get(i).getCurrency() + currencyGained;
			deadPlayerList.get(i).setCurrency(finalCurrency);
			deadPlayerList.get(i).save(Player.CURRENCY);
		}

		server.deleteSessionThreadOutOfActiveThreads(getId());
	}

	private boolean doElimination() {
		if(playerRankings.size() - deadPlayerList.size() <= 1){
			return false;
		}
		ArrayList<Player> ranking = (ArrayList<Player>) getRankings();
		System.out.println(ranking.toString());
		Player killThis = ranking.get(playerRankings.size()-deadPlayerList.size()-1);
		deadPlayerList.add(killThis);
		System.out.println("elminated " + killThis.getUsername());
		ResponseDead responseDead = new ResponseDead();
		responseDead.setUsername(killThis.getUsername());
		addResponseForAll(responseDead);
		return true;	
	}

	/**
	 * We only want to add clients to the session as long as we have a starting
	 * position for them
	 * 
	 * @param client
	 * @return boolean
	 */
	public int addGameClient(GameClient client) {
		for(GameClient _client : clients) {
			if(_client.getPlayer().getId() == client.getPlayer().getId()) {
				System.out.println("Client is already in room");
				return -1;
			}
		}
		clients.add(client);
		Position position = null;
		if (clients.size() < mapDetails.getMaxNumOfPlayers() && (position = availablePositions.remove(0)) != null) {
			startingPositions.put(client.getPlayer(), position);
			client.getPlayer().setPosition(position);
			playerRankings.put(client.getPlayer(), Double.valueOf(startingPositions.size()));
			System.out.println("Number of clients: " + getGameClients().size());
			return 1;
		}
		return 0;
	}

	public void clientDead(Player player) {
		deadPlayerList.add(player);
	}

	public void removeGameClient(GameClient client) {
		clients.remove(client);

		//Check if the player is alive
		for(Player player : deadPlayerList) {
			//Player is dead, so its okay
			if(player.getId() == client.getPlayer().getId()) {
				return;
			}
		}

		//Player is not dead - no points for him/her #Wendy
		playerRankings.remove(client.getPlayer());
		if (startingPositions.get(client.getPlayer()) != null) {
			availablePositions.add(startingPositions.get(client.getPlayer()));
			startingPositions.remove(client.getPlayer());
		}
	}

	public List<GameClient> getGameClients() {
		return clients;
	}

	public List<Player> getPlayers() {
		List<Player> players = new ArrayList<Player>();
		for (GameClient client : clients) {
			players.add(client.getPlayer());
		}
		return players;
	}

	public GameRoom getGameRoom() {
		return gameroom;
	}

	public void setGameroom(GameRoom gameroom) {
		this.gameroom = gameroom;
	}
	
	public int getMaxNumOfPlayers() {
		return mapDetails.getMaxNumOfPlayers();
	}
	
	public int getMinNumOfPlayers() {
		return mapDetails.getMinNumOfPlayers();
	}

	public GameServer getServer() {
		return server;
	}
	public void endGame(){
		isRunning = false;
	}
	public HashMap<Player,Position> getStartingPositions() {
		return startingPositions;
	}

	/**
	 * 0 = start countdown 1 = game time 2 = elimination countdown
	 * 
	 * @param type
	 * @param time
	 */
	public void sendAllResponseTime(int type, int time) {
		ResponseTime responseTime = new ResponseTime();
		responseTime.setData(type, time);
		addResponseForAll(responseTime);
	}

	public void nextPhase() {
		//send set_position response here
		//remember to edit all gameclients.player.position
		System.out.println("Entering next phase");
		switch(phase) {
		case 0:
			for(ResponseRenderCharacter responseRenderCharacter : getCharacterUpdates()){
				addResponseForAll(responseRenderCharacter);
			}
			System.out.println("Sending positions");
			ResponseSetPosition responseSetPosition = new ResponseSetPosition();
			responseSetPosition.setStartingPositions(startingPositions);
			addResponseForAll(responseSetPosition);
			availablePositions = new ArrayList<Position>();
			phase += 1;
			break;
		case 1:
			System.out.println("Starting game");
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

	public boolean getPowerups(int powerId) {
		long cur = System.currentTimeMillis();
		if (cur - powerups[powerId] >= Constants.RESPAWN_TIME) {
			powerups[powerId] = cur;
			return true;
		} else {
			return false;
		}
	}

	public boolean updatePlayerRanking(Player player, double rankValue) {
		playerRankings.put(player, rankValue);
		updateRankings = true;
		return true;
	}

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

	public void endThread() {
		if(clients.size() == 0){
			server.deleteSessionThreadOutOfActiveThreads(getId());
		}
	}

	public boolean isFull() {
		return availablePositions.size() == 0;
	}

	public MapDetails getMapDetails() {
		return mapDetails;
	}

}
