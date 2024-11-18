package user;

import javax.swing.*;
import java.awt.*;

public class UpdateProfileScreen extends JFrame {
    public UpdateProfileScreen() {
        setTitle("Cập nhật thông tin tài khoản");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Cập nhật thông tin tài khoản", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(120, 0, 0, 0));
        add(title, BorderLayout.NORTH);

        JPanel centeredPanel = new JPanel();
        centeredPanel.setLayout(new BorderLayout());
        centeredPanel.setBorder(BorderFactory.createEmptyBorder(10, 200, 200, 200));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        formPanel.add(new JLabel("Họ tên:"));
        JTextField fullnameField = new JTextField();
        formPanel.add(fullnameField);

        formPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Địa chỉ:"));
        JTextField addressField = new JTextField();
        formPanel.add(addressField);

        formPanel.add(new JLabel("Ngày sinh (dd/mm/yyyy):"));
        JTextField dobField = new JTextField();
        formPanel.add(dobField);

        formPanel.add(new JLabel("Giới tính:"));
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        formPanel.add(genderComboBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        JButton signupButton = new JButton("Cập nhật");
        JButton updatePasswordButton = new JButton("Cập nhật mật khẩu");
        buttonPanel.add(signupButton, BorderLayout.EAST);
        buttonPanel.add(updatePasswordButton, BorderLayout.WEST);

        centeredPanel.add(formPanel, BorderLayout.CENTER);
        centeredPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(centeredPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UpdateProfileScreen updateProfileScreen = new UpdateProfileScreen();
            updateProfileScreen.setVisible(true);
        });
    }
}
