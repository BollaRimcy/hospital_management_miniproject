package hospital.management.system;
import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PatientInformation extends JFrame {
    private JComboBox<String> idTypeCombo;
    private JTextField idNumberField, nameField, genderField, diseaseField, roomField, phnoField, depositField;
    private JButton searchButton, backButton;

    public PatientInformation() {
        setTitle("Patient Information Lookup");
        setSize(700, 600);
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
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);
        Dimension fieldSize = new Dimension(300, 35);
        int row = 0;

        // ID Type
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Select ID Type:"), gbc);
        idTypeCombo = new JComboBox<>(new String[]{"Aadhar Card", "Voter ID", "Passport", "Other"});
        idTypeCombo.setPreferredSize(fieldSize);
        idTypeCombo.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(idTypeCombo, gbc);

        // ID Number
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Enter ID Number:"), gbc);
        idNumberField = new JTextField();
        idNumberField.setPreferredSize(fieldSize);
        idNumberField.setFont(fieldFont);
        gbc.gridx = 1;
        panel.add(idNumberField, gbc);

        // Search Button
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(160, 40));
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        panel.add(searchButton, gbc);
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Name
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Name:"), gbc);
        nameField = new JTextField(); nameField.setEditable(false);
        nameField.setPreferredSize(new Dimension(160, 40));
        nameField.setFont(new Font("Segoe UI", Font.BOLD, 15));
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // Gender
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Gender:"), gbc);
        genderField = new JTextField(); genderField.setEditable(false);
        genderField.setPreferredSize(new Dimension(160, 40));
        genderField.setFont(new Font("Segoe UI", Font.BOLD, 15));
        gbc.gridx = 1;
        panel.add(genderField, gbc);

        // Disease
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Disease:"), gbc);
        diseaseField = new JTextField(); diseaseField.setEditable(false);
        diseaseField.setPreferredSize(new Dimension(160, 40));
        diseaseField.setFont(new Font("Segoe UI", Font.BOLD, 15));
        gbc.gridx = 1;
        panel.add(diseaseField, gbc);

        // Room
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Room:"), gbc);
        roomField = new JTextField(); roomField.setEditable(false);
        roomField.setPreferredSize(new Dimension(160, 40));
        roomField.setFont(new Font("Segoe UI", Font.BOLD, 15));
        gbc.gridx = 1;
        panel.add(roomField, gbc);

        // Phone number
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Phone_no:"), gbc);
        phnoField = new JTextField(); phnoField.setEditable(false); // Fixed from diseaseField
        phnoField.setPreferredSize(new Dimension(160, 40));
        phnoField.setFont(new Font("Segoe UI", Font.BOLD, 15));
        gbc.gridx = 1;
        panel.add(phnoField, gbc);

        // Deposit
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Deposit:"), gbc);
        depositField = new JTextField(); depositField.setEditable(false);
        depositField.setPreferredSize(new Dimension(160, 40));
        depositField.setFont(new Font("Segoe UI", Font.BOLD, 15));
        gbc.gridx = 1;
        panel.add(depositField, gbc);

        // Bottom panel with back button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(245, 250, 255));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 30));

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 35));
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JPanel rightAlignPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightAlignPanel.setBackground(new Color(245, 250, 255));
        rightAlignPanel.add(backButton);

        bottomPanel.add(rightAlignPanel, BorderLayout.SOUTH);

        // Add both main panel and bottom panel to frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        // Action Listeners
        searchButton.addActionListener(e -> fetchPatientDetails());
        backButton.addActionListener(e -> clearFields());

        setVisible(true);
    }

    private void fetchPatientDetails() {
        String selectedType = (String) idTypeCombo.getSelectedItem();
        String enteredID = idNumberField.getText().trim();

        if (enteredID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter ID number.");
            return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management", "root", "Bollapooja@2005");
            String query = "SELECT * FROM patient WHERE id_type = ? AND id_number = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, selectedType);
            stmt.setString(2, enteredID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nameField.setText(rs.getString("name"));
                genderField.setText(rs.getString("gender"));
                diseaseField.setText(rs.getString("disease"));
                roomField.setText(rs.getString("room"));
                phnoField.setText(rs.getString("phone_number"));
                depositField.setText(rs.getString("deposit"));
            } else {
                JOptionPane.showMessageDialog(this, "Patient not found!");
                clearFields();
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        nameField.setText("");
        genderField.setText("");
        diseaseField.setText("");
        roomField.setText("");
        phnoField.setText("");
        depositField.setText("");
        idNumberField.setText("");
        idTypeCombo.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        new PatientInformation();
    }
}