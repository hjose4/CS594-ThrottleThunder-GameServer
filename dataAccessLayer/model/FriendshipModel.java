package dataAccessLayer.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataAccessLayer.DatabaseDriver;
import dataAccessLayer.ObjectModel;
import dataAccessLayer.record.Friendship;
import dataAccessLayer.record.Player;

public class FriendshipModel {
	public static Friendship getFriendship(Player user, Player friend) {
		HashMap<String,String> params = new HashMap<>();
		params.put("user1_id", user.getId()+"");
		params.put("user2_id", friend.getId()+"");
		List<ObjectModel> models = DatabaseDriver.find(Friendship.class, params);
		if(models != null && models.size() > 0) {
			return new Friendship(models.remove(0).getData());
		} else {
			params = new HashMap<>();
			params.put("user1_id", friend.getId()+"");
			params.put("user2_id", user.getId()+"");
			models = DatabaseDriver.find(Friendship.class, params);
			if(models != null && models.size() > 0) {
				return new Friendship(models.remove(0).getData());
			}
		}
		return null;
	}
	
	public static List<Player> getFriends(Player user) {
		List<Friendship> list = new ArrayList<Friendship>();
		HashMap<String,String> params = new HashMap<>();
		params.put("user1_id", user.getId()+"");
		List<ObjectModel> models = DatabaseDriver.find(Friendship.class, params);
		if(models != null && models.size() > 0) {
			while(models.size() > 0) {
				list.add(new Friendship(models.remove(0).getData()));
			}
		} 
		
		params = new HashMap<>();
		params.put("user2_id", user.getId()+"");
		models = DatabaseDriver.find(Friendship.class, params);
		if(models != null && models.size() > 0) {
			while(models.size() > 0) {
				list.add(new Friendship(models.remove(0).getData()));
			}
		} 
		
		List<Player> ret = new ArrayList<>();
		for(Friendship friendship : list) {
			Player player = null;
			if(Integer.valueOf(friendship.get("user1_id")) == user.getId()) {
				player = PlayerModel.getPlayerById(Integer.valueOf(friendship.get("user2_id")));
			} else {
				player = PlayerModel.getPlayerById(Integer.valueOf(friendship.get("user1_id")));
			}
			
			if(player == null) {
				System.out.println("friend does not exists? " + Integer.valueOf(friendship.get("user2_id")) + " or " + Integer.valueOf(friendship.get("user1_id")));
			} else {
				ret.add(player);
			}
		}
		
		return ret;
	}
	
	public static boolean createFriendship(Player user, Player friend) {
		if(getFriendship(user,friend) == null) {
			HashMap<String,String> params = new HashMap<>();
			params.put("user1_id", user.getId()+"");
			params.put("user2_id", friend.getId()+"");
			Friendship friendship = new Friendship(params);
			return friendship.save("all");
		}
		return false;
	}
	
	public static boolean removeFriendship(Player user, Player friend) {
		Friendship friendship = getFriendship(user,friend);
		if(friendship != null) {
			return DatabaseDriver.remove(Friendship.class, friendship.getId());
		}
		return false;
	}
}
