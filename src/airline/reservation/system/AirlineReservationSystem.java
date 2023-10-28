/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airline.reservation.system;

import config.LoggingConfig;
import dao.DBConnectionManager;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sqlite.JDBC;
import service.ServiceFactory;

/**
 * Main entry point for the Airline Reservation System application.
 * Handles initialization of database connection, services, and UI components.
 * 
 * @author aliar
 */
public class AirlineReservationSystem {
    private static final Logger LOGGER = LoggingConfig.getLogger(AirlineReservationSystem.class.getName());
    
    // Legacy static references for backward compatibility
    public static Statement statement = null; // For ease of use
    public static Connection connection;
    
    // Application components
    private static DBConnectionManager connectionManager;
    private static ServiceFactory serviceFactory;
    
    /**
     * Main entry point for the application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            // Initialize logging
            LOGGER.info("Starting Airline Reservation System");
            
            // Initialize connection manager
            connectionManager = DBConnectionManager.getInstance();
            
            // Initialize service factory
            serviceFactory = ServiceFactory.getInstance();
            
            // For backward compatibility
            connection = connectionManager.getConnection();
            statement = connection.createStatement();
            
            // Show loader page
            LOGGER.info("Displaying loader page");
            new LoaderPage().setVisible(true);
            
            // Register shutdown hook for clean resource release
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                shutdown();
            }));
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error initializing application", e);
            System.exit(1);
        }
    }
    
    /**
     * Legacy connection method - maintained for backward compatibility
     */
    public static void connect() {
        try {
            // If connection already exists, return
            if (connection != null && !connection.isClosed()) {
                return;
            }
            
            File dbFile = new File("airlineDB.db");
            boolean dbCreated = dbFile.exists();
            LOGGER.info("Database file exists: " + dbCreated);
            
            // Use connection manager to get connection
            connection = connectionManager.getConnection();
            statement = connection.createStatement();
            LOGGER.info("Connected to database");
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error connecting to database", e);
        }
    }
    
    /**
     * Properly shuts down the application and releases resources
     */
    public static void shutdown() {
        LOGGER.info("Shutting down application");
        try {
            if (serviceFactory != null) {
                serviceFactory.shutdown();
            }
            if (connectionManager != null) {
                connectionManager.closeAllConnections();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during shutdown", e);
        }
    }
    
    /**
     * Get the service factory instance
     * @return ServiceFactory instance
     */
    public static ServiceFactory getServiceFactory() {
        return serviceFactory;
    }
}
