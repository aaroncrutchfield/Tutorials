package com.demos.tutorial.demologinscreen.data;

import java.util.List;

/**
 * Created by Aaron Crutchfield on 5/2/2018.
 */

public class UserRepository {
    private final UserDao userDao;

    public UserRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    public void insertUser(User user) {
        userDao.insertUser(user);
    }

    public List<User> getUsers(){
        return userDao.getUsers();
    }

    public User getUserByEmail(String email, String password) {
        return userDao.getUserByEmail(email, password);
    }
}
