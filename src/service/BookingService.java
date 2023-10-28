package service;

import dao.BookingDao;
import dao.FlightDao;
import dao.PassengerDao;
import model.Booking;
import model.Flight;
import model.Passenger;
import service.exception.ResourceNotFoundException;
import service.exception.ServiceException;
import service.exception.ValidationException;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service class for booking-related business operations
 */
public class BookingService {
    private static final Logger LOGGER = Logger.getLogger(BookingService.class.getName());
    private final BookingDao dao;
    private final FlightDao flightDao;
    private final PassengerDao passengerDao;
    private final Connection connection;

    /**
     * Constructs a BookingService with required DAOs
     * 
     * @param dao Data access object for bookings
     * @param flightDao Data access object for flights
     * @param passengerDao Data access object for passengers
     * @param connection Database connection for transaction management
     */
    public BookingService(BookingDao dao, FlightDao flightDao, PassengerDao passengerDao, Connection connection) {
        this.dao = dao;
        this.flightDao = flightDao;
        this.passengerDao = passengerDao;
        this.connection = connection;
    }

    /**
     * Creates a new booking with transaction management
     * 
     * @param passengerId Passenger ID
     * @param flightId Flight ID
     * @param amountPaid Amount paid
     * @return Created booking
     * @throws ValidationException if booking data is invalid
     * @throws ResourceNotFoundException if flight or passenger not found
     * @throws ServiceException if a service error occurs
     */
    public Booking createBooking(int passengerId, int flightId, double amountPaid) 
            throws ValidationException, ResourceNotFoundException, ServiceException {
        
        // Validate input
        if (amountPaid <= 0) {
            throw new ValidationException("Amount paid must be positive");
        }
        
        boolean autoCommit = true;
        try {
            // Check if passenger exists
            Passenger passenger = passengerDao.getPassengerById(passengerId);
            if (passenger == null) {
                throw new ResourceNotFoundException("Passenger with ID " + passengerId + " not found");
            }
            
            // Check if flight exists and has available seats
            Flight flight = flightDao.getFlightById(flightId);
            if (flight == null) {
                throw new ResourceNotFoundException("Flight with ID " + flightId + " not found");
            }
            
            // Business rule: Check if amount paid is at least the flight price
            if (amountPaid < flight.getPrice()) {
                throw new ValidationException("Amount paid must be at least the flight price: " + flight.getPrice());
            }
            
            // Start transaction
            autoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            
            // Create booking
            Booking booking = new Booking(
                null, 
                passengerId, 
                flightId, 
                amountPaid, 
                "CONFIRMED", 
                LocalDate.now()
            );
            
            dao.addBooking(booking);
            
            // Commit transaction
            connection.commit();
            
            return booking;
        } catch (SQLException e) {
            // Rollback transaction on error
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                LOGGER.log(Level.SEVERE, "Error rolling back transaction: " + rollbackEx.getMessage(), rollbackEx);
            }
            
            LOGGER.log(Level.SEVERE, "Error creating booking: " + e.getMessage(), e);
            throw new ServiceException("Failed to create booking", e);
        } finally {
            // Restore auto-commit state
            try {
                connection.setAutoCommit(autoCommit);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error restoring auto-commit: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Retrieves a booking by ID
     * 
     * @param id Booking ID
     * @return Booking object
     * @throws ResourceNotFoundException if booking not found
     * @throws ServiceException if a service error occurs
     */
    public Booking getBookingById(int id) throws ResourceNotFoundException, ServiceException {
        try {
            Booking booking = dao.getBookingById(id);
            if (booking == null) {
                throw new ResourceNotFoundException("Booking with ID " + id + " not found");
            }
            return booking;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving booking with ID " + id + ": " + e.getMessage(), e);
            throw new ServiceException("Failed to retrieve booking", e);
        }
    }
    
    /**
     * Retrieves all bookings for a passenger
     * 
     * @param passengerId Passenger ID
     * @return List of bookings
     * @throws ResourceNotFoundException if passenger not found
     * @throws ServiceException if a service error occurs
     */
    public List<Booking> getBookingsByPassengerId(int passengerId) 
            throws ResourceNotFoundException, ServiceException {
        try {
            // Check if passenger exists
            Passenger passenger = passengerDao.getPassengerById(passengerId);
            if (passenger == null) {
                throw new ResourceNotFoundException("Passenger with ID " + passengerId + " not found");
            }
            
            return dao.getBookingsByPassengerId(passengerId);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving bookings for passenger ID " + passengerId + ": " + e.getMessage(), e);
            throw new ServiceException("Failed to retrieve bookings", e);
        }
    }
}
