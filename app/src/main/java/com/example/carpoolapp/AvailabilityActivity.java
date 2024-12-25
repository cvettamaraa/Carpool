package com.example.carpoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AvailabilityActivity extends AppCompatActivity {

    EditText edtStartHour, edtStartMinute, edtEndHour, edtEndMinute;
    Button btnSaveAvailability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);

        edtStartHour = findViewById(R.id.edtStartHour);
        edtStartMinute = findViewById(R.id.edtStartMinute);
        edtEndHour = findViewById(R.id.edtEndHour);
        edtEndMinute = findViewById(R.id.edtEndMinute);
        btnSaveAvailability = findViewById(R.id.btnSaveAvailability);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        btnSaveAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int startHour = Integer.parseInt(edtStartHour.getText().toString());
                    int startMinute = Integer.parseInt(edtStartMinute.getText().toString());
                    int endHour = Integer.parseInt(edtEndHour.getText().toString());
                    int endMinute = Integer.parseInt(edtEndMinute.getText().toString());

                    int driverId = 1;  // Replace with actual driver ID logic

                    if (startHour > endHour || (startHour == endHour && startMinute >= endMinute)) {
                        Toast.makeText(AvailabilityActivity.this, "Start time must be before end time", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean isSaved = dbHelper.saveDriverAvailability(driverId, startHour, startMinute, endHour, endMinute);

                        if (isSaved) {
                            Toast.makeText(AvailabilityActivity.this, "Availability saved successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AvailabilityActivity.this, DriverDashboardActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(AvailabilityActivity.this, "Failed to save availability", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (NumberFormatException e) {
                    Log.e("AvailabilityActivity", "Invalid input for time", e);
                    Toast.makeText(AvailabilityActivity.this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("AvailabilityActivity", "Unexpected error", e);
                    Toast.makeText(AvailabilityActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
