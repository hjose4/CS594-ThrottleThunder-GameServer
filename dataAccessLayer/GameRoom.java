package dataAccessLayer;

import java.util.HashMap;
import java.util.List;

import dataAccessLayer.DatabaseDriver;

public class GameRoom extends ObjectModel{
	public static final String ID = "id";
	public static final String ROOM_NAME = "room_name";
	public static final String MAP_NAME = "map_name";
	public static final String TIME_STARTED = "time_started";
	public static final String STATUS = "status";
	public static final String TYPE = "type";
	private HashMap<Integer,Player> playerRankings;
	
	public GameRoom() {
		super(new HashMap<String,String>());	
	}
	public GameRoom(HashMap<String, String> input) {
		super(input);	
	}

	public int getType() {
		return Integer.valueOf(get(TYPE));
	}

	public void setType(int type) {
		set(TYPE,type);
	}

	public long getTimeStarted() {
		return Long.valueOf(get(TIME_STARTED));
	}

	public void setTimeStarted(long time_started) {
		set(TIME_STARTED,time_started);
	}

	public String getMapName() {
		return get(MAP_NAME);
	}

	public void setMapName(String map_name) {
		set(MAP_NAME,map_name);
	}

	public int getId() {
		return Integer.valueOf(get(ID));
	}

	public String getRoomName() {
		return get(ROOM_NAME);
	}

	public void setRoomName(String game_name) {
		set(ROOM_NAME,game_name);
	}
	
	public int getStatus() {
		return Integer.valueOf(get(STATUS));
	}
	
	public void setStatus(int status) {
		set(STATUS,status);
	}
	
	public boolean updatePlayerRanking(Player player, int ranking) {
		int prevRank = 0;
		for(int i = 0; i < playerRankings.size(); i++) {
			if(playerRankings.get(i).getID() == player.getID()) {
				prevRank = i;
				break;
			}
		}
		
		if(prevRank > 0) {
			if(Math.abs(prevRank-ranking) == 1) {
				Player temp = playerRankings.get(ranking);
				playerRankings.put(ranking,player);
				playerRankings.put(prevRank, temp);
				return true;
			} else if (prevRank - ranking == 0) {
				return true;
			}
			
			return false;
		} else {
			Player temp = playerRankings.get(ranking);
			if(temp == null) {
				playerRankings.put(ranking,player);
			} else {
				System.out.println("Looks like we hit a data integerity issue: GameRoom@updatePlayerRanking");
				insertPlayerAtRank(player,ranking);
			}
			return true;
		}
	}
	
	private void insertPlayerAtRank(Player player, int rank) {
		for(int i = playerRankings.size()-1; i > rank; i++) {
			playerRankings.put(i, playerRankings.get(i-1));
		}
		
		playerRankings.put(rank,player);
	}
	
	public HashMap<Player,Integer> getRankings() {
		HashMap<Player,Integer> list = new HashMap<Player,Integer>();
		for(int i = 0; i < playerRankings.size(); i++) {
			list.put(playerRankings.get(i), i);
		}
		return list;
	}
}
