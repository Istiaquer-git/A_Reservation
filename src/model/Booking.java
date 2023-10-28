package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a booking in the airline reservation system.
 * Links passengers with flights and tracks payment status.
 */
public class Booking {
    private Integer id;
    private Integer passengerId;
    private Integer flightId;
    private Double amountPaid;
    private String status;
    private LocalDate bookingDate;

    /**
     * Default constructor for Booking
     */
    public Booking() {}

    /**
     * Constructs a Booking with validation
     *
     * @param id Booking identifier
     * @param passengerId Associated passenger ID
     * @param flightId Associated flight ID
     * @param amountPaid Amount paid for booking
     * @param status Booking status
     * @param bookingDate Date of booking
     * @throws IllegalArgumentException if required fields are invalid
     */
    public Booking(Integer id, Integer passengerId, Integer flightId, Double amountPaid, String status, LocalDate bookingDate) {
        validateBookingData(passengerId, flightId);
        
        this.id = id;
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.amountPaid = amountPaid;
        this.status = status;
        this.bookingDate = bookingDate;
    }
    
    /**
     * Validates booking data
     * 
     * @param passengerId Associated passenger ID
     * @param flightId Associated flight ID
     * @throws IllegalArgumentException if validation fails
     */
    private void validateBookingData(Integer passengerId, Integer flightId) {
        if (passengerId == null) {
            throw new IllegalArgumentException("Passenger ID is required");
        }
        if (flightId == null) {
            throw new IllegalArgumentException("Flight ID is required");
        }
    }
    
    // Getters and setters with validation
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Integer passengerId) {
        if (passengerId == null) {
            throw new IllegalArgumentException("Passenger ID is required");
        }
        this.passengerId = passengerId;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        if (flightId == null) {
            throw new IllegalArgumentException("Flight ID is required");
        }
        this.flightId = flightId;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        if (amountPaid != null && amountPaid < 0) {
            throw new IllegalArgumentException("Amount paid cannot be negative");
        }
        this.amountPaid = amountPaid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
    
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", passengerId=" + passengerId +
                ", flightId=" + flightId +
                ", amountPaid=" + amountPaid +
                ", status='" + status + '\'' +
                ", bookingDate=" + bookingDate +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
