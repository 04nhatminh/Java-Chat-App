import javax.swing.*;
import java.awt.*;

import bus.UserBUS;

public class FriendRequestListScreen extends JFrame {
    private String username;

    public FriendRequestListScreen(String username) {
        this.username = username;
        setTitle("Danh sách yêu cầu kết bạn");
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

        JPanel requestPanel = new JPanel();
        requestPanel.setLayout(new BoxLayout(requestPanel, BoxLayout.Y_AXIS));

        for (int i = 1; i <= 20; i++) {
            JPanel singleRequestPanel = new JPanel(new BorderLayout());
            JLabel requestLabel = new JLabel("Yêu cầu từ bạn số " + i);
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton acceptButton = new JButton("Chấp nhận");
            JButton declineButton = new JButton("Từ chối");

            buttonPanel.add(acceptButton);
            buttonPanel.add(declineButton);

            singleRequestPanel.add(requestLabel, BorderLayout.WEST);
            singleRequestPanel.add(buttonPanel, BorderLayout.EAST);

            requestPanel.add(singleRequestPanel);
        }

        JScrollPane scrollPane = new JScrollPane(requestPanel);

        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Tìm kiếm");

        JPanel topPanel = new JPanel();
        topPanel.add(searchField);
        topPanel.add(searchButton);

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
            FriendRequestListScreen requestList = new FriendRequestListScreen("username"); // Thay "username" bằng tên
                                                                                           // người dùng thực tế
            requestList.setVisible(true);
        });
    }
}