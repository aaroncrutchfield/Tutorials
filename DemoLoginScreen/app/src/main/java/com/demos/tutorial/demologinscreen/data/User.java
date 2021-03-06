package com.demos.tutorial.demologinscreen.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * POJO that represents a table within the database. Each variable represents a column in the table
 */
@Entity
public class User {
    @PrimaryKey
    @NonNull
    // Email will be the primary key since this is the unique way we will identify each user
    private String email;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
