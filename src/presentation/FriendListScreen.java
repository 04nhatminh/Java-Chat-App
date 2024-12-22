import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import bus.UserBUS;
import dto.User;

public class FriendListScreen extends JFrame {
    private String username;
    private JPanel friendPanel;
    private List<User> friends;
    private UserBUS userBUS; // Khai báo biến toàn cục

    public FriendListScreen(String username) {
        this.username = username;
        this.userBUS = new UserBUS(); // Khởi tạo biến toàn cục
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

        friendPanel = new JPanel();
        friendPanel.setLayout(new BoxLayout(friendPanel, BoxLayout.Y_AXIS));

        friends = userBUS.getFriends(username);

        displayFriends(friends);

        JScrollPane scrollPane = new JScrollPane(friendPanel);

        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Tìm kiếm");
        JButton filterButton = new JButton("Lọc theo tên");
        JButton createGroupButton = new JButton("Tạo Group");

        JPanel topPanel = new JPanel();
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(filterButton);
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
            UserBUS userBUSLogout = new UserBUS(); // Đổi tên biến để tránh xung đột
            userBUSLogout.logoutUser(username);
            JOptionPane.showMessageDialog(this, "Đăng xuất thành công!");
            dispose();
            // Mở trang đăng nhập
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });

        // Thêm sự kiện cho nút "Lọc theo tên"
        filterButton.addActionListener(e -> {
            String searchText = searchField.getText().toLowerCase();
            List<User> filteredFriends = friends.stream()
                    .filter(friend -> friend.getFullname().toLowerCase().contains(searchText))
                    .collect(Collectors.toList());
            displayFriends(filteredFriends);
        });

        // Thêm sự kiện cho nút "Tìm kiếm"
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().toLowerCase();

            // Tìm kiếm người dùng
            User foundUser = userBUS.findUserByName(searchText, username);
            if (foundUser != null) {
                // Xóa nội dung trong friendPanel
                friendPanel.removeAll();
                displaySearchResult(foundUser); // Hiển thị kết quả tìm kiếm
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy người dùng!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            // Làm mới giao diện
            friendPanel.revalidate();
            friendPanel.repaint();
        });

        createGroupButton.addActionListener(e -> {
            dispose();
            CreateGroupScreen createGroupScreen = new CreateGroupScreen(username);
            createGroupScreen.setVisible(true);
        });
    }

    private void displayFriends(List<User> friends) {
        friendPanel.removeAll();
        if (friends != null) {
            for (User friend : friends) {
                JPanel singleFriendPanel = new JPanel(new GridBagLayout());
                singleFriendPanel.setBorder(new LineBorder(Color.BLACK)); // Thêm đường viền ngoài
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);

                JLabel friendLabel = new JLabel(friend.getFullname());
                JLabel statusLabel = new JLabel(friend.getStatus().equals("onl") ? " Online" : "Offline");
                JButton removeButton = new JButton("Hủy kết bạn");
                JButton blockButton = new JButton("Chặn");

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

                gbc.gridx = 4;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.EAST;
                gbc.weightx = 0.5;
                singleFriendPanel.add(blockButton, gbc);

                friendPanel.add(singleFriendPanel);

                // Thêm sự kiện cho nút "Hủy kết bạn"
                removeButton.addActionListener(e -> {
                    int confirm = JOptionPane.showConfirmDialog(this,
                            "Bạn có chắc chắn muốn hủy kết bạn với " + friend.getFullname() + "?", "Xác nhận",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean success = userBUS.unfriend(username, friend.getUsername());
                        if (success) {
                            JOptionPane.showMessageDialog(this, "Đã hủy kết bạn với " + friend.getFullname());
                            friendPanel.remove(singleFriendPanel);
                            friendPanel.revalidate();
                            friendPanel.repaint();
                        } else {
                            JOptionPane.showMessageDialog(this, "Hủy kết bạn thất bại!", "Lỗi",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                // Thêm sự kiện cho nút "Chặn"
                blockButton.addActionListener(e -> {
                    int confirm = JOptionPane.showConfirmDialog(this,
                            "Bạn có chắc chắn muốn chặn " + friend.getFullname() + "?", "Xác nhận",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean success = userBUS.blockUser(username, friend.getUsername());
                        if (success) {
                            JOptionPane.showMessageDialog(this, "Đã chặn " + friend.getFullname());
                            friendPanel.remove(singleFriendPanel);
                            friendPanel.revalidate();
                            friendPanel.repaint();
                        } else {
                            JOptionPane.showMessageDialog(this, "Chặn thất bại!", "Lỗi",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            }
        }
        friendPanel.revalidate();
        friendPanel.repaint();
    }

    private void displaySearchResult(User user) {
        JPanel searchResultPanel = new JPanel(new GridBagLayout());
        searchResultPanel.setBorder(new LineBorder(Color.BLACK)); // Thêm đường viền ngoài
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel userLabel = new JLabel(user.getFullname());
        JLabel statusLabel = new JLabel(user.getStatus().equals("onl") ? " Online" : "Offline");
        JButton addButton = new JButton("Kết bạn");
        JButton blockButton = new JButton("Chặn");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        searchResultPanel.add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.5;
        searchResultPanel.add(statusLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.5;
        searchResultPanel.add(addButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.5;
        searchResultPanel.add(blockButton, gbc);

        friendPanel.add(searchResultPanel);
        friendPanel.revalidate();
        friendPanel.repaint();

        // Thêm sự kiện cho nút "Kết bạn"
        addButton.addActionListener(e -> {
            boolean success = userBUS.sendFriendRequest(username, user.getUsername());
            if (success) {
                JOptionPane.showMessageDialog(this, "Đã gửi yêu cầu kết bạn tới " + user.getFullname());
            } else {
                JOptionPane.showMessageDialog(this, "Gửi yêu cầu kết bạn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Thêm sự kiện cho nút "Chặn"
        blockButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn chặn " + user.getFullname() + "?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = userBUS.blockUser(username, user.getUsername());
                if (success) {
                    JOptionPane.showMessageDialog(this, "Đã chặn " + user.getFullname());
                    friendPanel.remove(searchResultPanel);
                    friendPanel.revalidate();
                    friendPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(this, "Chặn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
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