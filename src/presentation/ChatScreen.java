import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import bus.UserBUS;
import dao.ChatDAO;
import dao.UserDAO;
import dto.Message;
import dto.User;

public class ChatScreen extends JFrame {
    private String username;
    private ChatDAO chatDAO;
    private UserDAO userDAO;
    private JTextArea chatArea;
    private JLabel chatWithLabel;
    private String currentChatWith;
    private String lastTimestamp;

    public ChatScreen(String username) {
        this.username = username;
        this.chatDAO = new ChatDAO();
        this.userDAO = new UserDAO();
        setTitle("Chat Application");
        setSize(1000, 600);
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
            List<User> friends = userDAO.getFriends(username);
            for (User friend : friends) {
                JPanel singleChatPanel = new JPanel(new BorderLayout());
                singleChatPanel.setPreferredSize(new Dimension(400, 20)); // Chiều cao cố định

                // Tạo LineBorder màu đen
                LineBorder lineBorder = new LineBorder(Color.BLACK, 1);

                // Tạo EmptyBorder
                EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);

                // Kết hợp LineBorder và EmptyBorder
                singleChatPanel.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));

                JLabel friendLabel = new JLabel(friend.getFullname());
                JLabel lastMessageLabel = new JLabel(chatDAO.getLastMessage(username, friend.getUsername()));
                singleChatPanel.add(friendLabel, BorderLayout.NORTH);
                singleChatPanel.add(lastMessageLabel, BorderLayout.SOUTH);
                chatListPanel.add(singleChatPanel);
                chatListPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Khoảng cách cố định

                singleChatPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        loadChat(friend.getUsername());
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
        chatWithLabel = new JLabel("");
        JButton reportSpamButton = new JButton("Báo cáo spam");
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Tìm kiếm");
        // Thêm nút xóa toàn bộ lịch sử chat
        JButton deleteAllButton = new JButton("Xóa toàn bộ lịch sử chat");
        JButton globalSearchButton = new JButton("Tìm kiếm toàn bộ");

        chatHeaderPanel.add(chatWithLabel);
        chatHeaderPanel.add(reportSpamButton);
        chatHeaderPanel.add(deleteAllButton);
        chatHeaderPanel.add(searchField);
        chatHeaderPanel.add(searchButton);
        chatHeaderPanel.add(globalSearchButton);

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

        deleteAllButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa toàn bộ lịch sử chat?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    boolean success = chatDAO.deleteAllMessages(username, currentChatWith);
                    if (success) {
                        chatArea.setText("");
                        JOptionPane.showMessageDialog(this, "Đã xóa toàn bộ lịch sử chat.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Xóa lịch sử chat thất bại!", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            if (!searchText.isEmpty()) {
                try {
                    List<Message> searchResults = chatDAO.searchMessages(username, currentChatWith, searchText);
                    chatArea.setText("");
                    for (Message message : searchResults) {
                        String senderName = userDAO.getNameByUsername(message.getSender());
                        chatArea.append(senderName + ": " + message.getContent() + "\n");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        globalSearchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            if (!searchText.isEmpty()) {
                try {
                    List<Message> searchResults = chatDAO.searchMessagesInAllChats(username, searchText);
                    if (!searchResults.isEmpty()) {
                        JList<Message> resultList = new JList<>(searchResults.toArray(new Message[0]));
                        resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        resultList.addListSelectionListener(event -> {
                            if (!event.getValueIsAdjusting()) {
                                Message selectedMessage = resultList.getSelectedValue();
                                if (selectedMessage != null) {
                                    loadChat(
                                            selectedMessage.getSender().equals(username) ? selectedMessage.getReceiver()
                                                    : selectedMessage.getSender());
                                    scrollToMessage(selectedMessage);
                                }
                            }
                        });
                        JOptionPane.showMessageDialog(this, new JScrollPane(resultList), "Kết quả tìm kiếm",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả nào.", "Kết quả tìm kiếm",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        createGroupButton.addActionListener(e -> {
            dispose();
            CreateGroupScreen createGroupScreen = new CreateGroupScreen(username);
            createGroupScreen.setVisible(true);
        });

        sendButton.addActionListener(e -> {
            String content = messageField.getText();
            if (!content.isEmpty()) {
                try {
                    // Tạo tin nhắn mới
                    Message message = new Message(username, currentChatWith,
                            content, new java.sql.Timestamp(System.currentTimeMillis()).toString());
                    boolean isInserted = chatDAO.insertMessage(message);
                    if (isInserted) {
                        // Hiển thị tin nhắn mới lên màn hình chat
                        messageField.setText(""); // Xóa nội dung trong trường nhập liệu
                        lastTimestamp = message.getCreatedAt(); // Cập nhật lastTimestamp
                    } else {
                        JOptionPane.showMessageDialog(this, "Gửi tin nhắn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Sử dụng Timer để kiểm tra tin nhắn mới mỗi giây
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (currentChatWith != null) {
                    loadNewMessages(currentChatWith);
                }
            }
        }, 0, 1000); // 1000ms = 1 giây
    }

    private void scrollToMessage(Message message) {
        try {
            String searchText = message.getContent();
            int pos = chatArea.getText().indexOf(searchText);
            if (pos >= 0) {
                chatArea.setCaretPosition(pos);
                chatArea.requestFocusInWindow();
                Highlighter highlighter = chatArea.getHighlighter();
                Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
                highlighter.removeAllHighlights();
                highlighter.addHighlight(pos, pos + searchText.length(), painter);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void loadNewMessages(String chatWith) {
        try {
            List<Message> newMessages = chatDAO.getNewMessages(username, chatWith, lastTimestamp);
            for (Message message : newMessages) {
                String senderName = userDAO.getNameByUsername(message.getSender());
                chatArea.append(senderName + ": " + message.getContent() + "\n");
                lastTimestamp = message.getCreatedAt(); // Cập nhật lastTimestamp
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadChat(String chatWith) {
        try {
            currentChatWith = chatWith;
            String name = userDAO.getNameByUsername(chatWith);
            chatWithLabel.setText(name);
            List<Message> messages = chatDAO.getMessages(username, chatWith);
            chatArea.setText("");
            for (Message message : messages) {
                String senderName = userDAO.getNameByUsername(message.getSender());
                chatArea.append(senderName + ": " + message.getContent() + "\n");
                lastTimestamp = message.getCreatedAt(); // Cập nhật lastTimestamp
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