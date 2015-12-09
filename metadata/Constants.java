package metadata;

/**
 * The Constants class stores important variables as constants for later use.
 */
public class Constants {

	// Request (1xx) + Response (2xx)
	public final static short CMSG_LOGIN = 101;
	public final static short CMSG_DISCONNECT = 102;
	public final static short CMSG_REGISTER = 103;
	public final static short CMSG_FORGOT_PASSWORD = 104;
	public final static short CMSG_CREATE_CHARACTER = 105;
	public final static short CMSG_CHAT = 106;
	public final static short CMSG_MOVE = 107;
	public final static short CMSG_POWER_UP = 108;
	public final static short CMSG_POWER_PICKUP = 109;
	public final static short CMSG_HEALTH = 110;
	public final static short CMSG_ENTER_QUEUE = 111;
	public final static short CMSG_FRIEND_LIST = 112;
	public final static short CMSG_FRIEND_UPDATE = 113;
	public final static short CMSG_FRIEND_REQUEST = 114;	
	public final static short CMSG_PRIVATE_CHAT = 115;
	public final static short CMSG_INVITE = 116;
	public final static short CMSG_LOBBY_READY = 117;
	public final static short CMSG_ENTER_GAME_LOBBY = 118;
	//public final static short CMSG_GROUP = 119;
	public final static short CMSG_GARAGE_DETAILS = 120;
	public final static short CMSG_GARAGE_PURCHASE = 121;
	public final static short CMSG_RESULTS = 122;
	public final static short CMSG_RANKINGS = 123;
	public final static short CMSG_PRIZES = 124;
	public final static short CMSG_COLLISION = 125;
	public final static short CMSG_DEAD = 126;
	public final static short CMSG_READY = 127;
	public final static short CMSG_SET_POSITION = 128;
	//public final static short CMSG_TIME = 129;
	public final static short CMSG_SET_RANK = 130;
	public final static short CMSG_EMOTE = 132;
	public final static short CMSG_CHECKPOINTS = 133;
	public final static short CMSG_CURRENCY = 134;
	public final static short CMSG_TEST = 150;
	public final static short CMSG_TEST2 = 160;
	
	public final static short SMSG_LOGIN = 201;
	public final static short SMSG_DISCONNECT = 202;
	public final static short SMSG_REGISTER = 203;
	public final static short SMSG_FORGOT_PASSWORD = 204;
	public final static short SMSG_CREATE_CHARACTER = 205;
	public final static short SMSG_CHAT = 206;
	public final static short SMSG_MOVE = 207;
	public final static short SMSG_POWER_UP = 208;
	public final static short SMSG_POWER_PICKUP = 209;
	public final static short SMSG_HEALTH = 210;
	public final static short SMSG_ENTER_QUEUE = 211;
	public final static short SMSG_FRIEND_LIST = 212;
	public final static short SMSG_FRIEND_UPDATE = 213;
	public final static short SMSG_PRIVATE_CHAT = 215;
	public final static short SMSG_INVITE = 216;
	public final static short SMSG_LOBBY_READY = 217;
	//public final static short SMSG_ENTER_GAME_LOBBY = 218;
	public final static short SMSG_GROUP = 219;
	public final static short SMSG_GARAGE_DETAILS = 220;
	public final static short SMSG_GARAGE_PURCHASE = 221;
	public final static short SMSG_RESULTS = 222;
	public final static short SMSG_RANKINGS = 223;
	public final static short SMSG_PRIZES = 224;
	public final static short SMSG_COLLISION = 225;
	public final static short SMSG_DEAD = 226;
	public final static short SMSG_READY = 227;
	public final static short SMSG_SET_POSITION = 228;
	public final static short SMSG_TIME = 229;
	public final static short SMSG_SET_RANK = 230;
	public final static short SMSG_SET_READY = 231;
	public final static short SMSG_EMOTE = 232;
	public final static short SMSG_CURRENCY = 234;
	public final static short SMSG_TEST = 250;
	
	public final static short SMSG_RENDER_CHARACTER = 310;
	public final static short SMSG_REMOVE_CHARACTER = 311;
	public final static short REQ_HEARTBEAT = 301;
	
	// Other
	public static final int SAVE_INTERVAL = 60000;
	public static final String CLIENT_VERSION = "1.00";
	public static final int TIMEOUT_SECONDS = 15;
	public static final int LOADING_TIMEOUT_SECONDS = 120;
	public static final int NUMBER_OF_POWERUPS = 6;
	public static final long RESPAWN_TIME = 30000L;
	public static final long SEND_TIME = 1000L;
	public static final long COUNTDOWN_TIME = 5000L;
	public static final long PEACE_TIME = 10000L;
	public static final long ELIMINATION_TIME = 20000L;
	
	public static final int DD = 0;
	public static final int RR = 1;
}