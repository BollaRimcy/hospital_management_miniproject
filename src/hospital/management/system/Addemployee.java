package hospital.management.system;

import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Addemployee extends JFrame implements ActionListener {
    JComboBox<String> comboBoxIdType;
    JTextField textIdNumber, textName, textPosition, textSalary;
    JTextField searchField;
    JButton addButton, backButton, viewButton, searchButton, updateButton;
    JRadioButton r1, r2;

    Addemployee() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Add Employee");
        setSize(850, 600);
        setLocation(300, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Add / Search / Update Employee", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);
        gbc.gridwidth = 1;

        // ID Type
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("ID Type:"), gbc);
        comboBoxIdType = new JComboBox<>(new String[]{"Aadhar", "Voter", "Passport", "Other"});
        gbc.gridx = 1;
        panel.add(comboBoxIdType, gbc);

        // ID Number
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("ID Number:"), gbc);
        textIdNumber = new JTextField();
        gbc.gridx = 1;
        panel.add(textIdNumber, gbc);

        // Name
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Name:"), gbc);
        textName = new JTextField();
        gbc.gridx = 1;
        panel.add(textName, gbc);

        // Position
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Position:"), gbc);
        textPosition = new JTextField();
        gbc.gridx = 1;
        panel.add(textPosition, gbc);

        // Salary
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Salary:"), gbc);
        textSalary = new JTextField();
        gbc.gridx = 1;
        panel.add(textSalary, gbc);

        // Gender
        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(new JLabel("Gender:"), gbc);
        JPanel genderPanel = new JPanel();
        genderPanel.setBackground(new Color(240, 248, 255));
        r1 = new JRadioButton("Male");
        r2 = new JRadioButton("Female");
        ButtonGroup bg = new ButtonGroup();
        bg.add(r1);
        bg.add(r2);
        genderPanel.add(r1);
        genderPanel.add(r2);
        gbc.gridx = 1;
        panel.add(genderPanel, gbc);

        // Search Field
        gbc.gridx = 0; gbc.gridy = 7;
        panel.add(new JLabel("Search by ID Number:"), gbc);
        searchField = new JTextField();
        gbc.gridx = 1;
        panel.add(searchField, gbc);

        // Buttons
        JPanel btnPanel = new JPanel();
        addButton = new JButton("Add");
        backButton = new JButton("Back");
        viewButton = new JButton("View");
        searchButton = new JButton("Search");
        updateButton = new JButton("Update");

        btnPanel.add(addButton);
        btnPanel.add(viewButton);
        btnPanel.add(searchButton);
        btnPanel.add(updateButton);
        btnPanel.add(backButton);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        panel.add(btnPanel, gbc);

        add(panel);
        setVisible(true);

        // Action Listeners
        addButton.addActionListener(this);
        backButton.addActionListener(this);
        viewButton.addActionListener(this);
        searchButton.addActionListener(this);
        updateButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String url = "jdbc:mysql://localhost:3306/hospital_management";
        String user = "root";
        String pass = "Bollapooja@2005";

        if (e.getSource() == addButton) {
            try {
                Connection con = DriverManager.getConnection(url, user, pass);
                String sql = "INSERT INTO employee(id_type, id_number, name, position,gender, salary) VALUES (?, ?, ?,?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, (String) comboBoxIdType.getSelectedItem());
                pst.setString(2, textIdNumber.getText());
                pst.setString(3, textName.getText());
                pst.setString(4, textPosition.getText());
                String gender = r1.isSelected() ? "Male" : r2.isSelected() ? "Female" : "";
                pst.setString(5, gender);

                pst.setString(6, textSalary.getText());

                int i = pst.executeUpdate();
                if (i > 0) {
                    JOptionPane.showMessageDialog(null, "✅ Employee Added Successfully!");
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(null, "❌ Failed to add employee!");
                }

                pst.close();
                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage());
            }
        } else if (e.getSource() == viewButton) {
            new EmployeeDetails();
        } else if (e.getSource() == backButton) {
            this.dispose();
        } else if (e.getSource() == searchButton) {
            try {
                Connection con = DriverManager.getConnection(url, user, pass);
                String sql = "SELECT * FROM employee WHERE id_number = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, searchField.getText());
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    comboBoxIdType.setSelectedItem(rs.getString("id_type"));
                    textIdNumber.setText(rs.getString("id_number"));
                    textName.setText(rs.getString("name"));
                    textPosition.setText(rs.getString("position"));
                    textSalary.setText(rs.getString("salary"));
                    String gender = rs.getString("gender");
                    if (gender != null) {
                        if (gender.equalsIgnoreCase("Male")) r1.setSelected(true);
                        else if (gender.equalsIgnoreCase("Female")) r2.setSelected(true);
                    }
                    
                } else {
                    JOptionPane.showMessageDialog(null, "❌ Employee Not Found!");
                }
                pst.close();
                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Search Error: " + ex.getMessage());
            }
        } else if (e.getSource() == updateButton) {
            try {
                Connection con = DriverManager.getConnection(url, user, pass);
                String sql = "UPDATE employee SET id_type=?, name=?, position=?, gender=?,salary=? WHERE id_number=?";
                PreparedStatement pst = con.prepareStatement(sql);
                String gender = r1.isSelected() ? "Male" : r2.isSelected() ? "Female" : "";
                pst.setString(1, (String) comboBoxIdType.getSelectedItem());
                pst.setString(2, textName.getText());
                pst.setString(3, textPosition.getText());
                pst.setString(4, gender);
                pst.setString(5, textSalary.getText());
                pst.setString(6, textIdNumber.getText());

                int i = pst.executeUpdate();
                if (i > 0) {
                    JOptionPane.showMessageDialog(null, "✅ Employee Updated Successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "❌ Update Failed!");
                }

                pst.close();
                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Update Error: " + ex.getMessage());
            }
        }
    }

    private void clearForm() {
        comboBoxIdType.setSelectedIndex(0);
        textIdNumber.setText("");
        textName.setText("");
        textPosition.setText("");
        textSalary.setText("");
        r1.setSelected(false);
        r2.setSelected(false);
    }

    public static void main(String[] args) {
        new Addemployee();
    }
}
