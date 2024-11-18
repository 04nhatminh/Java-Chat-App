package user;

import javax.swing.*;
import java.awt.*;

public class UpdatePasswordScreen extends JFrame {
    public UpdatePasswordScreen() {
        setTitle("Cập nhật mật khẩu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Cập nhật mật khẩu", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        add(title, BorderLayout.NORTH);

        JPanel centeredPanel = new JPanel();
        centeredPanel.setLayout(new BorderLayout());
        centeredPanel.setBorder(BorderFactory.createEmptyBorder(10, 250, 300, 250));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        formPanel.add(new JLabel("Mật khẩu hiện tại:"));
        JPasswordField oldPasswordField = new JPasswordField();
        formPanel.add(oldPasswordField);

        formPanel.add(new JLabel("Mật khẩu mới:"));
        JPasswordField newPasswordField = new JPasswordField();
        formPanel.add(newPasswordField);

        formPanel.add(new JLabel("Xác nhận mật khẩu mới:"));
        JPasswordField confirmNewPasswordField = new JPasswordField();
        formPanel.add(confirmNewPasswordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        JButton signupButton = new JButton("Cập nhật");
        JButton resetPasswordButton = new JButton("Khởi tạo mật khẩu");
        buttonPanel.add(signupButton, BorderLayout.EAST);
        buttonPanel.add(resetPasswordButton, BorderLayout.WEST);

        centeredPanel.add(formPanel, BorderLayout.CENTER);
        centeredPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(centeredPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UpdatePasswordScreen updatePasswordScreen = new UpdatePasswordScreen();
            updatePasswordScreen.setVisible(true);
        });
    }
}
