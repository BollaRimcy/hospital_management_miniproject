package hospital.management.system;

import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdatePatientInformation extends JFrame {
    private JComboBox<String> idTypeCombo;
    private JTextField idNumberField, nameField, genderField, diseaseField, roomField, phoneField, depositField;
    private JButton searchButton, updateButton, clearButton, viewButton;

    public UpdatePatientInformation() {
        setTitle("Update Patient Information");
        setSize(750, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 15);
        int row = 0;

        // ID Type
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Select ID Type:"), gbc);
        idTypeCombo = new JComboBox<>(new String[]{"Aadhar Card", "Voter ID", "Passport", "Other"});
        idTypeCombo.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(idTypeCombo, gbc);

        // ID Number
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Enter ID Number:"), gbc);
        idNumberField = new JTextField(); idNumberField.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(idNumberField, gbc);

        // Search Button
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        searchButton = new JButton("Search");
        searchButton.setFont(labelFont);
        panel.add(searchButton, gbc);
        gbc.gridwidth = 1;

        // Name
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Name:"), gbc);
        nameField = new JTextField(); nameField.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // Gender
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Gender:"), gbc);
        genderField = new JTextField(); genderField.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(genderField, gbc);

        // Disease
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Disease:"), gbc);
        diseaseField = new JTextField(); diseaseField.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(diseaseField, gbc);

        // Room
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Room:"), gbc);
        roomField = new JTextField(); roomField.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(roomField, gbc);

        // Phone Number
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Phone Number:"), gbc);
        phoneField = new JTextField(); phoneField.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        // Deposit
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Deposit:"), gbc);
        depositField = new JTextField(); depositField.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(depositField, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(panel.getBackground());
        updateButton = new JButton("Update");
        updateButton.setFont(labelFont);

        viewButton = new JButton("View");
        viewButton.setFont(labelFont);
        buttonPanel.add(viewButton);

        clearButton = new JButton("Clear");
        clearButton.setFont(labelFont);
        buttonPanel.add(updateButton);
        buttonPanel.add(clearButton);

        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        // Listeners
        searchButton.addActionListener(e -> fetchPatientDetails());
        updateButton.addActionListener(e -> updatePatientDetails());
        viewButton.addActionListener(e -> {
            String idType = (String) idTypeCombo.getSelectedItem();
            String idNumber = idNumberField.getText().trim();
        
            if (idNumber.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter ID number to view.");
                return;
            }
        
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management", "root", "Bollapooja@2005");
                String query = "SELECT * FROM patient WHERE id_type = ? AND id_number = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, idType);
                stmt.setString(2, idNumber);
                ResultSet rs = stmt.executeQuery();
        
                if (rs.next()) {
                    showPatientInTable(rs); // ✅ show JTable only here
                } else {
                    JOptionPane.showMessageDialog(this, "Patient not found!");
                }
        
                rs.close(); stmt.close(); conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        
        clearButton.addActionListener(e -> clearFields());

        add(panel);
        setVisible(true);
    }

    private void fetchPatientDetails() {
        String idType = (String) idTypeCombo.getSelectedItem();
        String idNumber = idNumberField.getText().trim();

        if (idNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter ID number.");
            return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management", "root", "Bollapooja@2005");
            String query = "SELECT * FROM patient WHERE id_type = ? AND id_number = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, idType);
            stmt.setString(2, idNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nameField.setText(rs.getString("name"));
                genderField.setText(rs.getString("gender"));
                diseaseField.setText(rs.getString("disease"));
                roomField.setText(rs.getString("room"));
                phoneField.setText(rs.getString("phone_number"));
                depositField.setText(rs.getString("deposit"));
            } else {
                JOptionPane.showMessageDialog(this, "Patient not found!");
            }

            rs.close(); stmt.close(); conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void showPatientInTable(ResultSet rs) throws SQLException {
        String[] columnNames = {"ID Type", "ID Number", "Name", "Gender", "Disease", "Room", "Phone Number", "Deposit"};
        Object[][] data = {
            {
                rs.getString("id_type"),
                rs.getString("id_number"),
                rs.getString("name"),
                rs.getString("gender"),
                rs.getString("disease"),
                rs.getString("room"),
                rs.getString("phone_number"),
                rs.getString("deposit")
            }
        };

        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 200));

        JOptionPane.showMessageDialog(this, scrollPane, "Updated Patient Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updatePatientDetails() {
        String idType = (String) idTypeCombo.getSelectedItem();
        String idNumber = idNumberField.getText().trim();

        if (idNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter ID number.");
            return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management", "root", "Bollapooja@2005");
            String query = "UPDATE patient SET name=?, gender=?, disease=?, room=?, phone_number=?, deposit=? WHERE id_type=? AND id_number=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, nameField.getText());
            stmt.setString(2, genderField.getText());
            stmt.setString(3, diseaseField.getText());
            stmt.setString(4, roomField.getText());
            stmt.setString(5, phoneField.getText());
            stmt.setString(6, depositField.getText());
            stmt.setString(7, idType);
            stmt.setString(8, idNumber);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Patient information updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Update failed. Please check ID.");
            }

            stmt.close(); conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        nameField.setText("");
        genderField.setText("");
        diseaseField.setText("");
        roomField.setText("");
        phoneField.setText("");
        depositField.setText("");
        idNumberField.setText("");
        idTypeCombo.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        new UpdatePatientInformation();
    }
}
