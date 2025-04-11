package hospital.management.system;

import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class EmployeeDetails extends JFrame {

    JTable table;
    DefaultTableModel model;

    public EmployeeDetails() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Employee Details");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel();
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        model.addColumn("ID Type");
        model.addColumn("ID Number");
        model.addColumn("Name");
        model.addColumn("Position");
        model.addColumn("Salary");

        fetchData();

        setVisible(true);
    }

    private void fetchData() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management", "root", "Bollapooja@2005");
            String query = "SELECT id_type, id_number, name, position, salary FROM employee";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_type"),
                    rs.getString("id_number"),
                    rs.getString("name"),
                    rs.getString("position"),
                    rs.getString("salary")
                });
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage());
        }
    }
}
