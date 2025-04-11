package hospital.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.sql.*;

public class Room extends JFrame {
    private JComboBox<String> availabilityCombo, bedTypeCombo;
    private JButton searchButton, nextButton, prevButton;
    private JTable roomTable;
    private DefaultTableModel tableModel;
    private JLabel pageInfoLabel, totalRoomLabel;
    private int currentPage = 1;
    private final int pageSize = 50;
    private int totalRooms = 500;

    Room() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Room Management - Paginated View");
        setSize(900, 600);
        setLayout(null);
        setLocation(300, 230);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 880, 590);
        panel.setBackground(new Color(90, 156, 163));
        panel.setLayout(null);
        add(panel);

        JLabel availabilityLabel = new JLabel("Availability:");
        availabilityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        availabilityLabel.setBounds(30, 20, 100, 30);
        panel.add(availabilityLabel);

        String[] availabilityOptions = {"All", "Available", "Occupied"};
        availabilityCombo = new JComboBox<>(availabilityOptions);
        availabilityCombo.setBounds(130, 20, 120, 30);
        panel.add(availabilityCombo);

        JLabel bedTypeLabel = new JLabel("Bed Type:");
        bedTypeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bedTypeLabel.setBounds(270, 20, 100, 30);
        panel.add(bedTypeLabel);

        String[] bedTypeOptions = {"All", "Single", "Double", "Deluxe"};
        bedTypeCombo = new JComboBox<>(bedTypeOptions);
        bedTypeCombo.setBounds(370, 20, 120, 30);
        panel.add(bedTypeCombo);

        searchButton = new JButton("Search");
        searchButton.setBounds(510, 20, 100, 30);
        panel.add(searchButton);

        String[] columnNames = {"Room No", "Availability", "Bed Type", "Price"};
        tableModel = new DefaultTableModel(columnNames, 0);
        roomTable = new JTable(tableModel);
        roomTable.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(roomTable);
        scrollPane.setBounds(30, 70, 820, 400);
        panel.add(scrollPane);

        prevButton = new JButton("Previous");
        prevButton.setBounds(280, 510, 100, 30);
        prevButton.setEnabled(false);
        panel.add(prevButton);

        nextButton = new JButton("Next");
        nextButton.setBounds(420, 510, 100, 30);
        panel.add(nextButton);

        pageInfoLabel = new JLabel();
        pageInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        pageInfoLabel.setBounds(30, 500, 200, 20);
        panel.add(pageInfoLabel);

        totalRoomLabel = new JLabel("Total Rooms: " + totalRooms);
        totalRoomLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalRoomLabel.setBounds(650, 500, 200, 20);
        panel.add(totalRoomLabel);

        loadRoomData(currentPage);

        searchButton.addActionListener(e -> {
            searchRoom();
        });

        prevButton.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                loadRoomData(currentPage);
            }
        });

        nextButton.addActionListener(e -> {
            currentPage++;
            loadRoomData(currentPage);
        });

        setVisible(true);
    }

    private void loadRoomData(int page) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management", "root", "Bollapooja@2005");
            int offset = (page - 1) * pageSize;
            String query = "SELECT * FROM room1 LIMIT ? OFFSET ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, pageSize);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();

          
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("room_no"),
                    rs.getString("availability"),
                    rs.getString("bed_type"),
                    rs.getString("price")
                });
              
            }

            int totalPages = (int) Math.ceil((double) totalRooms / pageSize);
            pageInfoLabel.setText("Page " + page + " of " + totalPages);

            prevButton.setEnabled(page > 1);
            nextButton.setEnabled(page < totalPages);

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchRoom() {
        String availability = (String) availabilityCombo.getSelectedItem();
        String bedType = (String) bedTypeCombo.getSelectedItem();

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management", "root", "Bollapooja@2005");
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM room1 WHERE 1=1");

            if (!availability.equals("All")) {
                queryBuilder.append(" AND availability = ?");
            }
            if (!bedType.equals("All")) {
                queryBuilder.append(" AND bed_type = ?");
            }

            PreparedStatement stmt = con.prepareStatement(queryBuilder.toString());

            int index = 1;
            if (!availability.equals("All")) {
                stmt.setString(index++, availability);
            }
            if (!bedType.equals("All")) {
                stmt.setString(index++, bedType);
            }

            ResultSet rs = stmt.executeQuery();

            tableModel.setRowCount(0);
            boolean found = false;
            while (rs.next()) {
                found = true;
                tableModel.addRow(new Object[]{
                    rs.getString("room_no"),
                    rs.getString("availability"),
                    rs.getString("bed_type"),
                    rs.getString("price")
                });
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "No matching records found!", "Not Found", JOptionPane.INFORMATION_MESSAGE);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Room();
    }
}
