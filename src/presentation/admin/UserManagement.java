package admin;

import javax.swing.*;
import java.awt.*;

public class UserManagement extends JFrame {
    public UserManagement() {
        setTitle("Quản lý người dùng");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel userListPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JPanel functionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        functionPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));

        JButton addButton = new JButton("Thêm người dùng");
        JButton updateButton = new JButton("Cập nhật người dùng");
        JButton deleteButton = new JButton("Xóa người dùng");
        JButton lockButton = new JButton("Khóa tài khoản");
        JButton unlockButton = new JButton("Mở khóa tài khoản");
        JButton updatePasswordButton = new JButton("Cập nhật mật khẩu");
        JButton viewLoginHistoryButton = new JButton("Xem lịch sử đăng nhập");
        JButton viewFriendsButton = new JButton("Danh sách bạn bè");

        functionPanel.add(addButton);
        functionPanel.add(updateButton);
        functionPanel.add(deleteButton);
        functionPanel.add(lockButton);
        functionPanel.add(unlockButton);
        functionPanel.add(updatePasswordButton);
        functionPanel.add(viewLoginHistoryButton);
        functionPanel.add(viewFriendsButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        userListPanel.add(new JLabel("Tên đăng nhập"), gbc);
        gbc.gridx = 1;
        userListPanel.add(new JLabel("Họ tên"), gbc);
        gbc.gridx = 2;
        userListPanel.add(new JLabel("Địa chỉ"), gbc);
        gbc.gridx = 3;
        userListPanel.add(new JLabel("Ngày sinh"), gbc);
        gbc.gridx = 4;
        userListPanel.add(new JLabel("Giới tính"), gbc);
        gbc.gridx = 5;
        userListPanel.add(new JLabel("Email"), gbc);

        for (int i = 1; i <= 30; i++) {
            gbc.gridy = i;
            gbc.gridx = 0;
            JLabel usernameLabel = new JLabel("Tên đăng nhập " + i);
            usernameLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
            userListPanel.add(usernameLabel, gbc);

            gbc.gridx = 1;
            JLabel fullNameLabel = new JLabel("Họ tên " + i);
            fullNameLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
            userListPanel.add(fullNameLabel, gbc);

            gbc.gridx = 2;
            JLabel addressLabel = new JLabel("Địa chỉ " + i);
            addressLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
            userListPanel.add(addressLabel, gbc);

            gbc.gridx = 3;
            JLabel dobLabel = new JLabel("Ngày sinh " + i);
            dobLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
            userListPanel.add(dobLabel, gbc);

            gbc.gridx = 4;
            JLabel genderLabel = new JLabel("Giới tính " + i);
            genderLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
            userListPanel.add(genderLabel, gbc);

            gbc.gridx = 5;
            JLabel emailLabel = new JLabel("Email " + i);
            emailLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
            userListPanel.add(emailLabel, gbc);
        }

        JScrollPane userListScrollPane = new JScrollPane(userListPanel);
        userListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        userListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        getContentPane().add(functionPanel, BorderLayout.NORTH);
        getContentPane().add(userListScrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserManagement userManagement = new UserManagement();
            userManagement.setVisible(true);
        });
    }
}
