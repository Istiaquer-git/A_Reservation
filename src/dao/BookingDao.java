package dao;

import model.Booking;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object for Booking entities
 * Handles database operations related to bookings
 */
public class BookingDao {
    private static final Logger LOGGER = Logger.getLogger(BookingDao.class.getName());
    private final Connection connection;
    
    /**
     * Constructs a BookingDao with a database connection
     * 
     * @param connection Database connection
     */
    public BookingDao(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Adds a new booking to the database
     * 
     * @param b Booking to be added
     * @throws SQLException if a database error occurs
     */
    public boolean addBooking(Booking b) throws SQLException {
        String sql = "INSERT INTO Bookings (id, passengerId, flightId, amountPaid, status, bookingDate) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, b.getId());
            pst.setInt(2, b.getPassengerId());
            pst.setInt(3, b.getFlightId());
            if(b.getAmountPaid() != null) pst.setDouble(4, b.getAmountPaid());
            else pst.setNull(4, Types.REAL);
            pst.setString(5, b.getStatus());
            pst.setString(6, b.getBookingDate() != null ? b.getBookingDate().toString() : null);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding booking: " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Retrieves a booking by ID
     * 
     * @param id Booking ID
     * @return Booking object if found, null otherwise
     * @throws SQLException if a database error occurs
     */
    public Booking getBookingById(int id) throws SQLException {
        String sql = "SELECT * FROM Bookings WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return mapRow(rs);
                return null;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving booking with ID " + id + ": " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Retrieves all bookings for a passenger
     * 
     * @param passengerId Passenger ID
     * @return List of bookings for the passenger
     * @throws SQLException if a database error occurs
     */
    public List<Booking> getBookingsByPassengerId(int passengerId) throws SQLException {
        String sql = "SELECT * FROM Bookings WHERE passengerId = ?";
        List<Booking> bookings = new ArrayList<>();
        
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, passengerId);
            
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    bookings.add(mapRow(rs));
                }
                return bookings;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving bookings for passenger ID " + passengerId + ": " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Maps a database row to a Booking object
     * 
     * @param rs ResultSet containing booking data
     * @return Booking object
     * @throws SQLException if a database error occurs
     */
    private Booking mapRow(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getInt("id"),
                rs.getInt("passengerId"),
                rs.getInt("flightId"),
                rs.getObject("amountPaid") != null ? rs.getDouble("amountPaid") : null,
                rs.getString("status"),
                rs.getString("bookingDate") != null ? LocalDate.parse(rs.getString("bookingDate")) : null
        );
    }
    // add update, delete, findAll as needed
}
