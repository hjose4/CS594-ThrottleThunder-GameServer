package dataAccessLayer;

import java.util.HashMap;


public class Rankings extends ObjectModel {
	private int id; 
	private int game_id; 
	private int player_id; 
	private int ranking; 
	
	public Rankings(HashMap<String, String> input) {
	super (input);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
}
