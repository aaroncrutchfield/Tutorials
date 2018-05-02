package com.demos.tutorial.demologinscreen.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by Aaron Crutchfield on 5/2/2018.
 */

public class UserViewModel extends ViewModel {
    UserRepository userRepo;

    public UserViewModel() {
    }

    public void insertUser(final User user) {
        new InsertAsyncTask(userRepo).execute(user);

    }

    public List<User> getUsers() {
        return userRepo.getUsers();
    }

    public User getUserByEmail(String email, String password) {
        return userRepo.getUserByEmail(email, password);
    }

    public void setRepository(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // AsyncTask to do work in the background
    private static class InsertAsyncTask extends AsyncTask<User, Void, Void> {

        private final UserRepository userRepository;

        InsertAsyncTask(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        protected Void doInBackground(User... users) {
            userRepository.insertUser(users[0]);
            return null;
        }
    }

    private static class ReadAsyncTask extends AsyncTask<User, Void, Void> {

        private final UserRepository userRepository;

        ReadAsyncTask(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        protected Void doInBackground(User... users) {
            userRepository.insertUser(users[0]);
            return null;
        }
    }
}
