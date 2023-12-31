package com.example.hike_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.hike_app.models.HikeModel;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button save_btn;
    private DatabaseHelper db;
    private EditText name, location, length,  level,  description;
    private Switch parking;
    private TextView date;
    boolean parkingValue = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_hike);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);

        save_btn = findViewById(R.id.save_button);
        name = findViewById(R.id.name);
        location = findViewById(R.id.destination);
        length = findViewById(R.id.length);
        date = findViewById(R.id.date);
        parking = findViewById(R.id.hike_parking);
        level = findViewById(R.id.level);
        description = findViewById(R.id.description);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateName() | !validateDateHike() | !validateLengthHike() | !validateLocationHike() | !validateLevelHike()){
                    return;
                }
                HikeModel hikeModel = new HikeModel(-1, name.getText().toString(), location.getText().toString(), date.getText().toString(), parking.isChecked(), Double.parseDouble(length.getText().toString()), level.getText().toString(), description.getText().toString());
                DatabaseHelper db = new DatabaseHelper(AddActivity.this);
                db.addHike(hikeModel);
                name.setText("");
                location.setText("");
                length.setText("");
                level.setText("");
                date.setText("");
                parking.setChecked(parkingValue);
                description.setText("");
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogPickDate();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home);
        return super.onOptionsItemSelected(item);
    }
    private void openDialogPickDate(){
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.setText(String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
            }
        }, currentYear, currentMonth, currentDay);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }
    private boolean validateName() {
        String nameInput = name.getText().toString().trim();
        if(nameInput.isEmpty()){
            name.setError("Name can not be empty");
            return false;
        } else {
            name.setError(null);
            return true;
        }
    }
    private boolean validateLocationHike() {
        String locationInput = location.getText().toString().trim();
        if (locationInput.isEmpty()) {
            location.setError("Location can not be empty");
            return false;
        } else {
            location.setError(null);
            return true;
        }
    }
    private boolean validateLengthHike() {
        String InputLength = length.getText().toString().trim();
        if (InputLength.isEmpty()) {
            length.setError("Length can not be empty");
            return false;
        } else {
            length.setError(null);
            return true;
        }
    }
    private boolean validateDateHike(){
        String inputDate = date.getText().toString().trim();
        if (inputDate.isEmpty()) {
            date.setError("Date can not be empty");
            return false;
        } else {
            date.setError(null);
            return true;
        }
    }
    private boolean validateLevelHike(){
        String inputLevel = level.getText().toString().trim();
        if (inputLevel.isEmpty()) {
            level.setError("Level can not be empty");
            return false;
        } else {
            level.setError(null);
            return true;
        }
    }
}