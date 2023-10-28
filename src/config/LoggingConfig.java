package config;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Centralized logging configuration for the application.
 * Configures loggers with appropriate handlers and formatting.
 */
public class LoggingConfig {
    private static final String LOG_FILE = "airline_reservation.log";
    private static final int LOG_FILE_SIZE = 1000000; // ~1MB
    private static final int LOG_FILE_COUNT = 3;
    private static final Level DEFAULT_LEVEL = Level.INFO;
    private static boolean initialized = false;
    
    /**
     * Initializes the logging configuration for the application.
     * Sets up console and file handlers with appropriate formatting.
     */
    public static synchronized void initialize() {
        if (initialized) {
            return;
        }
        
        try {
            // Get the root logger
            Logger rootLogger = Logger.getLogger("");
            
            // Remove existing handlers
            for (Handler handler : rootLogger.getHandlers()) {
                rootLogger.removeHandler(handler);
            }
            
            // Configure console handler
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(DEFAULT_LEVEL);
            rootLogger.addHandler(consoleHandler);
            
            // Configure file handler
            FileHandler fileHandler = new FileHandler(LOG_FILE, LOG_FILE_SIZE, LOG_FILE_COUNT, true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(DEFAULT_LEVEL);
            rootLogger.addHandler(fileHandler);
            
            // Set default level
            rootLogger.setLevel(DEFAULT_LEVEL);
            
            // Set specific levels for application packages
            Logger.getLogger("dao").setLevel(Level.FINE);
            Logger.getLogger("service").setLevel(Level.FINE);
            Logger.getLogger("ui").setLevel(Level.INFO);
            
            initialized = true;
            
            Logger.getLogger(LoggingConfig.class.getName()).info("Logging initialized");
        } catch (IOException e) {
            System.err.println("Failed to initialize logging: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Gets a configured logger for the specified class.
     * 
     * @param clazz the class to get a logger for
     * @return a configured logger
     */
    public static Logger getLogger(Class<?> clazz) {
        if (!initialized) {
            initialize();
        }
        return Logger.getLogger(clazz.getName());
    }
}