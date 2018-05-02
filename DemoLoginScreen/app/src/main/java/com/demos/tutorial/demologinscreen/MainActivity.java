package com.demos.tutorial.demologinscreen;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
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

    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;

    private TextView tvNameLabel;

    private Button btnLogin;
    private Button btnRegister;
    private Button btnSubmit;

    public static boolean isLogin = true;

    UserViewModel userViewModel;

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

        // Get an instance of the ViewModel
        AppDatabase appDatabase = AppDatabase.getAppDatabase(this);
        UserDao userDao = appDatabase.userDao();

        UserRepository userRepository = new UserRepository(userDao);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.setRepository(userRepository);
    }

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

    public void showLogin(View view) {
        String text = btnLogin.getText().toString();

        // Change the color of the button text
        btnLogin.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        btnRegister.setTextColor(ContextCompat.getColor(this, R.color.white));

        // Set submit button text
        btnSubmit.setText(text);

        // Hide the name field
        tvNameLabel.setVisibility(View.INVISIBLE);
        etName.setVisibility(View.INVISIBLE);

        isLogin = true;
    }

    public void submit(View view) {
        if (isLogin) {
            login();
        } else {
            registerNewUser();
        }
    }

    private void registerNewUser() {
        // Attempt to get the user's information
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        // Generate an error message if the user leaves one of the fields blank
        String errorMessage;
        if (name.equals("")) {
            errorMessage = "Please insert a valid name";
        }
        else if (email.equals("")) {
            errorMessage = "Please insert a valid email";
        }
        else if (password.equals("")) {
            errorMessage = "Please insert a valid password";
        }
        else {
            // Enter the user into the database
            User user = new User(name, email, password);
            // TODO: 4/30/2018 registerNewUser() - Insert new user in Database
            userViewModel.insertUser(user);
            errorMessage = name + " registered";
        }

        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void login() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        String errorMessage;
        if (email.equals("") | password.equals("")) {
            errorMessage = "Please fill the email and password fields";
        } else {
            // Check if the user exists in the database
            User user = userViewModel.getUserByEmail(email, password);
            if (user != null) {
                errorMessage = "Welcome " + user.getName() + "!";
            } else {
                errorMessage = "Invalid email and password combination";
            }

        }

        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
