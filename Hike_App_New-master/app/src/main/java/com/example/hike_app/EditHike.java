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
import android.widget.Toast;

import java.util.Calendar;

public class EditHike extends AppCompatActivity {
    private Toolbar toolbar;
    EditText name, location, length, date, level, description;
    Switch edit_switch;
    Button update_button;
    String hikeId, Name, Location, Date, Level, Description, Length, Parking;
    double hike_length;
    int is_parking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hike);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);

        name = findViewById(R.id.hike_name_edit_txt);
        location = findViewById(R.id.locationEdit);
        length = findViewById(R.id.lengthEdit);
        date = findViewById(R.id.dateEdit);
        level = findViewById(R.id.levelEdit);
        description = findViewById(R.id.desEdit);
        edit_switch = findViewById(R.id.edit_hike_parking);
        update_button = findViewById(R.id.update_btn);

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateName() | !validateDateHike() | !validateLengthHike() | !validateLocationHike() | !validateLevelHike()){
                    return;
                }
                DatabaseHelper db = new DatabaseHelper(EditHike.this);
                Name = name.getText().toString().trim();
                Location = location.getText().toString().trim();
                Length = length.getText().toString().trim();
                Date = date.getText().toString().trim();
                is_parking = edit_switch.isChecked() ? 1 : 0;
                Level = level.getText().toString().trim();
                Description = description.getText().toString().trim();

                db.updateHikeData(hikeId, Name, Location, Double.parseDouble(Length), Date, is_parking, Level, Description);
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogPickDate();
            }
        });
        getIntentData();
    }
    void getIntentData(){
        if(getIntent().hasExtra("hike_id") && getIntent().hasExtra("hike_name") && getIntent().hasExtra("hike_location") && getIntent().hasExtra("hike_length") && getIntent().hasExtra("hike_date") && getIntent().hasExtra("hike_parking") && getIntent().hasExtra("hike_date") && getIntent().hasExtra("hike_description")){
            hikeId = getIntent().getStringExtra("hike_id");
            Name = getIntent().getStringExtra("hike_name");
            Location = getIntent().getStringExtra("hike_location");
            Length = getIntent().getStringExtra("hike_length");
            Date = getIntent().getStringExtra("hike_date");
            Parking = getIntent().getStringExtra("hike_parking");
            Level = getIntent().getStringExtra("hike_level");
            Description = getIntent().getStringExtra("hike_description");

            is_parking = Integer.parseInt(Parking);
//            hike_length = Double.parseDouble(hikeLength);

            name.setText(Name);
            location.setText(Location);
            length.setText(Length);
            date.setText(Date);
            length.setText(Level);
            edit_switch.setChecked(is_parking == 0 ? false : true);
            description.setText(Description);
        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, DetailHike.class);
                intent.putExtra("hike_id", hikeId);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void openDialogPickDate(){
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.setText(dayOfMonth +"/"+ (month + 1) +"/"+(year));
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
        String lengthInput = length.getText().toString().trim();
        if (lengthInput.isEmpty()) {
            length.setError("Length can not be empty");
            return false;
        } else {
            length.setError(null);
            return true;
        }
    }
    private boolean validateDateHike(){
        String dateInput = date.getText().toString().trim();
        if (dateInput.isEmpty()) {
            date.setError("Date can not be empty");
            return false;
        } else {
            date.setError(null);
            return true;
        }
    }
    private boolean validateLevelHike(){
        String levelInput = level.getText().toString().trim();
        if (levelInput.isEmpty()) {
            level.setError("Level can not be empty");
            return false;
        } else {
            level.setError(null);
            return true;
        }
    }
}