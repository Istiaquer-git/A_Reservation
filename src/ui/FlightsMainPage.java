package ui;

import model.Flight;
import service.FlightService;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class FlightsMainPage extends JFrame {
    private FlightService service;
    // ... UI fields: textfields, table, buttons ...
    public FlightsMainPage(FlightService service) {
        this.service = service;
        initComponents();
    }
    private void onAddFlightButtonClick() {
        try {
            Flight f = /* method to build from form fields */;
            service.addFlight(f);
            JOptionPane.showMessageDialog(this, "Flight added.");
            // update table
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    // ... Other event handlers (update, delete, search) ...
    // ... No direct DB/SQL here!
}
