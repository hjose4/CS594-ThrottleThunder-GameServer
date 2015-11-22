package dataAccessLayer;

import java.util.HashMap;
import java.util.List;

import dataAccessLayer.DatabaseDriver;

public class GameRoom extends ObjectModel{
	private int id;
	private int type;
	private long time_started;
	private String map_name;
	private String room_name;
	private HashMap<Integer,Player> playerRankings;
	
	public GameRoom(HashMap<String, String> input) {
	super(input);	
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getTimeStarted() {
		return time_started;
	}

	public void setTimeStarted(long time_started) {
		this.time_started = time_started;
	}

	public String getMapName() {
		return map_name;
	}

	public void setMapName(String map_name) {
		this.map_name = map_name;
	}

	public int getId() {
		return id;
	}
	
	public void update(String field, Object value) {
		//TODO : Accessed in a static way, could do 
		//DatabaseDriver dbDriver = DatabaseDriver.getInstance(); (Bastien)
		dataAccessLayer.DatabaseDriver.update(this.getClass(), this.id, field, value);
	}

	public String getRoomName() {
		return room_name;
	}

	public void setRoomName(String game_name) {
		this.room_name = game_name;
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
