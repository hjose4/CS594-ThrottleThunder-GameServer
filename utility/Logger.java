package utility;

public class Logger {
	private static boolean enabled = true;
	public static void enableLogging() {
		enabled = true;
	}
	public static void disableLogging() {
		enabled = false;
	}
	public static void logMessage(Object message) {
		if(!enabled)
			return;
		
		System.out.println(message);
	}
	
	public static void logMessage(int message) {
		if(!enabled)
			return;
		
		System.out.println(message);
	}
	
	public static void logMessage(long message) {
		if(!enabled)
			return;
		
		System.out.println(message);
	}
	
	public static void logMessage(float message) {
		if(!enabled)
			return;
		
		System.out.println(message);
	}
	
	public static void logMessage(double message) {
		if(!enabled)
			return;
		
		System.out.println(message);
	}
	
}
