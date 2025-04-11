package hospital.management.system;

import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class Department extends JFrame {

    private JTable departmentTable;
    private DefaultTableModel tableModel;

    public Department() {
        // Set FlatLaf theme
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading FlatLaf theme: " + e.getMessage(), "Look and Feel Error", JOptionPane.ERROR_MESSAGE);
        }

        setTitle("Hospital Departments");
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window

        // Sample Department Data
        Map<String, String> departments = new HashMap<>();
        departments.put("Cardiology", "08852-28288");
        departments.put("Neurology", "08852-289687");
        departments.put("Orthopedics", "08852-284747");
        departments.put("General Medicine", "08852-289636");
        departments.put("Pediatrics", "08852-281401");
        departments.put("Oncology", "08852-287358");
        departments.put("Surgery", "08852-289638");
        departments.put("Emergency", "08852-289878");
        departments.put("Radiology", "08852-281474");
        departments.put("Pathology", "08852-284545");
        departments.put("Urology", "08852-289090");
        departments.put("Gastroenterology", "08852-280010");
        departments.put("ENT (Otolaryngology)", "08852-286633");
        departments.put("Ophthalmology", "08852-287991");
        departments.put("Dermatology", "08852-280000");
        departments.put("Psychiatry", "08852-287788");
        departments.put("Pulmonology", "08852-281032");
        departments.put("Nephrology", "08852-287777");
        departments.put("Endocrinology", "08852-289663");
        departments.put("Anesthesiology", "08852-287994");

        // Table Model
        String[] columnNames = {"Department Name", "Phone Number"};
        tableModel = new DefaultTableModel(columnNames, 0);
        for (Map.Entry<String, String> entry : departments.entrySet()) {
            tableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }

        // Table
        departmentTable = new JTable(tableModel);
        departmentTable.setEnabled(false); // Make it non-editable

        // Set custom cell renderer for better appearance
        departmentTable.setDefaultRenderer(Object.class, new DepartmentTableCellRenderer());

        // Set preferred column widths
        departmentTable.getColumnModel().getColumn(0).setPreferredWidth(250);
        departmentTable.getColumnModel().getColumn(1).setPreferredWidth(150);

        // Header appearance
        JTableHeader header = departmentTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(220, 230, 241)); // Light gray-blue
        header.setForeground(new Color(51, 51, 51)); // Dark gray

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(departmentTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add some padding

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245)); // Light background
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(204, 204, 204)), // Light border
                BorderFactory.createEmptyBorder(15, 15, 15, 15) // Padding
        ));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    // Custom Table Cell Renderer for better appearance
    static class DepartmentTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            c.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            if (isSelected) {
                c.setBackground(new Color(179, 215, 243)); // Light blue selection
                c.setForeground(Color.BLACK);
            } else {
                c.setBackground(row % 2 == 0 ? new Color(250, 250, 250) : Color.WHITE); // Alternating row colors
                c.setForeground(Color.BLACK);
            }
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Add padding within cells
            return c;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Department::new);
    }
}