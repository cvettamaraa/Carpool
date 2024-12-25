package com.example.carpoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DriverDetailsActivity extends AppCompatActivity {
    EditText edtDriverName, edtCarModel, edtCarType, edtDriverLocation;
    Button btnSaveDriverDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);

        // Initialize UI elements
        edtDriverName = findViewById(R.id.edtDriverName);
        edtCarModel = findViewById(R.id.edtVehicleModel);
        edtCarType = findViewById(R.id.edtVehicleType);
        edtDriverLocation = findViewById(R.id.edtLocation);

        btnSaveDriverDetails = findViewById(R.id.btnSaveDetails);

        btnSaveDriverDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the details from EditText
                String driverName = edtDriverName.getText().toString();
                String vehicleType = edtCarType.getText().toString();
                String vehicleModel = edtCarModel.getText().toString();
                String location = edtDriverLocation.getText().toString();

                // Add logging to check input data
                Log.d("DriverDetailsActivity", "Driver Name: " + driverName);
                Log.d("DriverDetailsActivity", "Vehicle Type: " + vehicleType);
                Log.d("DriverDetailsActivity", "Vehicle Model: " + vehicleModel);
                Log.d("DriverDetailsActivity", "Location: " + location);

                if (driverName.isEmpty() || vehicleType.isEmpty() || vehicleModel.isEmpty() || location.isEmpty()) {
                    Toast.makeText(DriverDetailsActivity.this, "Please fill in all details", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseHelper dbHelper = new DatabaseHelper(DriverDetailsActivity.this);
                    boolean isInserted = dbHelper.insertDriverDetails(driverName, vehicleType, vehicleModel, location);

                    if (isInserted) {
                        Toast.makeText(DriverDetailsActivity.this, "Details saved successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DriverDetailsActivity.this, AvailabilityActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DriverDetailsActivity.this, "Failed to save details", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
