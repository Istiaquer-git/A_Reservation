package service;

import dao.FlightDao;
import dao.IFlightDao;
import model.Flight;
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
 * Service implementation for Flight operations.
 * Handles business logic related to flights.
 */
public class FlightService implements IFlightService {
    private static final Logger LOGGER = Logger.getLogger(FlightService.class.getName());
    private final IFlightDao flightDao;
    private final Connection connection;

    /**
     * Constructs a FlightService with a database connection.
     * 
     * @param connection Database connection
     */
    public FlightService(Connection connection) {
        this.connection = connection;
        this.flightDao = new FlightDao(connection);
    }

    /**
     * Adds a new flight with business validation
     * 
     * @param flight Flight to be added
     * @return true if successful
     * @throws ValidationException if flight data is invalid
     * @throws ServiceException if a service error occurs
     */
    public boolean addFlight(Flight flight) throws ValidationException, ServiceException {
        validateFlight(flight);
        
        try {
            return dao.addFlight(flight);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding flight: " + e.getMessage(), e);
            throw new ServiceException("Failed to add flight", e);
        }
    }

    /**
     * Retrieves a flight by ID
     * 
     * @param id Flight ID
     * @return Flight object
     * @throws ResourceNotFoundException if flight is not found
     * @throws ServiceException if a service error occurs
     */
    public Flight getFlightById(int id) throws ResourceNotFoundException, ServiceException {
        try {
            Flight flight = dao.getFlightById(id);
            if (flight == null) {
                throw new ResourceNotFoundException("Flight with ID " + id + " not found");
            }
            return flight;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving flight with ID " + id + ": " + e.getMessage(), e);
            throw new ServiceException("Failed to retrieve flight", e);
        }
    }
    
    /**
     * Retrieves all available flights
     * 
     * @return List of all flights
     * @throws ServiceException if a service error occurs
     */
    public List<Flight> getAllFlights() throws ServiceException {
        try {
            return dao.getAllFlights();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all flights: " + e.getMessage(), e);
            throw new ServiceException("Failed to retrieve flights", e);
        }
    }
    
    /**
     * Searches for flights based on origin, destination, and date
     * 
     * @param origin Flight origin
     * @param destination Flight destination
     * @param departureDate Departure date
     * @return List of matching flights
     * @throws ValidationException if search parameters are invalid
     * @throws ServiceException if a service error occurs
     */
    public List<Flight> searchFlights(String origin, String destination, LocalDate departureDate) 
            throws ValidationException, ServiceException {
        // Validate search parameters
        if (origin == null || origin.trim().isEmpty()) {
            throw new ValidationException("Origin cannot be empty");
        }
        if (destination == null || destination.trim().isEmpty()) {
            throw new ValidationException("Destination cannot be empty");
        }
        if (departureDate == null) {
            throw new ValidationException("Departure date cannot be null");
        }
        if (departureDate.isBefore(LocalDate.now())) {
            throw new ValidationException("Departure date cannot be in the past");
        }
        
        // Business rule: origin and destination cannot be the same
        if (origin.equalsIgnoreCase(destination)) {
            throw new ValidationException("Origin and destination cannot be the same");
        }
        
        try {
            return dao.searchFlights(origin, destination, departureDate);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error searching flights: " + e.getMessage(), e);
            throw new ServiceException("Failed to search flights", e);
        }
    }
    
    /**
     * Validates flight data according to business rules
     * 
     * @param flight Flight to validate
     * @throws ValidationException if validation fails
     */
    private void validateFlight(Flight flight) throws ValidationException {
        if (flight == null) {
            throw new ValidationException("Flight cannot be null");
        }
        
        if (flight.getFlightNumber() == null || flight.getFlightNumber().trim().isEmpty()) {
            throw new ValidationException("Flight number cannot be empty");
        }
        
        if (flight.getOrigin() == null || flight.getOrigin().trim().isEmpty()) {
            throw new ValidationException("Origin cannot be empty");
        }
        
        if (flight.getDestination() == null || flight.getDestination().trim().isEmpty()) {
            throw new ValidationException("Destination cannot be empty");
        }
        
        if (flight.getDepartureDate() == null) {
            throw new ValidationException("Departure date cannot be null");
        }
        
        if (flight.getArrivalDate() == null) {
            throw new ValidationException("Arrival date cannot be null");
        }
        
        // Business rule: departure date must be before arrival date
        if (flight.getDepartureDate().isAfter(flight.getArrivalDate())) {
            throw new ValidationException("Departure date must be before arrival date");
        }
        
        // Business rule: departure date cannot be in the past
        if (flight.getDepartureDate().isBefore(LocalDate.now())) {
            throw new ValidationException("Departure date cannot be in the past");
        }
        
        // Business rule: capacity must be positive
        if (flight.getCapacity() <= 0) {
            throw new ValidationException("Capacity must be positive");
        }
        
        // Business rule: price must be positive
        if (flight.getPrice() <= 0) {
            throw new ValidationException("Price must be positive");
        }
        
        // Business rule: origin and destination cannot be the same
        if (flight.getOrigin().equalsIgnoreCase(flight.getDestination())) {
            throw new ValidationException("Origin and destination cannot be the same");
        }
    }
}
