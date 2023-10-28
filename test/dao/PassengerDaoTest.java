package test.dao;

import dao.PassengerDao;
import model.Passenger;
import org.junit.*;
import java.sql.*;

public class PassengerDaoTest {
    private static Connection connection;
    private static PassengerDao dao;

    @BeforeClass
    public static void setupClass() throws Exception {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        Statement st = connection.createStatement();
        st.executeUpdate("CREATE TABLE Passengers (passID INT PRIMARY KEY, pName TEXT, Gender TEXT, passNum TEXT, nationality TEXT, Pfrom TEXT, Pto TEXT, flightId INT, amountPaid REAL, status TEXT)");
        dao = new PassengerDao(connection);
    }
    @AfterClass
    public static void tearDownClass() throws Exception {
        connection.close();
    }
    @Test
    public void testAddAndFetchPassenger() throws Exception {
        Passenger p = new Passenger(1, "Ali", "Male", "PK", "PK123", "Karachi", "Lahore", null, null, "Unpaid");
        dao.addPassenger(p);
        Passenger fetched = dao.getPassengerById(1);
        Assert.assertEquals("Ali", fetched.getName());
        Assert.assertEquals("Male", fetched.getGender());
        Assert.assertEquals("PK", fetched.getNationality());
    }
}
