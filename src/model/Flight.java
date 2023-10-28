package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a flight in the airline reservation system.
 * Contains all flight details including route, schedule, capacity and pricing.
 */
public class Flight {
    private Integer id;
    private String code;
    private String fromCity;
    private String toCity;
    private LocalDate departureDate;
    private String departureTime;
    private String arrivalTime;
    private Integer seatCount;
    private Double price;

    /**
     * Default constructor for Flight
     */
    public Flight() {}

    /**
     * Constructs a Flight with validation
     *
     * @param id Flight identifier
     * @param code Flight code/number
     * @param fromCity Origin city
     * @param toCity Destination city
     * @param departureDate Date of departure
     * @param departureTime Time of departure
     * @param arrivalTime Time of arrival
     * @param seatCount Available seats
     * @param price Ticket price
     * @throws IllegalArgumentException if required fields are invalid
     */
    public Flight(Integer id, String code, String fromCity, String toCity, 
                 LocalDate departureDate, String departureTime, String arrivalTime, 
                 Integer seatCount, Double price) {
        validateFlightData(code, fromCity, toCity, departureDate, seatCount, price);
        
        this.id = id;
        this.code = code;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seatCount = seatCount;
        this.price = price;
    }
    
    /**
     * Validates flight data
     * 
     * @param code Flight code
     * @param fromCity Origin city
     * @param toCity Destination city
     * @param departureDate Date of departure
     * @param seatCount Available seats
     * @param price Ticket price
     * @throws IllegalArgumentException if validation fails
     */
    private void validateFlightData(String code, String fromCity, String toCity, 
                                   LocalDate departureDate, Integer seatCount, Double price) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Flight code cannot be empty");
        }
        if (fromCity == null || fromCity.trim().isEmpty()) {
            throw new IllegalArgumentException("Origin city cannot be empty");
        }
        if (toCity == null || toCity.trim().isEmpty()) {
            throw new IllegalArgumentException("Destination city cannot be empty");
        }
        if (departureDate == null) {
            throw new IllegalArgumentException("Departure date is required");
        }
        if (seatCount != null && seatCount < 0) {
            throw new IllegalArgumentException("Seat count cannot be negative");
        }
        if (price != null && price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    // Getters and setters with validation
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Flight code cannot be empty");
        }
        this.code = code;
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

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        if (departureDate == null) {
            throw new IllegalArgumentException("Departure date is required");
        }
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        if (seatCount != null && seatCount < 0) {
            throw new IllegalArgumentException("Seat count cannot be negative");
        }
        this.seatCount = seatCount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        if (price != null && price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", fromCity='" + fromCity + '\'' +
                ", toCity='" + toCity + '\'' +
                ", departureDate=" + departureDate +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", seatCount=" + seatCount +
                ", price=" + price +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(id, flight.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
