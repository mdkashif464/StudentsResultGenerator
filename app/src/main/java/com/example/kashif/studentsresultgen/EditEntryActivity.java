package com.example.kashif.studentsresultgen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditEntryActivity extends AppCompatActivity {

    private TextView editingTitleTextView;
    private TextView nameTextView;
    private TextView genderTextView;
    private TextView ageTextView;
    private EditText hindiMarksEditText;
    private EditText englishMarksEditText;

    long rowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        editingTitleTextView = (TextView) findViewById(R.id.editing_title_tv);
        nameTextView = (TextView) findViewById(R.id.name_tv);
        genderTextView = (TextView) findViewById(R.id.gender_tv);
        ageTextView = (TextView) findViewById(R.id.age_tv);
        hindiMarksEditText = (EditText) findViewById(R.id.hindiMarks_et);
        englishMarksEditText = (EditText) findViewById(R.id.englishMarks_et);

        Intent intent = getIntent();
        rowId = intent.getExtras().getLong("rowId");
        String name = intent.getExtras().getString("name");
        String gender = intent.getExtras().getString("gender");
        String age = intent.getExtras().getString("age");

        editingTitleTextView.append(name);
        nameTextView.append(name);
        genderTextView.append(gender);
        ageTextView.append(age);

    }


    public void updateStudentlist(View view){

        if (hindiMarksEditText.getText().length() == 0 ||
                englishMarksEditText.getText().length() == 0){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }

        int hindiMarks = Integer.parseInt(hindiMarksEditText.getText().toString());
        int englishMarks = Integer.parseInt(englishMarksEditText.getText().toString());
        MainActivity.updateEntry(rowId, hindiMarks, englishMarks);
        // Update the cursor in the adapter to trigger UI to display the new list
        MainActivity.studentListAdapter.swapCursor(MainActivity.getAllStudentslist());
        finish();
    }
}
