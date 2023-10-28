package test.dao;

import dao.FlightDao;
import model.Flight;
import org.junit.*;
import java.sql.*;
import java.time.LocalDate;

public class FlightDaoTest {
    private static Connection connection;
    private static FlightDao dao;

    @BeforeClass
    public static void setupClass() throws Exception {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        Statement st = connection.createStatement();
        st.executeUpdate("CREATE TABLE Flights (FlightId INT PRIMARY KEY, FCode TEXT, Ffrom TEXT, Fto TEXT, deptDate TEXT, deptTime TEXT, arrTime TEXT, Seats INT, price REAL)");
        dao = new FlightDao(connection);
    }
    @AfterClass
    public static void tearDownClass() throws Exception {
        connection.close();
    }
    @Test
    public void testAddAndFetchFlight() throws Exception {
        Flight f = new Flight(1, "PK001", "Karachi", "Islamabad", LocalDate.now(), "10:00", "12:00", 100, 2000.0);
        dao.addFlight(f);
        Flight fetched = dao.getFlightById(1);
        Assert.assertEquals("PK001", fetched.getCode());
        Assert.assertEquals("Karachi", fetched.getFromCity());
        Assert.assertEquals(100, fetched.getSeatCount());
    }
}
