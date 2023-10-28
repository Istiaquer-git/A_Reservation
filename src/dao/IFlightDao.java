package dao;

import model.Flight;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object interface for Flight entities.
 * Defines operations for accessing and manipulating Flight data in the database.
 */
public interface IFlightDao {
    
    /**
     * Adds a new flight to the database.
     * 
     * @param flight the flight to add
     * @return the ID of the newly added flight
     * @throws SQLException if a database error occurs
     */
    int addFlight(Flight flight) throws SQLException;
    
    /**
     * Retrieves a flight by its ID.
     * 
     * @param id the ID of the flight to retrieve
     * @return the flight with the specified ID, or null if not found
     * @throws SQLException if a database error occurs
     */
    Flight getFlightById(int id) throws SQLException;
    
    /**
     * Retrieves all flights from the database.
     * 
     * @return a list of all flights
     * @throws SQLException if a database error occurs
     */
    List<Flight> getAllFlights() throws SQLException;
    
    /**
     * Updates an existing flight in the database.
     * 
     * @param flight the flight to update
     * @return true if the flight was updated successfully, false otherwise
     * @throws SQLException if a database error occurs
     */
    boolean updateFlight(Flight flight) throws SQLException;
    
    /**
     * Deletes a flight from the database.
     * 
     * @param id the ID of the flight to delete
     * @return true if the flight was deleted successfully, false otherwise
     * @throws SQLException if a database error occurs
     */
    boolean deleteFlight(int id) throws SQLException;
    
    /**
     * Searches for flights based on origin and destination.
     * 
     * @param origin the origin city
     * @param destination the destination city
     * @return a list of flights matching the criteria
     * @throws SQLException if a database error occurs
     */
    List<Flight> searchFlights(String origin, String destination) throws SQLException;
}