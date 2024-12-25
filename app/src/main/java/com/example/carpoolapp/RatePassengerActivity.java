package com.example.carpoolapp;
import android.content.Intent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RatePassengerActivity extends AppCompatActivity {

    EditText edtPassengerName;
    RatingBar ratingBar;
    Button btnSubmitRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_passenger);

        edtPassengerName = findViewById(R.id.edtPassengerName);
        ratingBar = findViewById(R.id.ratingBar);
        btnSubmitRating = findViewById(R.id.btnSubmitRating);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        btnSubmitRating.setOnClickListener(v -> {
            String passengerName = edtPassengerName.getText().toString();
            float rating = ratingBar.getRating();

            // Example: Get the driver's name (hardcoded here; retrieve it dynamically in a real app)
            String driverName = getIntent().getStringExtra("driver_name");
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();


            if (passengerName.isEmpty()) {
                Toast.makeText(this, "Please enter the passenger's name", Toast.LENGTH_SHORT).show();
            } else {
                boolean isInserted = dbHelper.insertRating(driverName, passengerName, rating);
                if (isInserted) {
                    Toast.makeText(this, "Rating submitted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Failed to submit rating", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

