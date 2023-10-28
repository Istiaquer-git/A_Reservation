package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configuration manager for the application.
 * Handles loading properties from external configuration file
 * with proper fallback mechanisms and logging.
 */
public class AppConfig {
    private static final Logger LOGGER = Logger.getLogger(AppConfig.class.getName());
    private static final String CONFIG_FILE = "application.properties";
    private static final String CONFIG_FILE_CLASSPATH = "/application.properties";
    private static final Properties props = new Properties();
    
    // Default configuration values
    private static final String DEFAULT_DB_PATH = "airlineDB.db";
    private static final String DEFAULT_DB_USER = "";
    private static final String DEFAULT_DB_PASSWORD = "";
    
    static {
        loadProperties();
    }
    
    /**
     * Loads properties from configuration file with fallback mechanisms.
     * First tries to load from file system, then from classpath,
     * and finally falls back to default values.
     */
    private static void loadProperties() {
        boolean loaded = loadFromFileSystem() || loadFromClasspath();
        
        if (!loaded) {
            LOGGER.warning("Could not load configuration file. Using default values.");
            setDefaultProperties();
        }
        
        // Remove sensitive information from memory if not needed
        sanitizeProperties();
    }
    
    /**
     * Attempts to load properties from the file system.
     * 
     * @return true if successful, false otherwise
     */
    private static boolean loadFromFileSystem() {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            props.load(fis);
            LOGGER.info("Configuration loaded from file system: " + CONFIG_FILE);
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.FINE, "Could not load configuration from file system", e);
            return false;
        }
    }
    
    /**
     * Attempts to load properties from the classpath.
     * 
     * @return true if successful, false otherwise
     */
    private static boolean loadFromClasspath() {
        try (InputStream is = AppConfig.class.getResourceAsStream(CONFIG_FILE_CLASSPATH)) {
            if (is != null) {
                props.load(is);
                LOGGER.info("Configuration loaded from classpath: " + CONFIG_FILE_CLASSPATH);
                return true;
            }
            return false;
        } catch (IOException e) {
            LOGGER.log(Level.FINE, "Could not load configuration from classpath", e);
            return false;
        }
    }
    
    /**
     * Sets default property values when configuration file cannot be loaded.
     */
    private static void setDefaultProperties() {
        props.setProperty("db.path", DEFAULT_DB_PATH);
        props.setProperty("db.user", DEFAULT_DB_USER);
        props.setProperty("db.password", DEFAULT_DB_PASSWORD);
        LOGGER.config("Using default database path: " + DEFAULT_DB_PATH);
    }
    
    /**
     * Removes sensitive information from properties if not needed.
     */
    private static void sanitizeProperties() {
        // In a real application, you might want to clear sensitive data
        // after it's been used for initial configuration
    }
    
    /**
     * Gets the database file path.
     * 
     * @return the database file path
     */
    public static String getDbPath() {
        return props.getProperty("db.path", DEFAULT_DB_PATH);
    }
    
    /**
     * Gets the database username if configured.
     * 
     * @return the database username
     */
    public static String getDbUser() {
        return props.getProperty("db.user", DEFAULT_DB_USER);
    }
    
    /**
     * Gets the database password if configured.
     * 
     * @return the database password
     */
    public static String getDbPassword() {
        return props.getProperty("db.password", DEFAULT_DB_PASSWORD);
    }
    
    /**
     * Gets any property from the configuration.
     * 
     * @param key the property key
     * @param defaultValue the default value if property is not found
     * @return the property value or default if not found
     */
    public static String getProperty(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
