package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.User;

public class UserDAO {

    public boolean isUsernameExists(String username) throws SQLException {
        String query = "SELECT 1 FROM user WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public boolean insertUser(User user) throws SQLException {
        String sql = "INSERT INTO user (username, password, name, email, address, dob, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFullname());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getDob());
            stmt.setString(7, user.getGender());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE user SET name = ?, email = ?, address = ?, dob = ?, gender = ? WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getFullname());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getAddress());
            stmt.setString(4, user.getDob());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getUsername());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Trả về true nếu có ít nhất 1 dòng được cập nhật
        }
    }

    public boolean updatePassword(String username, String newPassword) throws SQLException {
        String sql = "UPDATE user SET password = ? WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, username);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Trả về true nếu có ít nhất 1 dòng được cập nhật
        }
    }

    public boolean checkLogin(String username, String password) {
        String query = "SELECT 1 FROM user WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserStatus(String username, String status) throws SQLException {
        String sql = "UPDATE user SET status = ? WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<User> getFriends(String username) throws SQLException {
        String query = """
                SELECT u.username, u.name, u.status
                FROM user u
                WHERE EXISTS (
                    SELECT 1
                    FROM relationship r1
                    WHERE r1.username1 = ? AND r1.username2 = u.username AND r1.type = 'follow'
                ) AND EXISTS (
                    SELECT 1
                    FROM relationship r2
                    WHERE r2.username1 = u.username AND r2.username2 = ? AND r2.type = 'follow'
                );
                """;
        List<User> friends = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    friends.add(new User(rs.getString("username"), null, rs.getString("name"), null, null, null, null,
                            rs.getString("status")));
                }
            }
        }
        return friends;
    }

    public boolean updateRelationship(String username1, String username2, String type) throws SQLException {
        String query = "REPLACE INTO relationship (username1, username2, type) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username1);
            stmt.setString(2, username2);
            stmt.setString(3, type);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteRelationship(String username1, String username2, String type) throws SQLException {
        String query = "DELETE FROM relationship WHERE username1 = ? AND username2 = ? AND type = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username1);
            stmt.setString(2, username2);
            stmt.setString(3, type);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean blockUser(String blocker, String blocked) throws SQLException {
        String query = "INSERT INTO relationship (username1, username2, type) VALUES (?, ?, 'block')";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, blocker);
            preparedStatement.setString(2, blocked);
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        }
    }

    public boolean isBlocked(String blocker, String blocked) throws SQLException {
        String query = "SELECT COUNT(*) FROM relationship WHERE username1 = ? AND username2 = ? AND type = 'block'";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, blocker);
            preparedStatement.setString(2, blocked);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public User findUserByName(String name, String currentUsername) throws SQLException {
        String blockCheckQuery = "SELECT COUNT(*) FROM relationship WHERE username1 = ? AND username2 = ? AND type = 'block'";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement blockCheckStmt = connection.prepareStatement(blockCheckQuery)) {
            blockCheckStmt.setString(1, name);
            blockCheckStmt.setString(2, currentUsername);
            try (ResultSet blockCheckResult = blockCheckStmt.executeQuery()) {
                if (blockCheckResult.next() && blockCheckResult.getInt(1) > 0) {
                    return null; // currentUsername is blocked by name
                }
            }
        }

        String query = "SELECT * FROM user WHERE name = ? AND username != ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, currentUsername);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("address"),
                            resultSet.getString("dob"),
                            resultSet.getString("gender"),
                            resultSet.getString("status"));
                }
            }
        }
        return null;
    }

    public String getNameByUsername(String username) throws SQLException {
        String query = "SELECT name FROM user WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                }
            }
        }
        return null;
    }

    public List<User> getFriendRequests(String username) throws SQLException {
        String query = """
                SELECT u.username, u.name, u.status
                FROM user u
                JOIN relationship r
                ON u.username = r.username1
                WHERE r.username2 = ? AND r.type = 'follow'
                AND NOT EXISTS (
                    SELECT 1
                    FROM relationship r2
                    WHERE r2.username1 = ? AND r2.username2 = u.username AND r2.type = 'follow'
                );
                """;
        List<User> requests = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    requests.add(new User(rs.getString("username"), null, rs.getString("name"), null, null, null, null,
                            rs.getString("status")));
                }
            }
        }
        return requests;
    }
}
