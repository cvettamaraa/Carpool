package com.example.carpoolapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "carpoolApp.db";
    private static final int DATABASE_VERSION = 6;

    // Table names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_AVAILABILITY = "availability";
    public static final String TABLE_DRIVER_DETAILS = "driver_details";
    public static final String TABLE_RATINGS = "ratings";

    // Column names for users table
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_USER_TYPE = "user_type";

    // Column names for availability table
    public static final String COLUMN_AVAILABILITY_ID = "id";
    public static final String COLUMN_DRIVER_ID = "driver_id";
    public static final String COLUMN_START_HOUR = "start_hour";
    public static final String COLUMN_START_MINUTE = "start_minute";
    public static final String COLUMN_END_HOUR = "end_hour";
    public static final String COLUMN_END_MINUTE = "end_minute";

    // Column names for driver_details table
    public static final String COLUMN_DRIVER_NAME = "driver_name";
    public static final String COLUMN_VEHICLE_TYPE = "vehicle_type";
    public static final String COLUMN_VEHICLE_MODEL = "vehicle_model";
    public static final String COLUMN_DRIVER_LOCATION = "location";

    // Column names for ratings table
    public static final String COLUMN_RATING_ID = "id";
    public static final String COLUMN_PASSENGER_NAME = "passenger_name";
    public static final String COLUMN_RATING = "rating";

    // SQL to create tables
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_USER_TYPE + " TEXT);";

    private static final String CREATE_TABLE_AVAILABILITY =
            "CREATE TABLE " + TABLE_AVAILABILITY + " (" +
                    COLUMN_AVAILABILITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DRIVER_ID + " INTEGER, " +
                    COLUMN_START_HOUR + " INTEGER, " +
                    COLUMN_START_MINUTE + " INTEGER, " +
                    COLUMN_END_HOUR + " INTEGER, " +
                    COLUMN_END_MINUTE + " INTEGER);";

    private static final String CREATE_TABLE_DRIVER_DETAILS =
            "CREATE TABLE " + TABLE_DRIVER_DETAILS + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DRIVER_NAME + " TEXT, " +
                    COLUMN_VEHICLE_TYPE + " TEXT, " +
                    COLUMN_VEHICLE_MODEL + " TEXT, " +
                    COLUMN_DRIVER_LOCATION + " TEXT);";

    private static final String CREATE_TABLE_RATINGS =
            "CREATE TABLE " + TABLE_RATINGS + " (" +
                    COLUMN_RATING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DRIVER_NAME + " TEXT, " +
                    COLUMN_PASSENGER_NAME + " TEXT, " +
                    COLUMN_RATING + " REAL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_USERS);
            Log.d("DatabaseHelper", "Created table: users");

            db.execSQL(CREATE_TABLE_AVAILABILITY);
            Log.d("DatabaseHelper", "Created table: availability");

            db.execSQL(CREATE_TABLE_DRIVER_DETAILS);
            Log.d("DatabaseHelper", "Created table: driver_details");

            db.execSQL(CREATE_TABLE_RATINGS);
            Log.d("DatabaseHelper", "Created table: ratings");
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error creating tables: ", e);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AVAILABILITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRIVER_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATINGS);
        onCreate(db);
    }

    // Insert Driver Details
    public boolean insertDriverDetails(String driverName, String vehicleType, String vehicleModel, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_DRIVER_NAME, driverName);
        values.put(COLUMN_VEHICLE_TYPE, vehicleType);
        values.put(COLUMN_VEHICLE_MODEL, vehicleModel);
        values.put(COLUMN_DRIVER_LOCATION, location);

        long result = db.insert(TABLE_DRIVER_DETAILS, null, values);
        db.close();

        // Add logging to check the result
        if (result == -1) {
            Log.e("DatabaseHelper", "Failed to insert driver details.");
            return false;
        } else {
            Log.d("DatabaseHelper", "Driver details inserted successfully with ID: " + result);
            return true;
        }
    }


    // Retrieve Driver Details
    public Cursor getDriverDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_DRIVER_DETAILS + " ORDER BY " + COLUMN_USER_ID + " DESC LIMIT 1", null);
    }

    // Retrieve All Drivers' Details
    public List<String> getAllDriverDetails() {
        List<String> drivers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DRIVER_DETAILS, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String driverInfo = cursor.getString(cursor.getColumnIndex(COLUMN_DRIVER_NAME)) +
                        " - " + cursor.getString(cursor.getColumnIndex(COLUMN_VEHICLE_MODEL)) +
                        " (" + cursor.getString(cursor.getColumnIndex(COLUMN_DRIVER_LOCATION)) + ")";
                drivers.add(driverInfo);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return drivers;
    }

    // Save Driver Availability
    public boolean saveDriverAvailability(int driverId, int startHour, int startMinute, int endHour, int endMinute) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DRIVER_ID, driverId);
        contentValues.put(COLUMN_START_HOUR, startHour);
        contentValues.put(COLUMN_START_MINUTE, startMinute);
        contentValues.put(COLUMN_END_HOUR, endHour);
        contentValues.put(COLUMN_END_MINUTE, endMinute);

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_AVAILABILITY, null, contentValues);

        return result != -1;
    }

    // Insert Rating
    public boolean insertRating(String driverName, String passengerName, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DRIVER_NAME, driverName);
        values.put(COLUMN_PASSENGER_NAME, passengerName);
        values.put(COLUMN_RATING, rating);

        long result = db.insert(TABLE_RATINGS, null, values);
        db.close();
        return result != -1;
    }
    // Method to check if a user exists
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, new String[]{username, password});
            return cursor.getCount() > 0;
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error checking user: " + e.getMessage());
            return false;
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
    }

    // Method to get user type
    public String getUserType(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_USER_TYPE + " FROM " + TABLE_USERS +
                " WHERE " + COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";

        Cursor cursor = null;
        String userType = null;
        try {
            cursor = db.rawQuery(query, new String[]{username, password});
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(COLUMN_USER_TYPE);
                if (columnIndex != -1) {
                    userType = cursor.getString(columnIndex);
                }
            }
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error getting user type: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return userType;
    }
    public boolean insertUser(String username, String password, String userType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_USER_TYPE, userType);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;  // Return true if data was inserted, false otherwise
    }
    public void logExistingTables() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (cursor.moveToFirst()) {
            do {
                Log.d("DatabaseHelper", "Table found: " + cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }


}
