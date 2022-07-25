package com.example.filerecorder;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class NewFileActivity extends AppCompatActivity {

    // creating a constant string variable for our
    // course name, description and duration.
    public static final String EXTRA_ID = "com.gtappdevelopers.gfgroomdatabase.EXTRA_ID";
    public static final String EXTRA_FILE_NAME = "com.gtappdevelopers.gfgroomdatabase.EXTRA_COURSE_NAME";
    public static final String EXTRA_DATEIN = "com.gtappdevelopers.gfgroomdatabase.EXTRA_COURSE_DESCRIPTION";
    public static final String EXTRA_DATEOUT = "com.gtappdevelopers.gfgroomdatabase.EXTRA_COURSE_DURATION";
    // creating a variables for our button and edittext.
    private EditText fileNameET, dateInET, dateOutET;
    private Button saveBtn;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_file);

        // initializing our variables for each view.
        fileNameET = findViewById(R.id.fileName);
        dateInET = findViewById(R.id.dateIn);
        dateOutET = findViewById(R.id.dateOut);
        saveBtn = findViewById(R.id.idBtnSaveCourse);

        // below line is to get intent as we
        // are getting data via an intent.
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            // if we get id for our data then we are
            // setting values to our edit text fields.
            fileNameET.setText(intent.getStringExtra(EXTRA_FILE_NAME));
            dateInET.setText(intent.getStringExtra(EXTRA_DATEIN));
            dateOutET.setText(intent.getStringExtra(EXTRA_DATEOUT));
        }
        dateInET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String selectedDate = day + "/" + (month + 1) + "/" + year;
                        dateInET.setText(selectedDate);
                    }
                };
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(NewFileActivity.this, onDateSetListener, year, month, day);
                datePickerDialog.show();
            }
        });
        dateOutET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String selectedDate = day + "/" + (month + 1) + "/" + year;
                        dateOutET.setText(selectedDate);
                    }
                };
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(NewFileActivity.this, onDateSetListener, year, month, day);
                datePickerDialog.show();
            }
        });
        // adding on click listener for our save button.
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting text value from edittext and validating if
                // the text fields are empty or not.
                String fileName = fileNameET.getText().toString();
                String dateIn = dateInET.getText().toString();
                String dateOut = dateOutET.getText().toString();
                if (fileName.isEmpty() || dateIn.isEmpty() || dateOut.isEmpty()) {
                    Toast.makeText(NewFileActivity.this, "Please enter the valid details.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to save our course.
                saveFile(fileName, dateIn, dateOut);
            }
        });
    }

    private void saveFile(String fileName, String dateIn, String dateOut) {
        // inside this method we are passing
        // all the data via an intent.
        Intent data = new Intent();

        // in below line we are passing all our course detail.
        data.putExtra(EXTRA_FILE_NAME, fileName);
        data.putExtra(EXTRA_DATEIN, dateIn);
        data.putExtra(EXTRA_DATEOUT, dateOut);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            // in below line we are passing our id.
            data.putExtra(EXTRA_ID, id);
        }

        // at last we are setting result as data.
        setResult(RESULT_OK, data);

        // displaying a toast message after adding the data
        Toast.makeText(this, "File has been saved to Room Database. ", Toast.LENGTH_SHORT).show();
    }
}