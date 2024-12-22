import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import bus.UserBUS;
import dao.GroupDAO;
import dao.UserDAO;
import dto.User;

public class CreateGroupScreen extends JFrame {
    private String username;
    private UserDAO userDAO;
    private GroupDAO groupDAO;
    private DefaultListModel<User> friendsListModel;
    private DefaultListModel<User> groupMembersListModel;
    private JList<User> friendsList;
    private JList<User> groupMembersList;

    public CreateGroupScreen(String username) {
        this.username = username;
        this.userDAO = new UserDAO();
        this.groupDAO = new GroupDAO();
        setTitle("Tạo Group - " + username);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tạo thanh Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem homeMenuItem = new JMenuItem("Trang chủ");
        JMenuItem logoutMenuItem = new JMenuItem("Đăng xuất");

        menu.add(homeMenuItem);
        menu.addSeparator();
        menu.add(logoutMenuItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Thêm sự kiện cho các mục trong menu
        homeMenuItem.addActionListener(e -> {
            dispose();
            FriendListScreen friendListScreen = new FriendListScreen(username);
            friendListScreen.setVisible(true);
        });

        logoutMenuItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Đăng xuất thành công!");
            dispose();
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });

        // Tạo danh sách bạn bè
        JPanel friendPanel = new JPanel();
        friendPanel.setLayout(new BoxLayout(friendPanel, BoxLayout.Y_AXIS));

        friendsListModel = new DefaultListModel<>();
        friendsList = new JList<>(friendsListModel);
        friendsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        friendPanel.add(new JScrollPane(friendsList));

        // Tạo danh sách thành viên nhóm
        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));

        groupMembersListModel = new DefaultListModel<>();
        groupMembersList = new JList<>(groupMembersListModel);
        groupMembersList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        groupPanel.add(new JScrollPane(groupMembersList));

        // Nút thêm thành viên
        JButton addButton = new JButton("Thêm thành viên");
        addButton.addActionListener(e -> addGroupMembers());
        friendPanel.add(addButton);

        // Nút gán quyền Admin
        JButton setAdminButton = new JButton("Gán quyền Admin");
        setAdminButton.addActionListener(e -> setAdminRights());
        groupPanel.add(setAdminButton);

        // Thanh tìm kiếm và nút tạo group
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Tìm kiếm");
        JButton createGroupButton = new JButton("Tạo Group");

        JPanel topPanel = new JPanel();
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(createGroupButton);

        getContentPane().add(new JScrollPane(friendPanel), BorderLayout.WEST);
        getContentPane().add(new JScrollPane(groupPanel), BorderLayout.EAST);
        getContentPane().add(topPanel, BorderLayout.NORTH);

        setLocationRelativeTo(null);

        // Thêm sự kiện cho nút tìm kiếm
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText();
            JOptionPane.showMessageDialog(this, "Tìm kiếm: " + searchText, "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // Thêm sự kiện cho nút tạo Group
        createGroupButton.addActionListener(e -> {
            String groupName = JOptionPane.showInputDialog(this, "Nhập tên nhóm:");
            if (groupName != null && !groupName.trim().isEmpty()) {
                try {
                    boolean success = groupDAO.createGroup(groupName, username);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Tạo group thành công!", "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Tạo group thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        loadFriendsList();
    }

    private void loadFriendsList() {
        try {
            List<User> friends = userDAO.getFriends(username);
            friendsListModel.clear();
            for (User friend : friends) {
                friendsListModel.addElement(friend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addGroupMembers() {
        List<User> selectedFriends = friendsList.getSelectedValuesList();
        for (User friend : selectedFriends) {
            if (!groupMembersListModel.contains(friend)) {
                groupMembersListModel.addElement(friend);
            }
        }
    }

    private void setAdminRights() {
        List<User> selectedMembers = groupMembersList.getSelectedValuesList();
        for (User member : selectedMembers) {
            // Gán quyền Admin cho thành viên
            // Bạn có thể thêm logic để lưu thông tin quyền Admin vào cơ sở dữ liệu nếu cần
            System.out.println("Gán quyền Admin cho: " + member.getFullname());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CreateGroupScreen createGroupScreen = new CreateGroupScreen("username"); // Truyền username
            createGroupScreen.setVisible(true);
        });
    }
}