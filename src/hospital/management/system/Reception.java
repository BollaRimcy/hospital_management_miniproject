package hospital.management.system;

import java.awt.*;
import javax.swing.*;
import com.formdev.flatlaf.FlatIntelliJLaf;

public class Reception extends JFrame {

    Reception() {
        // Set FlatLaf Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Frame Settings
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Use absolute layout for background image scaling

        // Load and Scale Background Image
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/icon/recp.jpg"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(1200, 700, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel background = new JLabel(scaledIcon);
        background.setBounds(0, 0, 1200, 700);

        // Create Layered Pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1200, 700);

        // Top Navigation Panel
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(2, 5, 20, 10));
        panel1.setBounds(50, 20, 1100, 100);
        panel1.setOpaque(false); // Transparent background

        // Buttons
        JButton addPatientBtn = createStyledButton("Add New Patient");
        addPatientBtn.addActionListener(e -> new Newpatient());
        JButton roombtn=createStyledButton("Room");
        roombtn.addActionListener(e->new Room());

        JButton departmentBtn = createStyledButton("Department");
        departmentBtn.addActionListener(e -> new Department());
          
        JButton addemployeebtn=createStyledButton("Add Employee");
        addemployeebtn.addActionListener(e->new Addemployee());

        JButton patientinfo=createStyledButton("Patient Information");
        patientinfo.addActionListener(e-> new PatientInformation());

        JButton updatedinfo=createStyledButton("update patientinfo");
        updatedinfo.addActionListener(e->new UpdatePatientInformation());

        JButton ambulance=createStyledButton("Ambulance");
        ambulance.addActionListener(e->new Ambulance1());

        JButton patientdischarge=createStyledButton("Patient Discharge");
        patientdischarge.addActionListener(e-> new PatientDischarge());

        JButton logoutBtn = createStyledButton("Logout");
         logoutBtn.addActionListener(e -> {
             int result = JOptionPane.showConfirmDialog(
             this,
             "Are you sure you want to logout?",
             "Confirm Logout",
             JOptionPane.YES_NO_OPTION,
             JOptionPane.QUESTION_MESSAGE
         );
           if (result == JOptionPane.YES_OPTION) {
           dispose(); // closes only Reception window
                 // OR: System.exit(0); // if you want to close the entire app
           }
        });



        JButton[] buttons = {
            addPatientBtn,
            roombtn,
            departmentBtn,
            addemployeebtn,
            patientinfo,
            updatedinfo,
            ambulance,
            patientdischarge,
            logoutBtn
        };

        for (JButton btn : buttons) {
            panel1.add(btn);
        }

        // Add Components to Layered Pane
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(panel1, JLayeredPane.PALETTE_LAYER);

        // Add Layered Pane to Frame
        add(layeredPane);

        setVisible(true);
    }

    // Function to Create Stylish Buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(246, 215, 118)); // Keep original button color
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Hover Effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 193, 7)); // Darker yellow on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(246, 215, 118));
            }
        });

        return button;
    }

    public static void main(String args[]) {
        new Reception();
    }
}
