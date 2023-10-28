package dao;

import model.Passenger;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object for Passenger entities
 * Handles database operations related to passengers
 */
public class PassengerDao {
    private static final Logger LOGGER = Logger.getLogger(PassengerDao.class.getName());
    private final Connection connection;

    /**
     * Constructs a PassengerDao with a database connection
     * 
     * @param connection Database connection
     */
    public PassengerDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Adds a new passenger to the database
     * 
     * @param p Passenger to be added
     * @throws SQLException if a database error occurs
     */
    public void addPassenger(Passenger p) throws SQLException {
        String sql = "INSERT INTO Passengers (passID, pName, Gender, passNum, nationality, Pfrom, Pto, status) \n" +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, p.getId());
            pst.setString(2, p.getName());
            pst.setString(3, p.getGender());
            pst.setString(4, p.getPassportNumber());
            pst.setString(5, p.getNationality());
            pst.setString(6, p.getFromCity());
            pst.setString(7, p.getToCity());
            pst.setString(8, p.getStatus());
            pst.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding passenger: " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Retrieves a passenger by ID
     * 
     * @param id Passenger ID
     * @return Passenger object if found, null otherwise
     * @throws SQLException if a database error occurs
     */
    public Passenger getPassengerById(int id) throws SQLException {
        String sql = "SELECT * FROM Passengers WHERE passID = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving passenger with ID " + id + ": " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Maps a database row to a Passenger object
     * 
     * @param rs ResultSet containing passenger data
     * @return Passenger object
     * @throws SQLException if a database error occurs
     */
    private Passenger mapRow(ResultSet rs) throws SQLException {
        return new Passenger(
            rs.getInt("passID"),
            rs.getString("pName"),
            rs.getString("Gender"),
            rs.getString("nationality"),
            rs.getString("passNum"),
            rs.getString("Pfrom"),
            rs.getString("Pto"),
            rs.getObject("flightId") != null ? rs.getInt("flightId") : null,
            rs.getObject("amountPaid") != null ? rs.getDouble("amountPaid") : null,
            rs.getString("status")
        );
    }
    // ...Add update, delete, findAll, etc.
}
