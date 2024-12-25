package com.example.carpoolapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    // Declare the UI elements
    EditText edtUsername, edtPassword;
    Spinner spinnerUserType;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize the UI elements
        edtUsername = findViewById(R.id.edtRegUsername);
        edtPassword = findViewById(R.id.edtRegPassword);
        spinnerUserType = findViewById(R.id.spinnerUserType);
        btnRegister = findViewById(R.id.btnRegister);

        // Initialize DatabaseHelper
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Set up the button click listener for registration
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered values
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String userType = spinnerUserType.getSelectedItem().toString();

                // Validate the inputs
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Insert the new user into the database
                    boolean isInserted = dbHelper.insertUser(username, password, userType);

                    if (isInserted) {
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        // Optionally, navigate to LoginActivity
                        finish(); // Close the register activity and return to the login screen
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}