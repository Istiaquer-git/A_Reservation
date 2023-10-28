package ui;

import model.Booking;
import service.BookingService;
import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class BookingMainPage extends JFrame {
    private BookingService service;
    // ... UI fields: textfields, table, buttons ...
    public BookingMainPage(BookingService service) {
        this.service = service;
        initComponents();
    }
    private void onAddBookingButtonClick() {
        try {
            Booking b = /* method to build from form fields */;
            service.addBooking(b);
            JOptionPane.showMessageDialog(this, "Booking added.");
            // update bookings table
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    // ... Other event handlers ...
    // No direct DB/JDBC logic here!
}
