package hospital.management.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class connection {
    Connection connection;
    Statement statement;

    public connection(){
        try {
            // Establishing the connection with MySQL database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management", "root", "Bollapooja@2005");
            statement = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions that occur during connection establishment
            // You might want to throw a custom exception or handle it according to your application needs
        }
    }

}