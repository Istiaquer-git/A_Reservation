package service;

import model.Flight;
import service.exception.ServiceException;
import service.exception.ValidationException;
import service.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Service interface for Flight operations.
 * Defines business operations related to flights.
 */
public interface IFlightService {
    
    /**
     * Adds a new flight after validating the data.
     * 
     * @param flight the flight to add
     * @return the ID of the newly added flight
     * @throws ValidationException if the flight data is invalid
     * @throws ServiceException if a service error occurs
     */
    int addFlight(Flight flight) throws ValidationException, ServiceException;
    
    /**
     * Retrieves a flight by its ID.
     * 
     * @param id the ID of the flight to retrieve
     * @return the flight with the specified ID
     * @throws ResourceNotFoundException if the flight is not found
     * @throws ServiceException if a service error occurs
     */
    Flight getFlightById(int id) throws ResourceNotFoundException, ServiceException;
    
    /**
     * Retrieves all flights.
     * 
     * @return a list of all flights
     * @throws ServiceException if a service error occurs
     */
    List<Flight> getAllFlights() throws ServiceException;
    
    /**
     * Searches for flights based on origin and destination.
     * 
     * @param origin the origin city
     * @param destination the destination city
     * @return a list of flights matching the criteria
     * @throws ValidationException if the search parameters are invalid
     * @throws ServiceException if a service error occurs
     */
    List<Flight> searchFlights(String origin, String destination) throws ValidationException, ServiceException;
}