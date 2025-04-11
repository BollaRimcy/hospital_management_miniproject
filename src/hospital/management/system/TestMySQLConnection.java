package hospital.management.system;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestMySQLConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hospital_management"; // Change 'your_database' to your DB name
        String user = "root"; // Change if your MySQL username is different
        String password = "Bollapooja@2005"; // Enter your MySQL password

        try {
            // Load MySQL JDBC Driver (optional in recent versions)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish Connection
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected to MySQL successfully!");

            // Close Connection
            con.close();
        } catch (ClassNotFoundException e) {
            System.out.println("❌ MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Connection Failed! Check database details.");
            e.printStackTrace();
        }
    }
}

