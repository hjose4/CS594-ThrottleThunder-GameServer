package core;

import networking.response.GameResponse;
import networking.response.ResponseReady;
import networking.response.ResponseTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataAccessLayer.record.GameRoom;
import dataAccessLayer.record.Player;
import metadata.Constants;
import model.MapManager;
import model.Position;

public class GameSession extends Thread{
	private int phase = 0;
	private GameRoom gameroom;
	private GameServer server;
	private boolean isRunning;
	private boolean gameStarted;
	private long[] powerups;
	private ArrayList<GameClient> clients;
	private HashMap<Player,Double> playerRankings;
	private HashMap<Player,Position> startingPositions;
	private ArrayList<Position> availablePositions;
	
	public GameSession(GameServer server, GameRoom gameRoom){
		this.gameroom = gameRoom;
		this.server = server;
		availablePositions = MapManager.getInstance().getStartingPositions(gameroom.getMapName());
		startingPositions = new HashMap<Player,Position>();
	}
	
	@Override
	public void run() {
		isRunning = true;
		gameStarted = false;
		long currentTime, gameRunTime, referTime, gameStartedTime;
		referTime = 0L;
		gameStartedTime = gameroom.getTimeStarted();
		while(isRunning){
			if(gameStarted){
				currentTime = System.currentTimeMillis();
				gameRunTime = currentTime - gameStartedTime;
				if(phase == 1) {
					//Start countdown
					if( gameroom.getTimeStarted()+5000 - currentTime > 0 && gameRunTime - referTime >= Constants.SEND_TIME) {
						sendAllResponseTime(0, gameStartedTime+5000-currentTime);
						referTime += Constants.SEND_TIME;
					} else if(gameStartedTime+5000 - currentTime <= 0) {
						phase += 1;
						referTime += Constants.SEND_TIME;
					}
				} else {
					//Start game timer
					//send responseTime approximately every 250 milliseconds
					if(gameRunTime - referTime >= Constants.SEND_TIME){
						sendAllResponseTime(1, gameRunTime);
						
					}
					
					//Start elimination countdown - only for RR
				}				
			}
		}
		System.out.println("Game Over : GameId - " + getId());
		//Send out prizes
		server.deleteSessionThreadOutOfActiveThreads(getId());
	}

	/**
	 * We only want to add clients to the session as long as we have a starting position for them
	 * @param client
	 * @return boolean
	 */
	public boolean addGameClient(GameClient client) {
		clients.add(client);
		Position position = availablePositions.remove(0);
		if(position != null) {
			startingPositions.put(client.getPlayer(), position);
			return true;
		}
		return false;
	}
	public void removeGameClient(GameClient client) {
		clients.remove(client);
		playerRankings.remove(client.getPlayer());
		
		if(startingPositions.get(client.getPlayer()) != null) {
			availablePositions.add(startingPositions.get(client.getPlayer()));
			startingPositions.remove(client.getPlayer());
		}
	}
	public List<GameClient> getGameClients() {
		return clients;
	}
	public List<Player> getPlayers() {
		List<Player> players = new ArrayList<Player>();
		for(GameClient client : clients) {
			players.add(client.getPlayer());
		}
		return players;
	}
	public GameRoom getGameRoom() {
		return gameroom;
	}
	public void setGameStarted(boolean gameStarted){
		this.gameStarted = gameStarted;
	}
	
	public void setGameroom(GameRoom gameroom) {
		this.gameroom = gameroom;
	}

	public GameServer getServer() {
		return server;
	}
	
	public HashMap<Player,Position> getStartingPositions() {
		return startingPositions;
	}
	
	public void sendAllResponseReady() {
		ResponseReady responseSetReady = new ResponseReady();
		addResponseForAll(responseSetReady);
	}
	
	/**
	 * 0 = start countdown
	 * 1 = game time
	 * 2 = elimination countdown
	 * @param type
	 * @param time
	 */
	public void sendAllResponseTime(int type, long time) {
		ResponseTime responseTime = new ResponseTime();
		responseTime.setData(type, time);
		addResponseForAll(responseTime);
	}

	public void nextPhase() {
		//send set_position response here
		//remember to edit all gameclients.player.position
		switch(phase) {
			case 0:
				sendAllResponseReady();
				phase += 1;
				break;
			case 1:
				setGameStarted(true);
				this.start();
				gameroom.setTimeStarted(System.currentTimeMillis());				
				initPowerUp(gameroom.getTimeStarted());
				gameroom.save(GameRoom.TIME_STARTED);
				break;
				
		}
	}

	private void initPowerUp(long l) {
		this.powerups = new long[Constants.NUMBER_OF_POWERUPS];
		for(int i=0; i<Constants.NUMBER_OF_POWERUPS; i++){
			powerups[i] = l - Constants.RESPAWN_TIME;
		}
	}

	public boolean getPowerups(int powerId) {
		long cur = System.currentTimeMillis();
		if(cur - powerups[powerId] >= Constants.RESPAWN_TIME){
			powerups[powerId] = cur;
			return true;
		}else{
			return false;
		}
	}
	
	public boolean updatePlayerRanking(Player player, double rankValue) {
		playerRankings.put(player, rankValue);
		return true;
	}

	public List<Player> getRankings() {
		List<Player> list = new ArrayList<Player>();
		HashMap<Player,Double> que = new HashMap<Player,Double>(playerRankings.size());
		for(Player player : playerRankings.keySet())
			que.put(player, playerRankings.get(player));
		
		while(que.size() > 0) {
			Player maxPlayer = null;
			for(Player player: que.keySet()) {
				if(maxPlayer == null || que.get(player) > que.get(maxPlayer)) {
					maxPlayer = player;
				}
			}
			list.add(maxPlayer);
			que.remove(maxPlayer);
		}
		return list;
	}

	public void addResponseForAll(long player_id, GameResponse response) {
		for (GameClient client : clients) {
			if (client.getId() != player_id) {
				client.addResponseForUpdate(response);
			}
		}		
	}
	
	public void addResponseForAll(GameResponse response) {
		for (GameClient client : clients) {
			client.addResponseForUpdate(response);
		}		
	}
}

