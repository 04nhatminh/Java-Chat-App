import javax.swing.*;
import java.awt.*;

import bus.UserBUS;
import dto.User;

public class UpdateProfileScreen extends JFrame {
    private String username;

    public UpdateProfileScreen(String username) {
        this.username = username;
        setTitle("Cập nhật thông tin tài khoản");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tạo thanh Menu
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Menu");
        JMenuItem chatMenuItem = new JMenuItem("Chat");
        JMenuItem friendListMenuItem = new JMenuItem("Friend List");
        JMenuItem friendRequestListMenuItem = new JMenuItem("Friend Request List");
        JMenuItem updateProfileMenuItem = new JMenuItem("Update Profile");
        JMenuItem logoutMenuItem = new JMenuItem("Đăng xuất");

        menu.add(chatMenuItem);
        menu.add(friendListMenuItem);
        menu.add(friendRequestListMenuItem);
        menu.add(updateProfileMenuItem);
        menu.addSeparator();
        menu.add(logoutMenuItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);

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

        formPanel.add(new JLabel("Ngày sinh (yyyy/mm/dd):"));
        JTextField dobField = new JTextField();
        formPanel.add(dobField);

        formPanel.add(new JLabel("Giới tính:"));
        JComboBox<String> genderComboBox = new JComboBox<>(new String[] { "Nam", "Nữ", "Khác" });
        formPanel.add(genderComboBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        JButton updateButton = new JButton("Cập nhật");
        JButton updatePasswordButton = new JButton("Cập nhật mật khẩu");
        buttonPanel.add(updateButton, BorderLayout.EAST);
        buttonPanel.add(updatePasswordButton, BorderLayout.WEST);

        centeredPanel.add(formPanel, BorderLayout.CENTER);
        centeredPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(centeredPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);

        // Xử lý sự kiện cho các mục Menu
        chatMenuItem.addActionListener(e -> {
            dispose();
            ChatScreen chatScreen = new ChatScreen(username);
            chatScreen.setVisible(true);
        });

        friendListMenuItem.addActionListener(e -> {
            dispose();
            FriendListScreen friendListScreen = new FriendListScreen(username);
            friendListScreen.setVisible(true);
        });

        friendRequestListMenuItem.addActionListener(e -> {
            dispose();
            FriendRequestListScreen friendRequestListScreen = new FriendRequestListScreen(username);
            friendRequestListScreen.setVisible(true);
        });

        updateProfileMenuItem.addActionListener(e -> {
            dispose();
            UpdateProfileScreen updateProfileScreen = new UpdateProfileScreen(username);
            updateProfileScreen.setVisible(true);
        });

        logoutMenuItem.addActionListener(e -> {
            // Xử lý đăng xuất
            UserBUS userBUS = new UserBUS();
            userBUS.logoutUser(username);
            JOptionPane.showMessageDialog(this, "Đăng xuất thành công!");
            dispose();
            // Mở trang đăng nhập
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });

        updateButton.addActionListener(e -> {
            String fullname = fullnameField.getText();
            String email = emailField.getText();
            String address = addressField.getText();
            String dob = dobField.getText();
            String gender = (String) genderComboBox.getSelectedItem();

            // Kiểm tra tính hợp lệ của dữ liệu
            if (fullname.isEmpty() || email.isEmpty() || address.isEmpty() || dob.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo đối tượng User mới
            User user = new User(username, null, fullname, email, address, dob, gender);

            // Cập nhật thông tin người dùng
            UserBUS userBUS = new UserBUS();
            boolean isUpdated = userBUS.updateUser(user);
            if (isUpdated) {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành công!", "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        updatePasswordButton.addActionListener(e -> {
            dispose();
            UpdatePasswordScreen updatePasswordScreen = new UpdatePasswordScreen(username);
            updatePasswordScreen.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UpdateProfileScreen updateProfileScreen = new UpdateProfileScreen("username"); // Thay "username" bằng tên
                                                                                           // người dùng thực tế
            updateProfileScreen.setVisible(true);
        });
    }
}