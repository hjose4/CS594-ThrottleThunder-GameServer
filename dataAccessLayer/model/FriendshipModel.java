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
	
	public static List<Friendship> getFriends(Player user, Player friend) {
		List<Friendship> list = new ArrayList<Friendship>();
		HashMap<String,String> params = new HashMap<>();
		params.put("user1_id", user.getId()+"");
		params.put("user2_id", friend.getId()+"");
		List<ObjectModel> models = DatabaseDriver.find(Friendship.class, params);
		if(models != null && models.size() > 0) {
			while(models.size() > 0) {
				list.add(new Friendship(models.remove(0).getData()));
			}
		} 

		params = new HashMap<>();
		params.put("user1_id", friend.getId()+"");
		params.put("user2_id", user.getId()+"");
		models = DatabaseDriver.find(Friendship.class, params);
		if(models != null && models.size() > 0) {
			while(models.size() > 0) {
				list.add(new Friendship(models.remove(0).getData()));
			}
		} 
		return list;
	}
	
	public static boolean createFriendship(Player user, Player friend) {
		HashMap<String,String> params = new HashMap<>();
		params.put("user1_id", user.getId()+"");
		params.put("user2_id", friend.getId()+"");
		Friendship friendship = new Friendship(params);
		return friendship.save("all");
	}
}