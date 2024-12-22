import javax.swing.*;
import java.awt.*;
import bus.UserBUS;
import utils.EmailSender;

public class UpdatePasswordScreen extends JFrame {
    private String username;

    public UpdatePasswordScreen(String username) {
        this.username = username;
        setTitle("Cập nhật mật khẩu");
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
        JButton updateButton = new JButton("Cập nhật");
        JButton resetPasswordButton = new JButton("Khởi tạo mật khẩu");
        buttonPanel.add(updateButton, BorderLayout.EAST);
        buttonPanel.add(resetPasswordButton, BorderLayout.WEST);

        centeredPanel.add(formPanel, BorderLayout.CENTER);
        centeredPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(centeredPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);

        // Thêm sự kiện cho nút "Cập nhật"
        updateButton.addActionListener(e -> {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmNewPassword = new String(confirmNewPasswordField.getPassword());

            // Kiểm tra tính hợp lệ của dữ liệu
            if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPassword.equals(confirmNewPassword)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu mới và xác nhận mật khẩu không khớp!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cập nhật mật khẩu
            UserBUS userBUS = new UserBUS();
            boolean isUpdated = userBUS.updatePassword(username, oldPassword, newPassword);
            if (isUpdated) {
                JOptionPane.showMessageDialog(this, "Cập nhật mật khẩu thành công!", "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không đúng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Thêm sự kiện cho nút "Khởi tạo mật khẩu"
        resetPasswordButton.addActionListener(e -> {
            try {
                // Tạo mật khẩu ngẫu nhiên
                String newPassword = generateRandomPassword();

                // Cập nhật mật khẩu trong cơ sở dữ liệu
                UserBUS userBUS = new UserBUS();
                boolean isUpdated = userBUS.updatePassword(username, newPassword);
                // if (isUpdated) {
                // // Gửi email cho người dùng
                // String email = userBUS.getUserEmail(username);
                // if (email == null || email.isEmpty()) {
                // JOptionPane.showMessageDialog(this, "Không tìm thấy email của người dùng!",
                // "Lỗi",
                // JOptionPane.ERROR_MESSAGE);
                // return;
                // }

                // String subject = "Mật khẩu mới của bạn";
                // String content = "Mật khẩu mới của bạn là: " + newPassword;

                // EmailSender.sendEmail(email, subject, content);
                // JOptionPane.showMessageDialog(this, "Mật khẩu mới đã được gửi đến email của
                // bạn!", "Thành công",
                // JOptionPane.INFORMATION_MESSAGE);
                // } else {
                // JOptionPane.showMessageDialog(this, "Khởi tạo mật khẩu thất bại!", "Lỗi",
                // JOptionPane.ERROR_MESSAGE);
                // }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi gửi email: " + ex.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

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
    }

    private String generateRandomPassword() {
        return Long.toHexString(Double.doubleToLongBits(Math.random())).substring(0, 6);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UpdatePasswordScreen updatePasswordScreen = new UpdatePasswordScreen("username");
            updatePasswordScreen.setVisible(true);
        });
    }
}
