package com.attendance;

import javax.swing.*;
import java.awt.*;

public class AdminLogin extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    // ✅ Constructor - Builds the login UI
    public AdminLogin() {
        this.setTitle("Admin Login");
        this.setSize(350, 220);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(3, 2, 10, 10));

        // Username
        this.add(new JLabel("Username:"));
        usernameField = new JTextField();
        this.add(usernameField);

        // Password
        this.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        this.add(passwordField);

        // Login button
        JButton loginButton = new JButton("Login");
        this.add(new JLabel()); // empty label for spacing
        this.add(loginButton);

        // ⚡ Login button action
        loginButton.addActionListener(e -> login());

        this.setVisible(true);
    }

    // ✅ Login method - check credentials & open dashboard
    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // For testing: admin/admin login
        if(username.equals("admin") && password.equals("admin")) {
            JOptionPane.showMessageDialog(this, "Login Successful");

            // Open main dashboard
            new MainDashboard();

            // Close login window
            this.dispose();

        } else {
            JOptionPane.showMessageDialog(this, "Invalid Credentials");
        }
    }

    // ✅ Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminLogin());
    }
}