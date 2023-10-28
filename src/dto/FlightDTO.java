package dto;

import model.Flight;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Data Transfer Object for Flight entities.
 * Used for transferring flight data between the UI and service layers.
 */
public class FlightDTO {
    private Integer id;
    private String flightNumber;
    private String origin;
    private String destination;
    private String departureDate;
    private Double price;
    private Integer availableSeats;
    
    /**
     * Default constructor.
     */
    public FlightDTO() {
    }
    
    /**
     * Constructs a FlightDTO from a Flight entity.
     * 
     * @param flight the Flight entity
     */
    public FlightDTO(Flight flight) {
        if (flight != null) {
            this.id = flight.getId();
            this.flightNumber = flight.getFlightNumber();
            this.origin = flight.getOrigin();
            this.destination = flight.getDestination();
            this.departureDate = flight.getDepartureDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
            this.price = flight.getPrice();
            this.availableSeats = flight.getAvailableSeats();
        }
    }
    
    /**
     * Converts this DTO to a Flight entity.
     * 
     * @return a new Flight entity
     */
    public Flight toEntity() {
        Flight flight = new Flight();
        flight.setId(this.id);
        flight.setFlightNumber(this.flightNumber);
        flight.setOrigin(this.origin);
        flight.setDestination(this.destination);
        flight.setDepartureDate(LocalDate.parse(this.departureDate));
        flight.setPrice(this.price);
        flight.setAvailableSeats(this.availableSeats);
        return flight;
    }
    
    // Getters and Setters
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getFlightNumber() {
        return flightNumber;
    }
    
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    
    public String getOrigin() {
        return origin;
    }
    
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    
    public String getDestination() {
        return destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public String getDepartureDate() {
        return departureDate;
    }
    
    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Integer getAvailableSeats() {
        return availableSeats;
    }
    
    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }
    
    @Override
    public String toString() {
        return "Flight #" + flightNumber + ": " + origin + " to " + destination + 
               " on " + departureDate + ", Price: $" + price + 
               ", Available Seats: " + availableSeats;
    }
}