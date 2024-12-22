import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Class untuk menangani koneksi database
class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/db_lawfirm";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Kahuripan21";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
}
