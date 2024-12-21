import javax.swing.*;
import java.awt.*;

import bus.UserBUS;

public class FriendListScreen extends JFrame {
    private String username;

    public FriendListScreen(String username) {
        this.username = username;
        setTitle("Danh sách bạn bè");
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

        JPanel friendPanel = new JPanel();
        friendPanel.setLayout(new BoxLayout(friendPanel, BoxLayout.Y_AXIS));

        for (int i = 1; i <= 20; i++) {
            JPanel singleFriendPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            JLabel friendLabel = new JLabel("Bạn số " + i);
            JLabel statusLabel = new JLabel(i % 2 == 0 ? " Online" : "Offline");
            JButton removeButton = new JButton("Hủy kết bạn");
            JButton blockButton = new JButton("Chặn");
            JButton reportButton = new JButton("Báo cáo Spam");

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 1.0;
            singleFriendPanel.add(friendLabel, gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.weightx = 0.5;
            singleFriendPanel.add(statusLabel, gbc);

            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.weightx = 0.5;
            singleFriendPanel.add(removeButton, gbc);

            gbc.gridx = 3;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.weightx = 0.5;
            singleFriendPanel.add(reportButton, gbc);

            gbc.gridx = 4;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.weightx = 0.5;
            singleFriendPanel.add(blockButton, gbc);

            friendPanel.add(singleFriendPanel);
        }

        JScrollPane scrollPane = new JScrollPane(friendPanel);

        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Tìm kiếm");
        JButton filterButton = new JButton("Lọc theo tên");
        JButton addButton = new JButton("Yêu cầu kết bạn");
        JButton createGroupButton = new JButton("Tạo Group");

        JPanel topPanel = new JPanel();
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(filterButton);
        topPanel.add(addButton);
        topPanel.add(createGroupButton);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(topPanel, BorderLayout.NORTH);

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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FriendListScreen friendList = new FriendListScreen("username"); // Thay "username" bằng tên người dùng thực
                                                                            // tế
            friendList.setVisible(true);
        });
    }
}