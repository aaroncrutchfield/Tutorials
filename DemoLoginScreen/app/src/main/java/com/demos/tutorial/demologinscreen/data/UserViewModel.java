package com.demos.tutorial.demologinscreen.data;

import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

/**
 * The ViewModel provides data to the UI and survives configuration changes. This prevents the need
 * for
 */

public class UserViewModel extends ViewModel {
    private UserRepository userRepo;

    public UserViewModel() {
    }

    public void insertUser(final User user) {
        new InsertAsyncTask(userRepo).execute(user);
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
}
