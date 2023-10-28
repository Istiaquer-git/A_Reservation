package dto;

import model.Booking;
import model.Flight;
import model.Passenger;

/**
 * Data Transfer Object for Booking entities.
 * Used for transferring booking data between the UI and service layers.
 */
public class BookingDTO {
    private Integer id;
    private Integer flightId;
    private Integer passengerId;
    private String bookingDate;
    private String seatNumber;
    private FlightDTO flight;
    private PassengerDTO passenger;
    
    /**
     * Default constructor.
     */
    public BookingDTO() {
    }
    
    /**
     * Constructs a BookingDTO from a Booking entity.
     * 
     * @param booking the Booking entity
     */
    public BookingDTO(Booking booking) {
        if (booking != null) {
            this.id = booking.getId();
            this.flightId = booking.getFlightId();
            this.passengerId = booking.getPassengerId();
            this.bookingDate = booking.getBookingDate().toString();
            this.seatNumber = booking.getSeatNumber();
            
            if (booking.getFlight() != null) {
                this.flight = new FlightDTO(booking.getFlight());
            }
            
            if (booking.getPassenger() != null) {
                this.passenger = new PassengerDTO(booking.getPassenger());
            }
        }
    }
    
    /**
     * Converts this DTO to a Booking entity.
     * 
     * @return a new Booking entity
     */
    public Booking toEntity() {
        Booking booking = new Booking();
        booking.setId(this.id);
        booking.setFlightId(this.flightId);
        booking.setPassengerId(this.passengerId);
        booking.setSeatNumber(this.seatNumber);
        
        if (this.flight != null) {
            booking.setFlight(this.flight.toEntity());
        }
        
        if (this.passenger != null) {
            booking.setPassenger(this.passenger.toEntity());
        }
        
        return booking;
    }
    
    // Getters and Setters
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getFlightId() {
        return flightId;
    }
    
    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }
    
    public Integer getPassengerId() {
        return passengerId;
    }
    
    public void setPassengerId(Integer passengerId) {
        this.passengerId = passengerId;
    }
    
    public String getBookingDate() {
        return bookingDate;
    }
    
    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }
    
    public String getSeatNumber() {
        return seatNumber;
    }
    
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
    
    public FlightDTO getFlight() {
        return flight;
    }
    
    public void setFlight(FlightDTO flight) {
        this.flight = flight;
    }
    
    public PassengerDTO getPassenger() {
        return passenger;
    }
    
    public void setPassenger(PassengerDTO passenger) {
        this.passenger = passenger;
    }
    
    @Override
    public String toString() {
        return "Booking #" + id + ": " + 
               (passenger != null ? passenger.getFirstName() + " " + passenger.getLastName() : "Unknown Passenger") + 
               " on Flight " + 
               (flight != null ? flight.getFlightNumber() : "Unknown") + 
               ", Seat: " + seatNumber;
    }
}