package user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import dao.DatabaseConnection;

public class SignupScreen extends JFrame {
    public SignupScreen() {
        setTitle("Đăng ký tài khoản");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tiêu đề
        JLabel title = new JLabel("Đăng ký tài khoản", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));
        add(title, BorderLayout.NORTH);

        // Form đăng ký
        JPanel centeredPanel = new JPanel();
        centeredPanel.setLayout(new BorderLayout());
        centeredPanel.setBorder(BorderFactory.createEmptyBorder(10, 200, 100, 200));

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        formPanel.add(new JLabel("Tên đăng nhập:"));
        JTextField usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Mật khẩu:"));
        JPasswordField passwordField = new JPasswordField();
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Xác nhận mật khẩu:"));
        JPasswordField confirmPasswordField = new JPasswordField();
        formPanel.add(confirmPasswordField);

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
        JComboBox<String> genderComboBox = new JComboBox<>(new String[] { "Nam", "Nữ", "Khác" });
        formPanel.add(genderComboBox);

        // Nút Đăng ký
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        JButton signupButton = new JButton("Đăng ký");
        buttonPanel.add(signupButton, BorderLayout.CENTER);

        centeredPanel.add(formPanel, BorderLayout.CENTER);
        centeredPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(centeredPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);

        // Lắng nghe sự kiện nút Đăng ký
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                String fullname = fullnameField.getText().trim();
                String email = emailField.getText().trim();
                String address = addressField.getText().trim();
                String dob = dobField.getText().trim();
                String gender = genderComboBox.getSelectedItem().toString();

                // Kiểm tra dữ liệu hợp lệ
                if (username.isEmpty() || password.isEmpty() || fullname.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(SignupScreen.this, "Vui lòng điền đầy đủ thông tin.", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(SignupScreen.this, "Mật khẩu xác nhận không khớp.", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Lưu vào cơ sở dữ liệu
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String sql = "INSERT INTO user (username, password, fullname, email, address, dob, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, username);
                    statement.setString(2, password);
                    statement.setString(3, fullname);
                    statement.setString(4, email);
                    statement.setString(5, address);
                    statement.setString(6, dob);
                    statement.setString(7, gender);
                    statement.executeUpdate();

                    JOptionPane.showMessageDialog(SignupScreen.this, "Đăng ký thành công!", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Đóng màn hình đăng ký
                    new LoginScreen().setVisible(true); // Chuyển về màn hình đăng nhập
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(SignupScreen.this, "Lỗi khi lưu thông tin: " + ex.getMessage(), "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SignupScreen signupScreen = new SignupScreen();
            signupScreen.setVisible(true);
        });
    }
}
