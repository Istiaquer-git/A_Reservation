package model;

import java.util.Objects;

/**
 * Represents a passenger in the airline reservation system.
 * Contains passenger details, travel information, and booking status.
 */
public class Passenger {
    private Integer id;
    private String name;
    private String gender;
    private String nationality;
    private String passportNumber;
    private String fromCity;
    private String toCity;
    private Integer flightId; // nullable
    private Double amountPaid; // nullable
    private String status; // e.g., Paid, Unpaid, Confirmed, Cancelled

    /**
     * Default constructor for Passenger
     */
    public Passenger() {}

    /**
     * Constructs a Passenger with validation
     *
     * @param id Passenger identifier
     * @param name Passenger name
     * @param gender Passenger gender
     * @param nationality Passenger nationality
     * @param passportNumber Passport number
     * @param fromCity Origin city
     * @param toCity Destination city
     * @param flightId Associated flight ID (can be null)
     * @param amountPaid Amount paid for booking (can be null)
     * @param status Booking status
     * @throws IllegalArgumentException if required fields are invalid
     */
    public Passenger(Integer id, String name, String gender, String nationality, String passportNumber,
                     String fromCity, String toCity, Integer flightId, Double amountPaid, String status) {
        validatePassengerData(name, passportNumber, fromCity, toCity);
        
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.nationality = nationality;
        this.passportNumber = passportNumber;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.flightId = flightId;
        this.amountPaid = amountPaid;
        this.status = status;
    }

    /**
     * Validates passenger data
     * 
     * @param name Passenger name
     * @param passportNumber Passport number
     * @param fromCity Origin city
     * @param toCity Destination city
     * @throws IllegalArgumentException if validation fails
     */
    private void validatePassengerData(String name, String passportNumber, String fromCity, String toCity) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Passenger name cannot be empty");
        }
        if (passportNumber == null || passportNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Passport number cannot be empty");
        }
        if (fromCity == null || fromCity.trim().isEmpty()) {
            throw new IllegalArgumentException("Origin city cannot be empty");
        }
        if (toCity == null || toCity.trim().isEmpty()) {
            throw new IllegalArgumentException("Destination city cannot be empty");
        }
    }

    // Getters and setters with validation
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Passenger name cannot be empty");
        }
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        if (passportNumber == null || passportNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Passport number cannot be empty");
        }
        this.passportNumber = passportNumber;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        if (fromCity == null || fromCity.trim().isEmpty()) {
            throw new IllegalArgumentException("Origin city cannot be empty");
        }
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        if (toCity == null || toCity.trim().isEmpty()) {
            throw new IllegalArgumentException("Destination city cannot be empty");
        }
        this.toCity = toCity;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
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

    @Override
    public String toString() {
        return "Passenger{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", gender='" + gender + '\'' +
               ", nationality='" + nationality + '\'' +
               ", passportNumber='" + passportNumber + '\'' +
               ", fromCity='" + fromCity + '\'' +
               ", toCity='" + toCity + '\'' +
               ", flightId=" + flightId +
               ", amountPaid=" + amountPaid +
               ", status='" + status + '\'' +
               '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(id, passenger.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
