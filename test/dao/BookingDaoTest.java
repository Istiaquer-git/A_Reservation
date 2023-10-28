package test.dao;

import dao.BookingDao;
import model.Booking;
import org.junit.*;
import java.sql.*;
import java.time.LocalDate;

public class BookingDaoTest {
    private static Connection connection;
    private static BookingDao dao;

    @BeforeClass
    public static void setupClass() throws Exception {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        Statement st = connection.createStatement();
        st.executeUpdate("CREATE TABLE Bookings (id INT PRIMARY KEY, passengerId INT, flightId INT, amountPaid REAL, status TEXT, bookingDate TEXT)");
        dao = new BookingDao(connection);
    }
    @AfterClass
    public static void tearDownClass() throws Exception {
        connection.close();
    }
    @Test
    public void testAddAndFetchBooking() throws Exception {
        Booking b = new Booking(1, 11, 22, 3000.0, "Paid", LocalDate.now());
        dao.addBooking(b);
        Booking fetched = dao.getBookingById(1);
        Assert.assertEquals(11, fetched.getPassengerId());
        Assert.assertEquals(22, fetched.getFlightId());
        Assert.assertEquals("Paid", fetched.getStatus());
    }
}
