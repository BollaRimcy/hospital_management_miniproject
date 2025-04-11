package hospital.management.system;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PatientDischarge extends JFrame {
    private JTable patientTable;
    private DefaultTableModel tableModel;
    private JButton dischargeButton, refreshButton, searchButton;
    private JTextField searchField;
    private JComboBox<String> idTypeComboBox;

    private String[] columnNames = {"ID", "ID Type", "Number", "Name", "Gender", "Disease", "Room", "Phone", "Deposit"};

    public PatientDischarge() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Patient Discharge");
        setSize(950, 500);
        setLocation(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top search panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(BorderFactory.createTitledBorder("Search Patient by ID"));

        String[] idTypes = {"Aadhar", "Voter ID", "Passport", "Other"};
        idTypeComboBox = new JComboBox<>(idTypes);
        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        topPanel.add(new JLabel("ID Type:"));
        topPanel.add(idTypeComboBox);
        topPanel.add(new JLabel("ID Number:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);

        add(topPanel, BorderLayout.NORTH);

        // Table and scroll pane
        tableModel = new DefaultTableModel(columnNames, 0);
        patientTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(patientTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        dischargeButton = new JButton("Discharge Patient");
        refreshButton = new JButton("Refresh");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(dischargeButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event handlers
        dischargeButton.addActionListener(e -> dischargeSelectedPatient());
        refreshButton.addActionListener(e -> loadPatientData());
        searchButton.addActionListener(e -> searchPatientById());

        // Load initial data
        loadPatientData();
        setVisible(true);
    }

    private void loadPatientData() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management", "root", "Bollapooja@2005");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patient");

            tableModel.setRowCount(0); // Clear previous data
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("id_type"),
                        rs.getString("id_number"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("disease"),
                        rs.getString("room"),
                        rs.getString("phone_number"),
                        rs.getString("deposit")
                });
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchPatientById() {
        String idType = idTypeComboBox.getSelectedItem().toString();
        String idNumber = searchField.getText().trim();

        if (idNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ID number to search.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management", "root", "Bollapooja@2005");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM patient WHERE id_type = ? AND id_number = ?");
            stmt.setString(1, idType);
            stmt.setString(2, idNumber);

            ResultSet rs = stmt.executeQuery();
            tableModel.setRowCount(0);

            boolean found = false;
            while (rs.next()) {
                found = true;
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("id_type"),
                        rs.getString("id_number"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("disease"),
                        rs.getString("room"),
                        rs.getString("phone_number"),
                        rs.getString("deposit")
                });
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "No patient found with the selected ID type and number.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error while searching patient!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dischargeSelectedPatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to discharge.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int patientId = (int) tableModel.getValueAt(selectedRow, 0);
        String roomNo = (String) tableModel.getValueAt(selectedRow, 6);

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to discharge this patient?", "Confirm Discharge", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management", "root", "Bollapooja@2005");
            con.setAutoCommit(false);

            // Delete patient
            PreparedStatement deleteStmt = con.prepareStatement("DELETE FROM patient WHERE id = ?");
            deleteStmt.setInt(1, patientId);
            int deleted = deleteStmt.executeUpdate();

            // Update room status
            PreparedStatement updateRoomStmt = con.prepareStatement("UPDATE room1 SET availability = 'Available' WHERE room_no = ?");
            updateRoomStmt.setString(1, roomNo);
            int updated = updateRoomStmt.executeUpdate();

            if (deleted > 0 && updated > 0) {
                con.commit();
                JOptionPane.showMessageDialog(this, "✅ Patient discharged and room marked available.");
                loadPatientData(); // Refresh table
            } else {
                con.rollback();
                JOptionPane.showMessageDialog(this, "❌ Operation failed. Please try again.");
            }

            deleteStmt.close();
            updateRoomStmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during discharge process!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new PatientDischarge();
    }
}
