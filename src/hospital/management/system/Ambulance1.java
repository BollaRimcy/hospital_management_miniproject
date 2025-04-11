package hospital.management.system;
import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Ambulance1 extends JFrame {
    private JTextField ambNumberField, areaField, driverField, phoneField, salaryField;
    private JButton addButton, updateButton, viewButton, clearButton, searchButton;

    public Ambulance1() {
        setTitle("Ambulance Information");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(new Color(245, 250, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 16);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);

        int row = 0;

        // Ambulance Number
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Ambulance Number:"), gbc);
        ambNumberField = new JTextField();
        ambNumberField.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(ambNumberField, gbc);

        // Area
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Area That Go To:"), gbc);
        areaField = new JTextField();
        areaField.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(areaField, gbc);

        // Driver Name
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Driver Name:"), gbc);
        driverField = new JTextField();
        driverField.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(driverField, gbc);

        // Driver Phone Number
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Driver Phone No:"), gbc);
        phoneField = new JTextField();
        phoneField.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        // Salary
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Salary:"), gbc);
        salaryField = new JTextField();
        salaryField.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(salaryField, gbc);

        // Buttons
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        viewButton = new JButton("View");
        clearButton = new JButton("Clear");
        searchButton = new JButton("Search");

        addButton.setFont(labelFont);
        updateButton.setFont(labelFont);
        viewButton.setFont(labelFont);
        clearButton.setFont(labelFont);
        searchButton.setFont(labelFont);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(panel.getBackground());
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(searchButton);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        // Button actions
        addButton.addActionListener(e -> addAmbulance());
        updateButton.addActionListener(e -> updateAmbulance());
        viewButton.addActionListener(e -> viewAmbulance());
        clearButton.addActionListener(e -> clearFields());
        searchButton.addActionListener(e -> searchAmbulance());

        add(panel);
        setVisible(true);
    }

    private void addAmbulance() {
        String ambNo = ambNumberField.getText().trim();
        String area = areaField.getText().trim();
        String driver = driverField.getText().trim();
        String phone = phoneField.getText().trim();
        String salary = salaryField.getText().trim();

        if (ambNo.isEmpty() || area.isEmpty() || driver.isEmpty() || phone.isEmpty() || salary.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management", "root", "Bollapooja@2005");

            String checkQuery = "SELECT * FROM ambulance WHERE ambulance_no=?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, ambNo);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Ambulance already exists. Use Update instead.");
            } else {
                String insertQuery = "INSERT INTO ambulance (ambulance_no, area, driver_name, driver_phone, salary) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(insertQuery);
                stmt.setString(1, ambNo);
                stmt.setString(2, area);
                stmt.setString(3, driver);
                stmt.setString(4, phone);
                stmt.setString(5, salary);
                stmt.executeUpdate();
                stmt.close();
                JOptionPane.showMessageDialog(this, "Ambulance added!");
            }

            rs.close();
            checkStmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    private void updateAmbulance() {
        String ambNo = ambNumberField.getText().trim();
        String area = areaField.getText().trim();
        String driver = driverField.getText().trim();
        String phone = phoneField.getText().trim();
        String salary = salaryField.getText().trim();

        if (ambNo.isEmpty() || area.isEmpty() || driver.isEmpty() || phone.isEmpty() || salary.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management", "root", "Bollapooja@2005");

            String checkQuery = "SELECT * FROM ambulance WHERE ambulance_no=?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, ambNo);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String updateQuery = "UPDATE ambulance SET area=?, driver_name=?, driver_phone=?, salary=? WHERE ambulance_no=?";
                PreparedStatement stmt = conn.prepareStatement(updateQuery);
                stmt.setString(1, area);
                stmt.setString(2, driver);
                stmt.setString(3, phone);
                stmt.setString(4, salary);
                stmt.setString(5, ambNo);
                stmt.executeUpdate();
                stmt.close();
                JOptionPane.showMessageDialog(this, "Ambulance updated!");
            } else {
                JOptionPane.showMessageDialog(this, "Ambulance not found. Use Add instead.");
            }

            rs.close();
            checkStmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
private void viewAmbulance() {
    try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management", "root", "Bollapooja@2005");
        String query = "SELECT * FROM ambulance";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Ambulance No");
        model.addColumn("Area");
        model.addColumn("Driver");
        model.addColumn("Phone");
        model.addColumn("Salary");

        while (rs.next()) {
            model.addRow(new Object[]{
                    rs.getString("ambulance_no"),
                    rs.getString("area"),
                    rs.getString("driver_name"),
                    rs.getString("driver_phone"),
                    rs.getString("salary")
            });
        }

        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(700, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "All Ambulances", JOptionPane.INFORMATION_MESSAGE);

        rs.close();
        stmt.close();
        conn.close();
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }
}
    private void searchAmbulance() {
        String ambNo = ambNumberField.getText().trim();

        if (ambNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ambulance number.");
            return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management", "root", "Bollapooja@2005");
            String query = "SELECT * FROM ambulance WHERE ambulance_no=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, ambNo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                areaField.setText(rs.getString("area"));
                driverField.setText(rs.getString("driver_name"));
                phoneField.setText(rs.getString("driver_phone"));
                salaryField.setText(rs.getString("salary"));
            } else {
                JOptionPane.showMessageDialog(this, "Ambulance not found.");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        ambNumberField.setText("");
        areaField.setText("");
        driverField.setText("");
        phoneField.setText("");
        salaryField.setText("");
    }

    public static void main(String[] args) {
        new Ambulance1();
    }
}
