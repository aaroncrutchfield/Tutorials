package com.demos.tutorial.demologinscreen.data;


/**
 * The repository class abstracts access to multiple data sources (ie. local database or
 * online database) It handles data operations and provides a clean interface to the data from
 * anywhere else in the app.
 */
public class UserRepository {
    private final UserDao userDao;

    public UserRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    void insertUser(User user) {
        userDao.insertUser(user);
    }

    User getUserByEmail(String email, String password) {
        return userDao.getUserByEmail(email, password);
    }
}
