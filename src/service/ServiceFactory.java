package service;

import config.LoggingConfig;
import dao.DBConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Factory for creating service instances.
 * Implements dependency injection pattern to manage service dependencies.
 */
public class ServiceFactory {
    private static final Logger LOGGER = LoggingConfig.getLogger(ServiceFactory.class);
    private static ServiceFactory instance;
    private final DBConnectionManager connectionManager;
    
    /**
     * Private constructor to initialize the service factory.
     */
    private ServiceFactory() {
        try {
            // Initialize logging
            LoggingConfig.initialize();
            
            // Get connection manager instance
            connectionManager = DBConnectionManager.getInstance();
            LOGGER.info("ServiceFactory initialized successfully");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize ServiceFactory", e);
            throw new RuntimeException("Failed to initialize ServiceFactory", e);
        }
    }
    
    /**
     * Gets the singleton instance of the service factory.
     * 
     * @return the service factory instance
     */
    public static synchronized ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }
    
    /**
     * Gets a database connection from the connection manager.
     * 
     * @return a database connection
     * @throws SQLException if a database error occurs
     */
    public Connection getConnection() throws SQLException {
        return connectionManager.getConnection();
    }
    
    /**
     * Releases a database connection back to the connection manager.
     * 
     * @param connection the connection to release
     */
    public void releaseConnection(Connection connection) {
        connectionManager.releaseConnection(connection);
    }
    
    /**
     * Creates a new FlightService instance.
     * 
     * @return a new FlightService instance
     * @throws SQLException if a database error occurs
     */
    public IFlightService createFlightService() throws SQLException {
        return new FlightService(getConnection());
    }
    
    /**
     * Creates a new PassengerService instance.
     * 
     * @return a new PassengerService instance
     * @throws SQLException if a database error occurs
     */
    public PassengerService createPassengerService() throws SQLException {
        return new PassengerService(getConnection());
    }
    
    /**
     * Creates a new BookingService instance.
     * 
     * @return a new BookingService instance
     * @throws SQLException if a database error occurs
     */
    public BookingService createBookingService() throws SQLException {
        return new BookingService(getConnection());
    }
    
    /**
     * Closes all connections and resources.
     * Should be called when shutting down the application.
     */
    public void shutdown() {
        connectionManager.closeAllConnections();
        LOGGER.info("ServiceFactory shutdown complete");
    }
}