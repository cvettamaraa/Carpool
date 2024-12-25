package com.example.carpoolapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PassengerActivity extends AppCompatActivity {

    private static final String TAG = "PassengerActivity";

    EditText edtLocation;
    Button btnSearchDrivers;
    RecyclerView recyclerViewDrivers;
    DriverAdapter driverAdapter;
    List<String> driverList;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);

        edtLocation = findViewById(R.id.edtLocation); // Corrected ID
        btnSearchDrivers = findViewById(R.id.btnSearchDrivers); // Corrected ID
        recyclerViewDrivers = findViewById(R.id.recyclerViewDrivers); // Corrected ID

        dbHelper = new DatabaseHelper(this);

        driverList = new ArrayList<>();
        driverAdapter = new DriverAdapter(driverList);
        recyclerViewDrivers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDrivers.setAdapter(driverAdapter);

        // Insert sample driver data for testing
        insertSampleDrivers();

        btnSearchDrivers.setOnClickListener(v -> {
            String location = edtLocation.getText().toString().trim();
            Log.d(TAG, "Location entered: " + location);

            if (location.isEmpty()) {
                Log.e(TAG, "Location is empty!");
                Toast.makeText(PassengerActivity.this, "Please enter a location", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PassengerActivity.this, "Searching for drivers near " + location, Toast.LENGTH_SHORT).show();
                try {
                    driverList.clear();

                    driverAdapter.notifyDataSetChanged();

                    Log.d(TAG, "Driver list updated: " + driverList.size());

                    if (driverList.isEmpty()) {
                        Toast.makeText(PassengerActivity.this, "No drivers found for the given location", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error during driver search", e);
                    Toast.makeText(PassengerActivity.this, "Error while searching for drivers", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Insert sample drivers into the database
    private void insertSampleDrivers() {
        dbHelper.insertDriverDetails("Driver 1", "Sedan", "Toyota Camry", "Skopje, Feit");
        dbHelper.insertDriverDetails("Driver 2", "SUV", "Honda CRV", "Skopje, Feit");
        dbHelper.insertDriverDetails("Driver 3", "Hatchback", "Ford Fiesta", "Skopje, Centar");
    }
}
