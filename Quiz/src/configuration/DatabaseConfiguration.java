package configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
/**
 * Class for loading active database parameters
 * 
 * @author Martin Veres
 * @author Filip Stojkovic
 * @author Marko Stevankovic
 */
public class DatabaseConfiguration {
	/**
	 * private static instance of DatabaseConfiguration class.
	 */
	private static DatabaseConfiguration instance;
	private Properties properties;
	
	/**
	 * private constructor
	 */
	private DatabaseConfiguration(){
		try{
			properties = new Properties();
			properties.load(new FileInputStream("db.config"));
		}catch(FileNotFoundException exc){
			exc.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * static method returning instance of DatabaseCongiguration class
	 * (Singleton pattern)
	 * 
	 * @return instance of DatabaseConfiguration class
	 */
	public static DatabaseConfiguration getInstance(){
		if(instance == null)
			instance = new DatabaseConfiguration();
		return instance;
	}
	
	/**
	 * @return active database, String containing information about active database
	 */
	public String getActiveDatabase(){
		return properties.getProperty("active_database");
	}
	
	/**
	 * @return active database url
	 */
	public String getURL(){
		return properties.getProperty(getActiveDatabase() + ".url");
	}
	
	/**
	 * @return active database login username
	 */
	public String getUsername(){
		return properties.getProperty(getActiveDatabase() + ".username");
	}
	
	/**
	 * @return active database login password
	 */
	public String getPassword(){
		return properties.getProperty(getActiveDatabase() + ".password");
	}
}
