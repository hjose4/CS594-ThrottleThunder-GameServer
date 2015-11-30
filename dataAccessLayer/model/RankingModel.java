package dataAccessLayer.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import dataAccessLayer.DatabaseDriver;
import dataAccessLayer.ObjectModel;
import dataAccessLayer.record.Ranking;

public class RankingModel {
	public static Ranking getRankingById(int id) {
		try {
			ObjectModel model = DatabaseDriver.getInstance().findById(Ranking.class, id);
			if(model != null) {
				return new Ranking(model.getData());
			}
		} catch(SQLException e) {
			//Do not care
		}
		return null;
	}
	
	public static ArrayList<Ranking> searchForRankings(HashMap<String,String> params) {
		ArrayList<Ranking> list = new ArrayList<Ranking>();
		ArrayList<ObjectModel> models = DatabaseDriver.getInstance().find(Ranking.class, params);
		if(models != null) {
			for(ObjectModel model : models) {
				list.add(new Ranking(model.getData()));
			}
		}
		return list;
	}
	
	public static ArrayList<Ranking> searchForRankingsForGameId(int gameId) {
		HashMap<String,String> params = new HashMap<>();
		params.put(Ranking.GAME_ID,gameId+"");
		return searchForRankings(params);
	}
	
	public static boolean insertRanking(Ranking ranking) {
		return ranking.save("all");
	}
}
