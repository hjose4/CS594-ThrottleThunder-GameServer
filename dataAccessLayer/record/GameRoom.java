package dataAccessLayer.record;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import core.GameSession;
import dataAccessLayer.ObjectModel;

public class GameRoom extends ObjectModel{
	public static final String ID = "id";
	public static final String ROOM_NAME = "room_name";
	public static final String MAP_NAME = "map_name";
	public static final String TIME_STARTED = "time_started";
	public static final String STATUS = "status";
	public static final String TYPE = "type";
	
	private GameSession session;
	
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
		java.text.DateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = formatter.parse(get(TIME_STARTED));
			return date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0L;
	}

	public void setTimeStarted(Date time_started) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		set(TIME_STARTED,sdf.format(time_started));
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
	
	public void setGameSession(GameSession session) {
		this.session = session;
	}
	
	public GameSession getGameSession() {
		return session;
	}
	public boolean isRR() {
		return getType() == 1;
	}
}
