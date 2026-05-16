package com.attendance;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewAttendance extends JFrame {

    JTextField dateField;
    JTable table;
    DefaultTableModel model;

    public ViewAttendance() {

        setTitle("View Attendance");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔹 Top Panel
        JPanel topPanel = new JPanel();

        topPanel.add(new JLabel("Enter Date (YYYY-MM-DD):"));

        dateField = new JTextField(10);
        topPanel.add(dateField);

        JButton viewBtn = new JButton("View");
        topPanel.add(viewBtn);

        add(topPanel, BorderLayout.NORTH);

        // 🔹 Table
        model = new DefaultTableModel();
        model.addColumn("Student ID");
        model.addColumn("Name");
        model.addColumn("Class");
        model.addColumn("Status");
        model.addColumn("Date");

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        viewBtn.addActionListener(e -> loadAttendance());

        setVisible(true);
    }

    private void loadAttendance() {

        String date = dateField.getText();

        model.setRowCount(0); // clear old data

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT s.id, s.name, s.class, a.status, a.date " +
                "FROM attendance a " +
                "JOIN students s ON a.student_id = s.id " +
                "WHERE a.date = ?"
            );

            ps.setString(1, date);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("class"),
                        rs.getString("status"),
                        rs.getDate("date")
                });
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}