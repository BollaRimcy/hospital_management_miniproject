package hospital.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class login extends JFrame implements ActionListener {
    JTextField textField;
    JPasswordField jpasswordField;
    JButton loginButton, cancelButton;

    login() {
        setTitle("Hospital Management System - Login");
        setLayout(null);
        setSize(1050, 600);
        setLocation(300, 100);
        setUndecorated(false);
        getContentPane().setBackground(Color.WHITE);

        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/login.png"));
        Image img = imageIcon.getImage().getScaledInstance(1050, 600, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(img));
        backgroundLabel.setBounds(0, 0, 1050, 600);
        add(backgroundLabel);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(330, 200, 400, 250);
        loginPanel.setBackground(new Color(0, 0, 0, 80)); // Semi-transparent panel
        backgroundLabel.add(loginPanel);

        JLabel titleLabel = new JLabel("LOGIN PANEL");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(140, 20, 200, 30);
        loginPanel.add(titleLabel);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(50, 70, 100, 30);
        loginPanel.add(usernameLabel);

        textField = new JTextField();
        textField.setBounds(150, 70, 200, 30);
        textField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createEmptyBorder());
        loginPanel.add(textField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(50, 120, 100, 30);
        loginPanel.add(passwordLabel);

        jpasswordField = new JPasswordField();
        jpasswordField.setBounds(150, 120, 200, 30);
        jpasswordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        jpasswordField.setBorder(BorderFactory.createEmptyBorder());
        loginPanel.add(jpasswordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(70, 180, 120, 40);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setBackground(new Color(34, 167, 240));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(34, 167, 240), 2));
        loginButton.addActionListener(this);
        loginPanel.add(loginButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(220, 180, 120, 40);
        cancelButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        cancelButton.setBackground(new Color(242, 38, 19));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(BorderFactory.createLineBorder(new Color(242, 38, 19), 2));
        cancelButton.addActionListener(this);
        loginPanel.add(cancelButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            try {
                connection c = new connection();
                String user = textField.getText();
                String pass = new String(jpasswordField.getPassword());
                String query = "SELECT * FROM login WHERE ID = '" + user + "' AND PW = '" + pass + "' ";
                ResultSet resultSet = c.statement.executeQuery(query);
                if (resultSet.next()) {
                    new Reception();
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new login();
    }
}