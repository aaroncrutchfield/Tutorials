package com.demos.tutorial.demologinscreen.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Aaron Crutchfield on 4/30/2018.
 */
@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM user")
    List<User> getUsers();

    @Query("SELECT * FROM user " +
            "WHERE email = :email " +
            "AND password = :password")
    User getUserByEmail(String email, String password);
}
