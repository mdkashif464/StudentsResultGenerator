package com.example.kashif.studentsresultgen;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kashif.studentsresultgen.DataClasses.StudentListContract;
import com.example.kashif.studentsresultgen.DataClasses.StudentListDbHelper;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private RecyclerView studentListRecyclerView;
    public static StudentListAdapter studentListAdapter;
    public static SQLiteDatabase mDb;
    private TextView highestScorerHindi_tv;
    private TextView highestScorerEnglish_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set local attributes to corresponding views
        highestScorerHindi_tv = (TextView) findViewById(R.id.highest_hindi_scorer_tv);
        highestScorerEnglish_tv = (TextView) findViewById(R.id.highest_english_scorer_tv);
        studentListRecyclerView = (RecyclerView) findViewById(R.id.saved_students_list_rv);
        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        studentListRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Create a DB helper (this will create the DB if run for the first time)
        StudentListDbHelper dbHelper = new StudentListDbHelper(this);

        // Keep a reference to the mDb until paused or killed. Get a writable database
        // because you will be adding student data
        mDb = dbHelper.getWritableDatabase();

        // Get all guest info from the database and save in a cursor
        Cursor cursor = getAllStudentslist();

        // Create an adapter for that cursor to display the data
        studentListAdapter = new StudentListAdapter(this, cursor);

        // Link the adapter to the RecyclerView
        studentListRecyclerView.setAdapter(studentListAdapter);


        //new ItemTouchHelper with a SimpleCallback that handles both LEFT and RIGHT swipe directions
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                long id = (long) viewHolder.itemView.getTag();
                removeEntry(id);
                getAndSetHighestScorers();
                Toast.makeText(MainActivity.this,"Entry deleted",Toast.LENGTH_LONG).show();
                studentListAdapter.swapCursor(getAllStudentslist());
            }
        }).attachToRecyclerView(studentListRecyclerView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAndSetHighestScorers();
    }

    private void getAndSetHighestScorers(){
        //getting and setting student name with highest marks in hindi
        Cursor maxHindiMarksCursor = getStudentWithMaxHindiMarks();
        if (maxHindiMarksCursor.getCount() == 0){
            highestScorerHindi_tv.setText("");
            highestScorerEnglish_tv.setText("");
            return;
        }
        maxHindiMarksCursor.moveToFirst();
        String studentWithMaxHindiMarks = maxHindiMarksCursor.getString(maxHindiMarksCursor.getColumnIndex(StudentListContract.StudentlistEntry.COLUMN_STUDENT_NAME));
        highestScorerHindi_tv.setText(studentWithMaxHindiMarks);


        //getting and setting student name with highest marks in hindi
        Cursor maxEnglishMarksCursor = getStudentWithMaxEnglishMarks();
        maxEnglishMarksCursor.moveToFirst();
        String studentWithMaxEnglishMarks = maxEnglishMarksCursor.getString(maxEnglishMarksCursor.getColumnIndex(StudentListContract.StudentlistEntry.COLUMN_STUDENT_NAME));
        highestScorerEnglish_tv.setText(studentWithMaxEnglishMarks);
    }



    //Query the mDb and get all studentslist from the studentlist table sorted by TimeStamp
    //By default student list will be sorted by TimeStamp
    public static Cursor getAllStudentslist() {
        return mDb.query(
                StudentListContract.StudentlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                StudentListContract.StudentlistEntry.COLUMN_TIMESTAMP
        );
    }


    //Query the mDb and get all studentslist from the studentlist table sorted by gender
    public static Cursor getAllStudentslistSortedByGender() {
        return mDb.query(
                StudentListContract.StudentlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                StudentListContract.StudentlistEntry.COLUMN_GENDER
        );
    }

    //Query the mDb and get all studentslist from the studentlist table sorted by Hindi Marks
    public static Cursor getAllStudentslistSortedByHindiMarks() {
        return mDb.query(
                StudentListContract.StudentlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                StudentListContract.StudentlistEntry.COLUMN_HINDI_MARKS
        );
    }

    //Query the mDb and get all studentslist from the studentlist table sorted by English Marks
    public static Cursor getAllStudentslistSortedByEnglishMarks() {
        return mDb.query(
                StudentListContract.StudentlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                StudentListContract.StudentlistEntry.COLUMN_ENGLISH_MARKS
        );
    }


    //Query the mDb and get studentslist Entry with highest marks in Hindi
    public static Cursor getStudentWithMaxHindiMarks(){
        return mDb.query(
                StudentListContract.StudentlistEntry.TABLE_NAME,
                null,
                ""+ StudentListContract.StudentlistEntry.COLUMN_HINDI_MARKS+" = (SELECT MAX("+StudentListContract.StudentlistEntry.COLUMN_HINDI_MARKS+") FROM "+StudentListContract.StudentlistEntry.TABLE_NAME+")",
                null,
                null,
                null,
                null
        );
    }


    //Query the mDb and get studentslist Entry with highest marks in Hindi
    public static Cursor getStudentWithMaxEnglishMarks(){
        return mDb.query(
                StudentListContract.StudentlistEntry.TABLE_NAME,
                null,
                ""+ StudentListContract.StudentlistEntry.COLUMN_ENGLISH_MARKS+" = (SELECT MAX("+StudentListContract.StudentlistEntry.COLUMN_ENGLISH_MARKS+") FROM "+StudentListContract.StudentlistEntry.TABLE_NAME+")",
                null,
                null,
                null,
                null
        );
    }



    //Adds a new student entry to the mDb
    public static long addNewEntry(String name, String gender, int age, int hindi_marks, int english_marks) {
        ContentValues cv = new ContentValues();
        cv.put(StudentListContract.StudentlistEntry.COLUMN_STUDENT_NAME, name);
        cv.put(StudentListContract.StudentlistEntry.COLUMN_GENDER, gender);
        cv.put(StudentListContract.StudentlistEntry.COLUMN_AGE, age);
        cv.put(StudentListContract.StudentlistEntry.COLUMN_HINDI_MARKS, hindi_marks);
        cv.put(StudentListContract.StudentlistEntry.COLUMN_ENGLISH_MARKS, english_marks);
        return mDb.insert(StudentListContract.StudentlistEntry.TABLE_NAME, null, cv);
    }


    //function that takes long id as input and returns a boolean
    private boolean removeEntry(long id){
        return mDb.delete(StudentListContract.StudentlistEntry.TABLE_NAME, StudentListContract.StudentlistEntry._ID + "=" + id, null) > 0;
    }


    //function to update studentlist entries
    public static boolean updateEntry(long rowId, int hindiMarks, int englishMarks){
        ContentValues cv = new ContentValues();
        cv.put(StudentListContract.StudentlistEntry.COLUMN_HINDI_MARKS, hindiMarks);
        cv.put(StudentListContract.StudentlistEntry.COLUMN_ENGLISH_MARKS, englishMarks);
        return mDb.update(StudentListContract.StudentlistEntry.TABLE_NAME, cv, StudentListContract.StudentlistEntry._ID + "=" + rowId, null) > 0;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int clickedItemId = item.getItemId();
        switch (clickedItemId){
            case R.id.add_new_entry: {
                Intent intent = new Intent(this, DataEntryActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.sortBy_gender: {
                studentListAdapter.swapCursor(getAllStudentslistSortedByGender());
                break;
            }

            case R.id.sortBy_hindiMarks: {
                studentListAdapter.swapCursor(getAllStudentslistSortedByHindiMarks());
                break;
            }

            case R.id.sortBy_englishMarks: {
                studentListAdapter.swapCursor(getAllStudentslistSortedByEnglishMarks());
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
