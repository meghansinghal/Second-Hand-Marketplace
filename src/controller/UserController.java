package controller;

import model.User;

public class UserController {

    public void registerUser(User user) {
        user.register();
    }

    public boolean loginUser(User user, String email, String password) {
        return user.login(email, password);
    }

    public void logoutUser(User user) {
        user.logout();
    }
}