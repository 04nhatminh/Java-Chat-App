package presentation;

import bus.UserBUS;
import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {
    public LoginScreen() {
        setTitle("Đăng nhập");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tiêu đề
        JLabel title = new JLabel("Đăng nhập", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));
        add(title, BorderLayout.NORTH);

        // Form đăng nhập
        JPanel centeredPanel = new JPanel();
        centeredPanel.setLayout(new BorderLayout());
        centeredPanel.setBorder(BorderFactory.createEmptyBorder(10, 250, 350, 250));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        formPanel.add(new JLabel("Tên đăng nhập:"));
        JTextField usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Mật khẩu:"));
        JPasswordField passwordField = new JPasswordField();
        formPanel.add(passwordField);

        // Nút Đăng nhập
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        JButton loginButton = new JButton("Đăng nhập");
        buttonPanel.add(loginButton, BorderLayout.CENTER);

        centeredPanel.add(formPanel, BorderLayout.CENTER);
        centeredPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(centeredPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);

        // Thêm sự kiện cho nút Đăng nhập
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            UserBUS userBUS = new UserBUS();
            if (userBUS.checkLogin(username, password)) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu sai!");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
    }
}
