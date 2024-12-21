package bus;

import java.sql.SQLException;

import dao.UserDAO;
import dto.User;

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
        // Kiểm tra tính hợp lệ của dữ liệu người dùng
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            System.out.println("Tên đăng nhập không được để trống.");
            return false;
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            System.out.println("Mật khẩu không được để trống.");
            return false;
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            System.out.println("Email không được để trống.");
            return false;
        }
        // Kiểm tra độ dài mật khẩu
        if (user.getPassword().length() < 6) {
            System.out.println("Mật khẩu phải có ít nhất 6 ký tự.");
            return false;
        }
        // Kiểm tra định dạng email
        if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            System.out.println("Email không hợp lệ.");
            return false;
        }

        // Kiểm tra tên đăng nhập đã tồn tại
        try {
            if (userDAO.isUsernameExists(user.getUsername())) {
                System.out.println("Tên đăng nhập đã tồn tại.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // Nếu tất cả kiểm tra đều hợp lệ, tiến hành đăng ký người dùng
        try {
            return userDAO.insertUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void logoutUser(String username) {
        try {
            userDAO.updateUserStatus(username, "off");
        } catch (SQLException e) {
            e.printStackTrace();
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

    // Thêm phương thức updatePassword với hai tham số
    public boolean updatePassword(String username, String newPassword) {
        try {
            return userDAO.updatePassword(username, newPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUserEmail(String username) {
        try {
            return userDAO.getEmailByUsername(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}