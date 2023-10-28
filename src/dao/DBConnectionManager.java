package dao;

import config.AppConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database connection manager implementing a simple connection pool.
 * Manages database connections efficiently to improve performance and resource usage.
 */
public class DBConnectionManager {
    private static final Logger LOGGER = Logger.getLogger(DBConnectionManager.class.getName());
    private static final int MAX_POOL_SIZE = 10;
    private static final int INITIAL_POOL_SIZE = 5;
    private static final long CONNECTION_TIMEOUT = 10000; // 10 seconds
    
    private static DBConnectionManager instance;
    private final BlockingQueue<Connection> connectionPool;
    private final List<Connection> usedConnections = new ArrayList<>();
    
    /**
     * Private constructor to initialize the connection pool.
     * 
     * @throws SQLException if there is an error initializing the connection pool
     */
    private DBConnectionManager() throws SQLException {
        connectionPool = new ArrayBlockingQueue<>(MAX_POOL_SIZE);
        initializeConnectionPool();
    }
    
    /**
     * Gets the singleton instance of the connection manager.
     * 
     * @return the connection manager instance
     * @throws SQLException if there is an error initializing the connection manager
     */
    public static synchronized DBConnectionManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnectionManager();
        }
        return instance;
    }
    
    /**
     * Initializes the connection pool with a set number of connections.
     * 
     * @throws SQLException if there is an error creating the connections
     */
    private void initializeConnectionPool() throws SQLException {
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            connectionPool.offer(createConnection());
        }
        LOGGER.info("Connection pool initialized with " + INITIAL_POOL_SIZE + " connections");
    }
    
    /**
     * Creates a new database connection.
     * 
     * @return a new database connection
     * @throws SQLException if there is an error creating the connection
     */
    private Connection createConnection() throws SQLException {
        String dbPath = AppConfig.getDbPath();
        String url = "jdbc:sqlite:" + dbPath;
        
        try {
            // Ensure SQLite JDBC driver is loaded
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(url);
            LOGGER.fine("Created new database connection to " + url);
            return connection;
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "SQLite JDBC driver not found", e);
            throw new SQLException("SQLite JDBC driver not found", e);
        }
    }
    
    /**
     * Gets a connection from the pool or creates a new one if needed.
     * 
     * @return a database connection
     * @throws SQLException if there is an error getting a connection
     */
    public synchronized Connection getConnection() throws SQLException {
        Connection connection;
        
        try {
            // Try to get a connection from the pool with timeout
            connection = connectionPool.poll();
            
            // If no connection is available and we haven't reached max size, create a new one
            if (connection == null) {
                if (usedConnections.size() < MAX_POOL_SIZE) {
                    connection = createConnection();
                    LOGGER.fine("Created new connection as pool was empty");
                } else {
                    LOGGER.warning("Connection pool exhausted, waiting for a connection to be returned");
                    // Wait for a connection to be returned to the pool
                    try {
                        connection = connectionPool.take();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new SQLException("Interrupted while waiting for a connection", e);
                    }
                }
            }
            
            // Validate connection before returning
            if (!isConnectionValid(connection)) {
                LOGGER.warning("Connection validation failed, creating a new connection");
                connection = createConnection();
            }
            
            usedConnections.add(connection);
            return connection;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting database connection", e);
            throw new SQLException("Error getting database connection", e);
        }
    }
    
    /**
     * Releases a connection back to the pool.
     * 
     * @param connection the connection to release
     */
    public synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            usedConnections.remove(connection);
            try {
                // Reset auto-commit to default state before returning to pool
                if (!connection.getAutoCommit()) {
                    connection.setAutoCommit(true);
                }
                connectionPool.offer(connection);
                LOGGER.fine("Connection returned to pool");
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error resetting connection state", e);
                // If we can't reset the connection, close it and create a new one
                closeConnection(connection);
                try {
                    connectionPool.offer(createConnection());
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, "Error creating replacement connection", ex);
                }
            }
        }
    }
    
    /**
     * Checks if a connection is valid.
     * 
     * @param connection the connection to check
     * @return true if the connection is valid, false otherwise
     */
    private boolean isConnectionValid(Connection connection) {
        try {
            if (connection == null || connection.isClosed()) {
                return false;
            }
            // Test the connection with a simple query
            return connection.isValid((int) (CONNECTION_TIMEOUT / 1000));
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error validating connection", e);
            return false;
        }
    }
    
    /**
     * Closes a connection.
     * 
     * @param connection the connection to close
     */
    private void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                LOGGER.fine("Connection closed");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error closing connection", e);
        }
    }
    
    /**
     * Closes all connections in the pool.
     * Should be called when shutting down the application.
     */
    public synchronized void closeAllConnections() {
        // Close used connections
        for (Connection connection : usedConnections) {
            closeConnection(connection);
        }
        usedConnections.clear();
        
        // Close connections in the pool
        for (Connection connection : connectionPool) {
            closeConnection(connection);
        }
        connectionPool.clear();
        
        LOGGER.info("All database connections closed");
    }
}