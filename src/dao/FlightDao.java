package dao;

import model.Flight;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object for Flight entities
 * Handles database operations related to flights
 */
public class FlightDao implements IFlightDao {
    private static final Logger LOGGER = Logger.getLogger(FlightDao.class.getName());
    private final Connection connection;

    /**
     * Constructs a FlightDao with a database connection
     * 
     * @param connection Database connection
     */
    public FlightDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Adds a new flight to the database
     * 
     * @param f Flight to be added
     * @return true if successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean addFlight(Flight f) throws SQLException {
        String sql = "INSERT INTO Flights (FlightId, FCode, Ffrom, Fto, deptDate, deptTime, arrTime, Seats, price)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, f.getId());
            pst.setString(2, f.getCode());
            pst.setString(3, f.getFromCity());
            pst.setString(4, f.getToCity());
            pst.setString(5, f.getDepartureDate() != null ? f.getDepartureDate().toString() : null);
            pst.setString(6, f.getDepartureTime());
            pst.setString(7, f.getArrivalTime());
            pst.setInt(8, f.getSeatCount());
            pst.setDouble(9, f.getPrice());
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding flight: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Retrieves a flight by its ID
     * 
     * @param id Flight ID
     * @return Flight object if found, null otherwise
     * @throws SQLException if a database error occurs
     */
    public Flight getFlightById(int id) throws SQLException {
        String sql = "SELECT * FROM Flights WHERE FlightId = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving flight with ID " + id + ": " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Maps a database row to a Flight object
     * 
     * @param rs ResultSet containing flight data
     * @return Flight object
     * @throws SQLException if a database error occurs
     */
    private Flight mapRow(ResultSet rs) throws SQLException {
        return new Flight(
                rs.getInt("FlightId"),
                rs.getString("FCode"),
                rs.getString("Ffrom"),
                rs.getString("Fto"),
                rs.getString("deptDate") != null ? LocalDate.parse(rs.getString("deptDate")) : null,
                rs.getString("deptTime"),
                rs.getString("arrTime"),
                rs.getInt("Seats"),
                rs.getDouble("price")
        );
    }
    
    /**
     * Retrieves all flights from the database
     * 
     * @return List of all flights
     * @throws SQLException if a database error occurs
     */
    public List<Flight> getAllFlights() throws SQLException {
        String sql = "SELECT * FROM Flights";
        List<Flight> flights = new ArrayList<>();
        
        try (PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                flights.add(mapRow(rs));
            }
            return flights;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all flights: " + e.getMessage(), e);
            throw e;
        }
    }
    // ... Add update, delete methods
}
