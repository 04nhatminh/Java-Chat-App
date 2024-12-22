import javax.swing.*;
import java.awt.*;
import java.util.List;
import bus.UserBUS;
import dto.User;

public class FriendRequestListScreen extends JFrame {
    private String username;
    private UserBUS userBUS;
    private JPanel requestPanel;
    private JTextField searchField;

    public FriendRequestListScreen(String username) {
        this.username = username;
        this.userBUS = new UserBUS();
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

        requestPanel = new JPanel();
        requestPanel.setLayout(new BoxLayout(requestPanel, BoxLayout.Y_AXIS));

        loadFriendRequests();

        JScrollPane scrollPane = new JScrollPane(requestPanel);

        searchField = new JTextField(15);
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
            userBUS.logoutUser(username);
            JOptionPane.showMessageDialog(this, "Đăng xuất thành công!");
            dispose();
            // Mở trang đăng nhập
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });

        // Xử lý sự kiện cho nút tìm kiếm
        searchButton.addActionListener(e -> filterFriendRequests());
    }

    private void loadFriendRequests() {
        List<User> requests = userBUS.getFriendRequests(username);
        displayFriendRequests(requests);
    }

    private void filterFriendRequests() {
        String searchText = searchField.getText().trim().toLowerCase();
        List<User> requests = userBUS.getFriendRequests(username);
        List<User> filteredRequests = requests.stream()
                .filter(user -> user.getFullname().toLowerCase().contains(searchText))
                .toList();
        displayFriendRequests(filteredRequests);
    }

    private void displayFriendRequests(List<User> requests) {
        requestPanel.removeAll();
        if (requests != null) {
            for (User request : requests) {
                JPanel singleRequestPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần

                JLabel requestLabel = new JLabel("Yêu cầu từ: " + request.getFullname());
                JButton acceptButton = new JButton("Chấp nhận");
                JButton declineButton = new JButton("Từ chối");

                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.WEST;
                singleRequestPanel.add(requestLabel, gbc);

                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.EAST;
                singleRequestPanel.add(acceptButton, gbc);

                gbc.gridx = 2;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.EAST;
                singleRequestPanel.add(declineButton, gbc);

                requestPanel.add(singleRequestPanel);

                // Thêm sự kiện cho nút "Chấp nhận"
                acceptButton.addActionListener(e -> {
                    boolean success = userBUS.acceptFriendRequest(username, request.getUsername());
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Đã chấp nhận yêu cầu kết bạn từ " + request.getFullname());
                        requestPanel.remove(singleRequestPanel);
                        requestPanel.revalidate();
                        requestPanel.repaint();
                    } else {
                        JOptionPane.showMessageDialog(this, "Chấp nhận yêu cầu kết bạn thất bại!", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });

                // Thêm sự kiện cho nút "Từ chối"
                declineButton.addActionListener(e -> {
                    boolean success = userBUS.declineFriendRequest(username, request.getUsername());
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Đã từ chối yêu cầu kết bạn từ " + request.getFullname());
                        requestPanel.remove(singleRequestPanel);
                        requestPanel.revalidate();
                        requestPanel.repaint();
                    } else {
                        JOptionPane.showMessageDialog(this, "Từ chối yêu cầu kết bạn thất bại!", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });
            }
        }
        requestPanel.revalidate();
        requestPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FriendRequestListScreen requestList = new FriendRequestListScreen("username"); // Thay "username" bằng tên
                                                                                           // người dùng thực tế
            requestList.setVisible(true);
        });
    }
}