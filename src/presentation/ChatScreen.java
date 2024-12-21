import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import bus.UserBUS;
import dao.ChatDAO;
import dao.UserDAO;
import dto.Message;

public class ChatScreen extends JFrame {
    private String username;
    private ChatDAO chatDAO;
    private UserDAO userDAO;
    private JTextArea chatArea;
    private JLabel chatWithLabel;

    public ChatScreen(String username) {
        this.username = username;
        this.chatDAO = new ChatDAO();
        this.userDAO = new UserDAO();
        setTitle("Chat Application");
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

        // Panel danh sách chat
        JPanel chatListPanel = new JPanel();
        chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));
        chatListPanel.setPreferredSize(new Dimension(250, 600));

        JButton createGroupButton = new JButton("Tạo nhóm");
        chatListPanel.add(createGroupButton);
        chatListPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Khoảng cách cố định

        try {
            List<String> chatList = chatDAO.getChatList(username);
            for (String chatWith : chatList) {
                JPanel singleChatPanel = new JPanel(new BorderLayout());
                singleChatPanel.setPreferredSize(new Dimension(400, 20)); // Chiều cao cố định

                // Tạo LineBorder màu đen
                LineBorder lineBorder = new LineBorder(Color.BLACK, 1);

                // Tạo EmptyBorder
                EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);

                // Kết hợp LineBorder và EmptyBorder
                singleChatPanel.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));

                String friendName = userDAO.getNameByUsername(chatWith); // Lấy tên người dùng
                JLabel friendLabel = new JLabel(friendName);
                JLabel lastMessageLabel = new JLabel(chatDAO.getLastMessage(username, chatWith));
                singleChatPanel.add(friendLabel, BorderLayout.NORTH);
                singleChatPanel.add(lastMessageLabel, BorderLayout.SOUTH);
                chatListPanel.add(singleChatPanel);
                chatListPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Khoảng cách cố định

                singleChatPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        loadChat(chatWith);
                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JScrollPane chatListScrollPane = new JScrollPane(chatListPanel);

        // Panel chat
        JPanel chatPanel = new JPanel(new BorderLayout());

        JPanel chatHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chatWithLabel = new JLabel("Chọn một cuộc trò chuyện");
        JButton oldMessagesButton = new JButton("Tra cứu tin nhắn cũ");
        JButton reportSpamButton = new JButton("Báo cáo spam");

        chatHeaderPanel.add(chatWithLabel);
        chatHeaderPanel.add(oldMessagesButton);
        chatHeaderPanel.add(reportSpamButton);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);

        JPanel messagePanel = new JPanel(new BorderLayout());
        JTextField messageField = new JTextField();
        JButton sendButton = new JButton("Gửi");

        messagePanel.add(messageField, BorderLayout.CENTER);
        messagePanel.add(sendButton, BorderLayout.EAST);

        chatPanel.add(chatHeaderPanel, BorderLayout.NORTH);
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);
        chatPanel.add(messagePanel, BorderLayout.SOUTH);

        getContentPane().add(chatListScrollPane, BorderLayout.WEST);
        getContentPane().add(chatPanel, BorderLayout.CENTER);

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

        sendButton.addActionListener(e -> {
            String content = messageField.getText();
            if (!content.isEmpty()) {
                try {
                    // Tạo tin nhắn mới
                    Message message = new Message(username, chatWithLabel.getText().replace("Đang chat với: ", ""),
                            content, new java.sql.Timestamp(System.currentTimeMillis()).toString());
                    boolean isInserted = chatDAO.insertMessage(message);
                    if (isInserted) {
                        // Hiển thị tin nhắn mới lên màn hình chat
                        chatArea.append(username + ": " + content + "\n");
                        messageField.setText(""); // Xóa nội dung trong trường nhập liệu
                    } else {
                        JOptionPane.showMessageDialog(this, "Gửi tin nhắn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void loadChat(String chatWith) {
        try {
            String name = userDAO.getNameByUsername(chatWith);
            chatWithLabel.setText("Đang chat với: " + name);
            List<Message> messages = chatDAO.getMessages(username, chatWith);
            chatArea.setText("");
            for (Message message : messages) {
                String senderName = userDAO.getNameByUsername(message.getSender());
                chatArea.append(senderName + ": " + message.getContent() + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatScreen chatScreen = new ChatScreen("username"); // Thay "username" bằng tên người dùng thực tế
            chatScreen.setVisible(true);
        });
    }
}