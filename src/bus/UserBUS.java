package bus;

import dao.UserDAO;
import dto.User;

public class UserBUS {
    private UserDAO userDAO;

    public UserBUS() {
        userDAO = new UserDAO();
    }

    public boolean checkLogin(String username, String password) {
        return userDAO.checkLogin(username, password);
    }

    public boolean registerUser(User user) {

        return userDAO.insertUser(user);
    }
}
