package driver.database.record;

import java.util.HashMap;

import driver.database.ObjectModel;


public class Ranking extends ObjectModel {
	public static final String ID = "id";
	public static final String GAME_ID = "game_id";
	public static final String PLAYER_ID = "player_id";
	public static final String RANKING = "ranking"; 
	
	public Ranking () {
		super(new HashMap<String,String>());
	}
	public Ranking(HashMap<String, String> input) {
		super (input);
	}
	
	public int getId() {
		return Integer.valueOf(get(ID));
	}
	
	public int getGameId() {
		return Integer.valueOf(get(GAME_ID));
	}
	
	public void setGameId(int game_id) {
		set(GAME_ID,game_id);
	}
	
	public int getPlayerId() {
		return Integer.valueOf(get(PLAYER_ID));
	}
	
	public void setPlayerId(int player_id) {
		set(PLAYER_ID,player_id);
	}
	
	public int getRanking() {
		return Integer.valueOf(get(RANKING));
	}
	
	public void setRanking(int ranking) {
		set(RANKING,ranking);
	}
}
