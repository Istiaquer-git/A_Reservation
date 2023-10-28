package service;

import model.Passenger;
import dao.PassengerDao;
import java.sql.SQLException;
import java.util.List;

public class PassengerService {
    private final PassengerDao dao;

    public PassengerService(PassengerDao dao) {
        this.dao = dao;
    }

    public void addPassenger(Passenger p) throws SQLException {
        // Add domain or business validation here, e.g., unique passport number etc.
        dao.addPassenger(p);
    }

    public Passenger getPassengerById(int id) throws SQLException {
        return dao.getPassengerById(id);
    }
    // ... add update, delete, getAll, etc. as needed
}
