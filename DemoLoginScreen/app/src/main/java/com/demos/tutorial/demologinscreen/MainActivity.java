package com.demos.tutorial.demologinscreen;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.demos.tutorial.demologinscreen.data.AppDatabase;
import com.demos.tutorial.demologinscreen.data.User;
import com.demos.tutorial.demologinscreen.data.UserDao;
import com.demos.tutorial.demologinscreen.data.UserRepository;
import com.demos.tutorial.demologinscreen.data.UserViewModel;

public class MainActivity extends AppCompatActivity {

    // EditTexts
    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;

    // TextViews
    private TextView tvNameLabel;

    // Buttons
    private Button btnLogin;
    private Button btnRegister;
    private Button btnSubmit;

    // Boolean to notify us if we are logging in or signing up
    private static boolean isLogin = true;

    // Our ViewModel for managing our UI-related data in a lifecycle conscious way
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a reference to our views
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

        tvNameLabel = findViewById(R.id.tv_name_label);

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        btnSubmit = findViewById(R.id.btn_submit);

        // Setup the app database, dao, and repository
        AppDatabase appDatabase = AppDatabase.getAppDatabase(this);
        UserDao userDao = appDatabase.userDao();
        UserRepository userRepository = new UserRepository(userDao);

        // Get an instance of the ViewModel from the provider
        // This associates your ViewModel with your UI controller (Activity)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.setRepository(userRepository);
    }

    /**
     * Shows the views necessary for registering a new account after clicking the register option
     * @param view
     */
    public void showRegistration(View view) {
        String text = btnRegister.getText().toString();

        btnRegister.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        btnLogin.setTextColor(ContextCompat.getColor(this, R.color.white));

        btnSubmit.setText(text);

        // Show the name field
        tvNameLabel.setVisibility(View.VISIBLE);
        etName.setVisibility(View.VISIBLE);

        isLogin = false;
    }

    /**
     * Shows the views necessary for logging in after clicking the login option
     * @param view
     */
    public void showLogin(View view) {

        // Change the color of the button text
        btnLogin.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        btnRegister.setTextColor(ContextCompat.getColor(this, R.color.white));

        // Set submit button text
        String text = btnLogin.getText().toString();
        btnSubmit.setText(text);

        // Hide the name field
        tvNameLabel.setVisibility(View.INVISIBLE);
        etName.setVisibility(View.INVISIBLE);

        // Set true because we are logging in
        isLogin = true;
    }

    /**
     * Submits the data entered in the EditText fields for logging in or registering
     * @param view
     */
    public void submit(View view) {
        if (isLogin) {
            login();
        } else {
            registerNewUser();
        }
    }

    /**
     * Retrieves the information entered in the EditText fields and enters it into the database.
     * Alerts the user if all of the fields aren't filled in.
     */
    private void registerNewUser() {
        // Attempt to get the user's information
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        // Generate an appropriate message if the user leaves one of the fields blank
        String message;
        if (name.equals("")) {
            message = "Please insert a valid name";
        }
        else if (email.equals("")) {
            message = "Please insert a valid email";
        }
        else if (password.equals("")) {
            message = "Please insert a valid password";
        }
        else {
            // Enter the user into the database
            User user = new User(name, email, password);
            userViewModel.insertUser(user);

            message = name + " registered";
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Checks the database for an existing user with a matching email and password
     */
    private void login() {
        // Retrieve the email and password from the user
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        // Notify the user if all the fields aren't entered
        String message;
        if (email.equals("") | password.equals("")) {
            message = "Please fill the email and password fields";
        } else {
            // Check if the user exists in the database
            User user = userViewModel.getUserByEmail(email, password);
            if (user != null) {
                message = "Welcome " + user.getName() + "!";
            } else {
                message = "Invalid email and password combination";
            }

        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
