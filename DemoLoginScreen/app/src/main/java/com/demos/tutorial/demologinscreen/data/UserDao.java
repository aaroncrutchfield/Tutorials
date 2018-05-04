package com.demos.tutorial.demologinscreen.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * The database access object has methods that offers abstract access to the app database
 */
@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    /**
     * Returns a user with a matching email and password
     * @param email
     * @param password
     * @return
     */
    @Query("SELECT * FROM user " +
            "WHERE email = :email " +
            "AND password = :password")
    User getUserByEmail(String email, String password);
}
