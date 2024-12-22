package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class GroupDAO {
    public boolean createGroup(String groupName, String createdBy) throws SQLException {
        String sql = "INSERT INTO `group` (groupName, createdAt) VALUES (?, NOW())";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, groupName);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating group failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int groupId = generatedKeys.getInt(1);
                    return addGroupMember(groupId, createdBy, true);
                } else {
                    throw new SQLException("Creating group failed, no ID obtained.");
                }
            }
        }
    }

    public boolean addGroupMember(int groupId, String username, boolean isAdmin) throws SQLException {
        String sql = "INSERT INTO groupmember (groupId, username, isGroupAdmin) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, groupId);
            stmt.setString(2, username);
            stmt.setBoolean(3, isAdmin);
            return stmt.executeUpdate() > 0;
        }
    }
}
