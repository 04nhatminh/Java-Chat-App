package user;

import javax.swing.*;
import java.awt.*;

public class ChatScreen extends JFrame {
    public ChatScreen() {
        setTitle("Chat Application");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel chatListPanel = new JPanel();
        chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));
        chatListPanel.setPreferredSize(new Dimension(400, 600));

        JButton createGroupButton = new JButton("Tạo nhóm");
        chatListPanel.add(createGroupButton);

        for (int i = 1; i <= 5; i++) {
            JPanel singleChatPanel = new JPanel(new BorderLayout());
            singleChatPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

            JLabel friendLabel = new JLabel("Bạn số " + i);
            JLabel lastMessageLabel = new JLabel("Tin nhắn gần nhất từ bạn số " + i);
            singleChatPanel.add(friendLabel, BorderLayout.NORTH);
            singleChatPanel.add(lastMessageLabel, BorderLayout.SOUTH);
            chatListPanel.add(singleChatPanel);
        }
        for (int i = 6; i <= 10; i++) {
            JPanel singleChatPanel = new JPanel(new BorderLayout());
            singleChatPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

            JLabel friendLabel = new JLabel("Nhóm số " + i);
            JLabel lastMessageLabel = new JLabel("Tin nhắn gần nhất từ nhóm số " + i);
            singleChatPanel.add(friendLabel, BorderLayout.NORTH);
            singleChatPanel.add(lastMessageLabel, BorderLayout.SOUTH);
            chatListPanel.add(singleChatPanel);
        }

        JScrollPane chatListScrollPane = new JScrollPane(chatListPanel);

        JPanel chatPanel = new JPanel(new BorderLayout());

        JPanel chatHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel chatWithLabel = new JLabel("Bạn số 1");
        JButton oldMessagesButton = new JButton("Tra cứu tin nhắn cũ");
        JButton reportSpamButton = new JButton("Báo cáo spam");

        chatHeaderPanel.add(chatWithLabel);
        chatHeaderPanel.add(oldMessagesButton);
        chatHeaderPanel.add(reportSpamButton);

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setText("Bạn số 1: Xin chào!\nBạn: Chào bạn, bạn khỏe không?\nBạn số 1: Mình khỏe, cảm ơn bạn. Còn bạn?\nBạn: Mình cũng khỏe, cảm ơn bạn đã hỏi.\n");
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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatScreen chatScreen = new ChatScreen();
            chatScreen.setVisible(true);
        });
    }
}
