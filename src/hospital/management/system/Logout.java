package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Logout extends JFrame implements ActionListener {

    JButton logoutBtn, cancelBtn;

    public Logout() {
        setTitle("Logout");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel msg = new JLabel("Are you sure you want to logout?", JLabel.CENTER);
        msg.setFont(new Font("Tahoma", Font.PLAIN, 14));
        add(msg, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        logoutBtn = new JButton("Logout");
        cancelBtn = new JButton("Cancel");
        btnPanel.add(logoutBtn);
        btnPanel.add(cancelBtn);
        add(btnPanel, BorderLayout.SOUTH);

        logoutBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutBtn) {
            JOptionPane.showMessageDialog(this, "👋 Logged out successfully!");
            System.exit(0); // Closes the entire application
        } else if (e.getSource() == cancelBtn) {
            dispose(); // Closes only this window
        }
    }

    public static void main(String[] args) {
        new Logout();
    }
}
