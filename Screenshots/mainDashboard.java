package com.attendance;

import javax.swing.*;
import java.awt.*;

public class MainDashboard extends JFrame {

    public MainDashboard() {

        setTitle("Admin Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton markBtn = new JButton("Mark Attendance");
        JButton viewBtn = new JButton("View Attendance");

        markBtn.addActionListener(e -> new MarkAttendance());
        viewBtn.addActionListener(e -> new ViewAttendance());

        setLayout(new FlowLayout());
        add(markBtn);
        add(viewBtn);

        setVisible(true);
    }

}
