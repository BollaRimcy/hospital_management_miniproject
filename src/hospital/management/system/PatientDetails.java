package hospital.management.system;
import javax.swing.*;
import java.sql.*;

public class PatientDetails extends JFrame {
    JTable table;
    String[] columnNames = {"ID Type", "Number", "Name", "Gender", "Disease", "Room","Phone_number", "Deposit"};
    String[][] data = new String[500][8]; // Assuming max 50 patients

    PatientDetails() {
        setTitle("Patient Details");

        // Load Data from Database
        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital_management", "root", "Bollapooja@2005"
            );
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patient");

            int i = 0;
            while (rs.next()) {
                data[i][0] = rs.getString("id_type");
                data[i][1] = rs.getString("id_number");
                data[i][2] = rs.getString("name");
                data[i][3] = rs.getString("gender");
                data[i][4] = rs.getString("disease");
                data[i][5] = rs.getString("room");
                data[i][6] = rs.getString("phone_number");
                data[i][7] = rs.getString("deposit");
                i++;
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create Table
        table = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(table);
        add(sp);

        setSize(800, 400);
        setVisible(true);
    }
}
