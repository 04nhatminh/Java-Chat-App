import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import bus.UserBUS;
import dto.User;

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

        formPanel.add(new JLabel("Ngày sinh (yyyy/mm/dd):"));
        JTextField dobField = new JTextField();
        formPanel.add(dobField);

        formPanel.add(new JLabel("Giới tính:"));
        JComboBox<String> genderComboBox = new JComboBox<>(new String[] { "Nam", "Nữ", "Khác" });
        formPanel.add(genderComboBox);

        // Nút Đăng ký
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));
        JButton signupButton = new JButton("Đăng ký");
        JButton loginButton = new JButton("Chuyển đến Đăng nhập");
        buttonPanel.add(signupButton);
        buttonPanel.add(loginButton);

        centeredPanel.add(formPanel, BorderLayout.CENTER);
        centeredPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(centeredPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);

        // Xử lý sự kiện đăng ký
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                String name = fullnameField.getText();
                String email = emailField.getText();
                String address = addressField.getText();
                String dob = dobField.getText();
                String gender = (String) genderComboBox.getSelectedItem();

                // Kiểm tra các trường thông tin
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() ||
                        email.isEmpty() || address.isEmpty() || dob.isEmpty() || gender.isEmpty()) {
                    JOptionPane.showMessageDialog(SignupScreen.this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(SignupScreen.this, "Mật khẩu xác nhận không khớp!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                User newUser = new User(username, password, name, email, address, dob, gender, null);

                try {
                    UserBUS userBUS = new UserBUS();
                    if (userBUS.registerUser(newUser)) {
                        JOptionPane.showMessageDialog(SignupScreen.this, "Đăng ký thành công!", "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        // Mở trang đăng nhập
                        LoginScreen loginScreen = new LoginScreen();
                        loginScreen.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(SignupScreen.this, "Đăng ký thất bại!", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(SignupScreen.this, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Xử lý sự kiện cho nút "Chuyển đến Đăng nhập"
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setVisible(true);
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