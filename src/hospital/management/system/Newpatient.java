package hospital.management.system;
import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class Newpatient extends JFrame implements ActionListener {
    JComboBox<String> combobox;
    JTextField textFieldNumber, textName, textFieldDisease, textFieldRoom, textFieldDeposite,textFieldPhoneno;
    JRadioButton r1, r2;
    JLabel date;
    JButton b1, b2, b3,b4;

    Newpatient() {
        // Set Modern Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("New Patient Form");
        setSize(850, 550);
        setLocation(300, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels & Inputs
        JLabel heading = new JLabel("NEW PATIENT FORM", JLabel.CENTER);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        heading.setForeground(new Color(25, 42, 86));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(heading, gbc);

        gbc.gridwidth = 1;

        // ID Type
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("ID Type:"), gbc);
        combobox = new JComboBox<>(new String[]{"Aadhar Card", "Voter ID", "Passport", "Other"});
        gbc.gridx = 1;
        panel.add(combobox, gbc);

        // Number
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Number:"), gbc);
        textFieldNumber = new JTextField();
        gbc.gridx = 1;
        panel.add(textFieldNumber, gbc);

        // Name
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Name:"), gbc);
        textName = new JTextField();
        gbc.gridx = 1;
        panel.add(textName, gbc);

        // Gender
        gbc.gridx = 0; gbc.gridy = 4;
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

        // Disease
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Disease:"), gbc);
        textFieldDisease = new JTextField();
        gbc.gridx = 1;
        panel.add(textFieldDisease, gbc);

        // Room
        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(new JLabel("Room:"), gbc);
        textFieldRoom = new JTextField();
        gbc.gridx = 1;
        panel.add(textFieldRoom, gbc);

        // Time
        gbc.gridx = 0; gbc.gridy = 7;
        panel.add(new JLabel("Time:"), gbc);
        date = new JLabel(java.time.LocalDateTime.now().toString());
        gbc.gridx = 1;
        panel.add(date, gbc);

        // Phone Number
          gbc.gridx = 0; gbc.gridy = 8;
          panel.add(new JLabel("Phone Number:"), gbc);
          textFieldPhoneno = new JTextField();
          gbc.gridx = 1;
          panel.add(textFieldPhoneno, gbc);


        // Deposit
        gbc.gridx = 0; gbc.gridy = 9;
        panel.add(new JLabel("Deposit:"), gbc);
        textFieldDeposite = new JTextField();
        gbc.gridx = 1;
        panel.add(textFieldDeposite, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 248, 255));
        b1 = new JButton("Add");
        b2 = new JButton("Back");
        b3 = new JButton("View Patients");
        b4 = new JButton("Clear");
        buttonPanel.add(b1);
        buttonPanel.add(b2);
        buttonPanel.add(b3);
        buttonPanel.add(b4);

        gbc.gridx = 0; gbc.gridy = 10;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);
        setVisible(true);

        // Button Actions
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) { // ADD button clicked
            Connection con=null;
            try {
                 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management", "root", "Bollapooja@2005");
                 con.setAutoCommit(false);
                 System.out.println("Checking room availability..");
                
    // Check if room exists and is available
    String query = "SELECT * FROM room1 WHERE room_no = ?";
    PreparedStatement checkRoomStmt = con.prepareStatement(query);
    checkRoomStmt.setString(1, textFieldRoom.getText().trim());
    ResultSet rs = checkRoomStmt.executeQuery();

    if (!rs.next()) {
        JOptionPane.showMessageDialog(null, "❌ Room does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
        rs.close();
        checkRoomStmt.close();
        con.close();
        return;
    } else if (!rs.getString("availability").equalsIgnoreCase("Available")) {
        JOptionPane.showMessageDialog(null, "❌ Room is already occupied!", "Error", JOptionPane.ERROR_MESSAGE);
        rs.close();
        checkRoomStmt.close();
        con.close();
        return;
    }
    rs.close();
    checkRoomStmt.close();
    System.out.println("Room is Available.");
                String query2 = "INSERT INTO patient(id_type, id_number, name, gender, disease, room,phone_number, deposit) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
                PreparedStatement stmt = con.prepareStatement(query2);
                stmt.setString(1, (String) combobox.getSelectedItem());
                stmt.setString(2, textFieldNumber.getText());
                stmt.setString(3, textName.getText());
                stmt.setString(4, r1.isSelected() ? "Male" : "Female");
                stmt.setString(5, textFieldDisease.getText());
                stmt.setString(6, textFieldRoom.getText());
                stmt.setString(7, textFieldPhoneno.getText());
                stmt.setString(8, textFieldDeposite.getText());
                 int patientAdded=stmt.executeUpdate();
                 if(patientAdded>0){
                String updateRoom = "UPDATE room1 SET availability = 'Occupied' WHERE room_no = ?";
                PreparedStatement updateStmt = con.prepareStatement(updateRoom);
                updateStmt.setString(1,textFieldRoom.getText().trim());
                int roomUpdated=updateStmt.executeUpdate();
                System.out.println("Room Update Result: " + roomUpdated);
                if(roomUpdated>0){
                    con.commit();
                    JOptionPane.showMessageDialog(null, "✅ Patient Added Successfully!Room updated.");
                }else{
                    con.rollback();
                    JOptionPane.showMessageDialog(null, "❌ Failed to update room availability!", "Error", JOptionPane.ERROR_MESSAGE);
                }
          updateStmt.close();
        
            }else{
                con.rollback();
                JOptionPane.showMessageDialog(null,"❌ Failed to add patient!","Error",JOptionPane.ERROR_MESSAGE);
            }

                stmt.close();
                con.close();
             
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
            }
        } else if (ae.getSource() == b2) { // BACK button clicked
            setVisible(false);
        } else if (ae.getSource() == b3) { // VIEW PATIENT DETAILS button clicked
            new PatientDetails();
        }
        else if (ae.getSource()==b4){
            combobox.setSelectedItem(0);
            textFieldNumber.setText("");
            textName.setText("");
            textFieldDisease.setText("");
            textFieldRoom.setText("");
            textFieldPhoneno.setText("");
            textFieldDeposite.setText("");
            r1.setSelected(false);
            r2.setSelected(false);
        }
    }
    public static void main(String[] args) {
        new Newpatient();
    }
}
