package com.example.carpoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    // Declare EditTexts for username and password
    EditText edtUsername, edtPassword;
    Button btnLogin, btnRegister;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize the UI elements
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnGoToRegister); // Register button

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Set up the button click listener for login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the username and password from the input fields
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                // Check if the user exists in the database
                boolean isUserValid = dbHelper.checkUser(username, password);

                // Show a toast message based on success or failure
                if (isUserValid) {
                    // Get the user type from the database (Driver or Passenger)
                    String userType = dbHelper.getUserType(username, password);

                    // If the user is a driver, navigate to DriverDetailsActivity
                    if (userType != null && userType.equals("Driver")) {
                        Intent intent = new Intent(LoginActivity.this, DriverDetailsActivity.class);
                        startActivity(intent);  // Start DriverDetailsActivity
                        finish();  // Optional: Close LoginActivity
                    } else {
                        // For passengers or other users, navigate to another activity
                        Toast.makeText(LoginActivity.this, "Welcome Passenger!", Toast.LENGTH_SHORT).show();
                        // You can navigate to a passenger dashboard or home activity here
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set up the button click listener for the Register button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to RegisterActivity when the Register button is clicked
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent); // Start RegisterActivity
            }
        });
    }
}
