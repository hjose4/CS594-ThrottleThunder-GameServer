package configuration;

// Java Imports
import java.util.HashMap;
import java.util.Map;

/**
 * The GameServerConf class stores important variables such as the port number
 * to be used for the server from the configuration file.
 */
public class GameServerConf {

    private Map<String, String> confRecords; // Stores server config. variables

    public GameServerConf() {
        confRecords = new HashMap<String, String>();
    }

    public void setConfRecords(Map<String, String> confRecords) {
        this.confRecords = confRecords;
    }

    public int getPortNumber() {
        return Integer.valueOf(confRecords.get("portNumber"));
    }
    
    public String getDatabaseHost() {
        return confRecords.get("databaseHost");
    }
    
    public int getDatabasePort() {
        return Integer.valueOf(confRecords.get("databasePort"));
    }
    
    public String getDatabaseUsername() {
        return confRecords.get("databaseUsername");
    }
    
    public String getDatabasePassword() {
        return confRecords.get("databasePassword");
    }
    
    public String getDatabaseName() {
        return confRecords.get("databaseName");
    }
    
}