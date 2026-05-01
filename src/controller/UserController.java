package controller;

import dao.UserDAO;
import model.User;

public class UserController {

    private final UserDAO userDAO = new UserDAO();

    public boolean registerUser(User user) {
        return userDAO.createUser(user);
    }

    public User loginUser(String email, String password) {
        User user = userDAO.findByEmail(email);
        if (user == null) {
            return null;
        }
        if (user.login(email, password)) {
            return user;
        }
        return null;
    }

    public User getUserById(int userId) {
        return userDAO.findById(userId);
    }

    public java.util.List<User> getAllUsers() {
        return userDAO.findAllUsers();
    }

    public void logoutUser(User user) {
        if (user != null) {
            user.logout();
        }
    }
}