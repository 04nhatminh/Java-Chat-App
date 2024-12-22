package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Message;

public class ChatDAO {
    public List<Message> getMessages(String username, String chatWith) throws SQLException {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT sender, content, createdAt FROM message WHERE (sender = ? AND receiver = ?) OR (sender = ? AND receiver = ?) ORDER BY createdAt";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, chatWith);
            stmt.setString(3, chatWith);
            stmt.setString(4, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String sender = rs.getString("sender");
                    String receiver = chatWith;
                    String content = rs.getString("content");
                    String createdAt = rs.getString("createdAt");
                    messages.add(new Message(sender, receiver, content, createdAt));
                }
            }
        }
        return messages;
    }

    public List<Message> getNewMessages(String username, String chatWith, String lastTimestamp) throws SQLException {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT sender, content, createdAt FROM message WHERE ((sender = ? AND receiver = ?) OR (sender = ? AND receiver = ?)) AND createdAt > ? ORDER BY createdAt";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, chatWith);
            stmt.setString(3, chatWith);
            stmt.setString(4, username);
            stmt.setString(5, lastTimestamp);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String sender = rs.getString("sender");
                    String receiver = chatWith;
                    String content = rs.getString("content");
                    String createdAt = rs.getString("createdAt");
                    messages.add(new Message(sender, receiver, content, createdAt));
                }
            }
        }
        return messages;
    }

    public List<String> getChatList(String username) throws SQLException {
        List<String> chatList = new ArrayList<>();
        String sql = "SELECT DISTINCT IF(sender = ?, receiver, sender) AS chatWith FROM message WHERE sender = ? OR receiver = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, username);
            stmt.setString(3, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    chatList.add(rs.getString("chatWith"));
                }
            }
        }
        return chatList;
    }

    public String getLastMessage(String username, String chatWith) throws SQLException {
        String lastMessage = "";
        String sql = "SELECT content FROM message WHERE (sender = ? AND receiver = ?) OR (sender = ? AND receiver = ?) ORDER BY createdAt DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, chatWith);
            stmt.setString(3, chatWith);
            stmt.setString(4, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    lastMessage = rs.getString("content");
                }
            }
        }
        return lastMessage;
    }

    public boolean insertMessage(Message message) throws SQLException {
        String sql = "INSERT INTO message (sender, receiver, content, createdAt) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, message.getSender());
            stmt.setString(2, message.getReceiver());
            stmt.setString(3, message.getContent());
            stmt.setString(4, message.getCreatedAt());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; // Trả về true nếu có ít nhất 1 dòng được thêm
        }
    }

    public boolean deleteAllMessages(String username, String chatWith) throws SQLException {
        String sql = "DELETE FROM message WHERE (sender = ? AND receiver = ?) OR (sender = ? AND receiver = ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, chatWith);
            stmt.setString(3, chatWith);
            stmt.setString(4, username);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    public boolean deleteMessages(List<Integer> messageIds) throws SQLException {
        String sql = "DELETE FROM message WHERE messageId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int messageId : messageIds) {
                stmt.setInt(1, messageId);
                stmt.addBatch();
            }
            int[] rowsDeleted = stmt.executeBatch();
            return rowsDeleted.length > 0;
        }
    }

    public List<Message> searchMessages(String username, String chatWith, String searchText) throws SQLException {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT sender, content, createdAt FROM message WHERE ((sender = ? AND receiver = ?) OR (sender = ? AND receiver = ?)) AND content LIKE ? ORDER BY createdAt";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, chatWith);
            stmt.setString(3, chatWith);
            stmt.setString(4, username);
            stmt.setString(5, "%" + searchText + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String sender = rs.getString("sender");
                    String receiver = chatWith;
                    String content = rs.getString("content");
                    String createdAt = rs.getString("createdAt");
                    messages.add(new Message(sender, receiver, content, createdAt));
                }
            }
        }
        return messages;
    }

    public List<Message> searchMessagesInAllChats(String username, String searchText) throws SQLException {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT sender, receiver, content, createdAt FROM message WHERE (sender = ? OR receiver = ?) AND content LIKE ? ORDER BY createdAt";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, username);
            stmt.setString(3, "%" + searchText + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String sender = rs.getString("sender");
                    String receiver = rs.getString("receiver");
                    String content = rs.getString("content");
                    String createdAt = rs.getString("createdAt");
                    messages.add(new Message(sender, receiver, content, createdAt));
                }
            }
        }
        return messages;
    }
}