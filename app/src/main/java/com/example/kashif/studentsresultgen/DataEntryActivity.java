package com.example.kashif.studentsresultgen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DataEntryActivity extends AppCompatActivity {


    private EditText name_editText;
    private EditText gender_editText;
    private EditText age_editText;
    private EditText hindi_marks_editText;
    private EditText english_marks_editText;
   // private Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        initViews();
    }


    public void initViews(){

        name_editText = (EditText) findViewById(R.id.student_name_et);
        gender_editText = (EditText) findViewById(R.id.student_gender_et);
        age_editText = (EditText) findViewById(R.id.student_age_et);
        hindi_marks_editText = (EditText) findViewById(R.id.hindi_marks_et);
        english_marks_editText = (EditText) findViewById(R.id.english_marks_et);
      //  submit_button = (Button) findViewById(R.id.submit_btn);
    }


    public void addToStudentlist(View view) {

        if (name_editText.getText().length() == 0 ||
                gender_editText.getText().length() == 0 ||
                age_editText.getText().length() == 0 ||
                hindi_marks_editText.getText().length() == 0 ||
                english_marks_editText.getText().length() == 0) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }

        //default student age to 10
        int student_age = 10;
        //default hindi & english marks to 0
        int hindi_marks = 0;
        int english_marks = 0;
        try {
            //age_editText, hindi_marks_editText, english_marks_editText inputType="number", so this should always work
            student_age = Integer.parseInt(age_editText.getText().toString());
            hindi_marks = Integer.parseInt(hindi_marks_editText.getText().toString());
            english_marks = Integer.parseInt(english_marks_editText.getText().toString());
        } catch (NumberFormatException ex) {
            Log.e("parse_Msg", "Failed to parse student age text to number: " + ex.getMessage());
        }

        String student_name = name_editText.getText().toString();
        String student_gender = gender_editText.getText().toString();

        MainActivity.addNewEntry(student_name, student_gender, student_age, hindi_marks, english_marks);
        // Update the cursor in the adapter to trigger UI to display the new list
        MainActivity.studentListAdapter.swapCursor(MainActivity.getAllStudentslist());
        finish();

    }
}
