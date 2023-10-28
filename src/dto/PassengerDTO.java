package dto;

import model.Passenger;

/**
 * Data Transfer Object for Passenger entities.
 * Used for transferring passenger data between the UI and service layers.
 */
public class PassengerDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    
    /**
     * Default constructor.
     */
    public PassengerDTO() {
    }
    
    /**
     * Constructs a PassengerDTO from a Passenger entity.
     * 
     * @param passenger the Passenger entity
     */
    public PassengerDTO(Passenger passenger) {
        if (passenger != null) {
            this.id = passenger.getId();
            this.firstName = passenger.getFirstName();
            this.lastName = passenger.getLastName();
            this.email = passenger.getEmail();
            this.phone = passenger.getPhone();
        }
    }
    
    /**
     * Converts this DTO to a Passenger entity.
     * 
     * @return a new Passenger entity
     */
    public Passenger toEntity() {
        Passenger passenger = new Passenger();
        passenger.setId(this.id);
        passenger.setFirstName(this.firstName);
        passenger.setLastName(this.lastName);
        passenger.setEmail(this.email);
        passenger.setPhone(this.phone);
        return passenger;
    }
    
    // Getters and Setters
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + email + ")";
    }
}