package com.attendance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.sql.*;

public class MarkAttendance extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JButton saveBtn;
    private JButton viewBtn;

    private Connection con;

    public MarkAttendance() {
        setTitle("Mark Attendance");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ✅ Table Columns
        String[] columns = {"ID", "Name", "Class", "Present", "Absent"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if(columnIndex == 3 || columnIndex == 4) return Boolean.class; // Checkbox
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 4; // Only Present/Absent editable
            }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // ✅ Make checkboxes mutually exclusive
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int col = e.getColumn();

                if(col == 3 && Boolean.TRUE.equals(model.getValueAt(row, 3))) {
                    model.setValueAt(false, row, 4);
                } else if(col == 4 && Boolean.TRUE.equals(model.getValueAt(row, 4))) {
                    model.setValueAt(false, row, 3);
                }
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ✅ Buttons
        saveBtn = new JButton("Save Attendance");
        saveBtn.addActionListener(e -> saveAttendance());

        viewBtn = new JButton("View Attendance");
        viewBtn.addActionListener(e -> new ViewAttendance());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(saveBtn);
        bottomPanel.add(viewBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // ✅ Load students
        loadStudents();

        setVisible(true);
    }

    private void loadStudents() {
        try {
            con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students"); // make sure columns: id, name, class

            model.setRowCount(0);

            while(rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("class"),
                        false,
                        false
                });
            }

        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading students: " + e.getMessage());
        }
    }

    private void saveAttendance() {
        try {
            PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO attendance(student_id, date, status) VALUES(?, CURDATE(), ?)"
            );

            for(int i = 0; i < model.getRowCount(); i++) {
                int studentId = (int) model.getValueAt(i, 0);
                boolean present = (boolean) model.getValueAt(i, 3);
                boolean absent = (boolean) model.getValueAt(i, 4);

                String status = null;
                if(present) status = "Present";
                else if(absent) status = "Absent";

                if(status != null) {
                    pst.setInt(1, studentId);
                    pst.setString(2, status);
                    pst.addBatch();
                }
            }

            pst.executeBatch();
            JOptionPane.showMessageDialog(this, "Attendance Saved Successfully!");

        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving attendance: " + e.getMessage());
        }
    }

    @Override
    public void dispose() {
        try {
            if(con != null) con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        super.dispose();
    }
}