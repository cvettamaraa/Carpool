package com.example.carpoolapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.util.Log;



public class DriverDashboardActivity extends AppCompatActivity {

    TextView tvDriverDetails, tvVehicleDetails, tvAvailability;
    Button btnRatePassenger;
    ListView lvDriverRatings;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_dashboard);

        tvDriverDetails = findViewById(R.id.tvDriverDetails);
        tvVehicleDetails = findViewById(R.id.tvVehicleDetails);
        tvAvailability = findViewById(R.id.tvAvailability);
        btnRatePassenger = findViewById(R.id.btnRatePassenger);
        lvDriverRatings = findViewById(R.id.lvDriverRatings);
        dbHelper = new DatabaseHelper(this);

        dbHelper = new DatabaseHelper(this);

        // Set some example driver details
        tvDriverDetails.setText("Driver: John Doe");
        tvVehicleDetails.setText("Vehicle: Tesla Model S");
        tvAvailability.setText("Available: 9:00 AM - 5:00 PM");

        // Fetch all driver details from the database
        List<String> drivers = dbHelper.getAllDriverDetails();

        // Create an ArrayAdapter to display the list of driver details in the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, drivers);
        lvDriverRatings.setAdapter(adapter);


        btnRatePassenger.setOnClickListener(v -> {
            // Navigate to RatePassengerActivity
            startActivity(new Intent(this, RatePassengerActivity.class));
        });
    }
}
