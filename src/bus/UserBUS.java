package bus;

import java.sql.SQLException;
import java.util.List;

import dao.UserDAO;
import dto.User;
import utils.EmailSender;

public class UserBUS {
    private UserDAO userDAO;

    public UserBUS() {
        userDAO = new UserDAO();
    }

    public boolean checkLogin(String username, String password) {
        boolean isLoggedIn = userDAO.checkLogin(username, password);
        if (isLoggedIn) {
            try {
                userDAO.updateUserStatus(username, "onl");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isLoggedIn;
    }

    public boolean registerUser(User user) {
        if (isInvalidUser(user)) {
            return false;
        }

        try {
            if (userDAO.isUsernameExists(user.getUsername())) {
                System.out.println("Tên đăng nhập đã tồn tại.");
                return false;
            }
            return userDAO.insertUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User user) {
        try {
            return userDAO.updateUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        try {
            // Kiểm tra mật khẩu hiện tại
            if (!userDAO.checkLogin(username, oldPassword)) {
                System.out.println("Mật khẩu hiện tại không đúng.");
                return false;
            }
            // Cập nhật mật khẩu mới
            return userDAO.updatePassword(username, newPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức updatePassword với hai tham số
    public boolean updatePassword(String username, String newPassword) {
        try {
            return userDAO.updatePassword(username, newPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isInvalidUser(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            System.out.println("Tên đăng nhập không được để trống.");
            return true;
        }
        if (user.getPassword() == null || user.getPassword().isEmpty() || user.getPassword().length() < 6) {
            System.out.println("Mật khẩu không hợp lệ.");
            return true;
        }
        if (user.getEmail() == null || !user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            System.out.println("Email không hợp lệ.");
            return true;
        }
        return false;
    }

    public void logoutUser(String username) {
        try {
            userDAO.updateUserStatus(username, "off");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getFriends(String username) {
        try {
            return userDAO.getFriends(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean blockUser(String blocker, String blocked) {
        try {
            return this.unfriend(blocker, blocked) && userDAO.blockUser(blocker, blocked);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isBlocked(String blocker, String blocked) {
        try {
            return userDAO.isBlocked(blocker, blocked);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean unfriend(String username1, String username2) {
        try {
            return userDAO.deleteRelationship(username1, username2, "follow")
                    && userDAO.deleteRelationship(username2, username1, "follow");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendFriendRequest(String fromUsername, String toUsername) {
        try {
            if (isBlocked(toUsername, fromUsername)) {
                System.out.println("Cannot send friend request. The user has blocked you.");
                return false;
            }
            return userDAO.updateRelationship(fromUsername, toUsername, "follow");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User findUserByName(String name, String currentUsername) {
        try {
            return userDAO.findUserByName(name, currentUsername);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> getFriendRequests(String username) {
        try {
            return userDAO.getFriendRequests(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean acceptFriendRequest(String username1, String username2) {
        try {
            return userDAO.updateRelationship(username1, username2, "follow");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean declineFriendRequest(String username1, String username2) {
        try {
            return userDAO.deleteRelationship(username2, username1, "follow");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
