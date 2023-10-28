package ui;

import model.Passenger;
import service.PassengerService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class PassengerMainPage extends JFrame {
    private PassengerService service;
    // ... Define UI components as fields ...
    public PassengerMainPage(PassengerService svc) {
        this.service = svc;
        initComponents();
        // Register event handlers (button clicks, etc.) only
    }
    private void onAddPassengerButtonClick() {
        // Gather data from form fields
        try {
            Passenger p = /* method to build model from form */;
            service.addPassenger(p);
            JOptionPane.showMessageDialog(this, "Passenger added successfully");
            // update UI
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error adding passenger: " + ex.getMessage());
        }
    }
    // ... Other event handlers for update, delete, search ...
    // ... No raw SQL/connection management here! ...
}
