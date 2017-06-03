package com.example.kashif.studentsresultgen;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kashif.studentsresultgen.DataClasses.StudentListContract;

/**
 * Created by kashif on 2/6/17.
 */

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentListViewHolder> {


    // Holds on to the cursor to display the studentlist
    private Cursor mCursor;
    private Context mContext;

    //Constructor using the context(calling context/activity) and the db cursor(the db cursor with studentlist data to display)
    public StudentListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }


    @Override
    public StudentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Get the RecyclerView item layout
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.saved_students_list,parent,false);
        return new StudentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentListViewHolder holder, int position) {

        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return;

        String name = mCursor.getString(mCursor.getColumnIndex(StudentListContract.StudentlistEntry.COLUMN_STUDENT_NAME));
        String gender = mCursor.getString(mCursor.getColumnIndex(StudentListContract.StudentlistEntry.COLUMN_GENDER));
        int age = mCursor.getInt(mCursor.getColumnIndex(StudentListContract.StudentlistEntry.COLUMN_AGE));
        int hindi_marks = mCursor.getInt(mCursor.getColumnIndex(StudentListContract.StudentlistEntry.COLUMN_HINDI_MARKS));
        int english_marks = mCursor.getInt(mCursor.getColumnIndex(StudentListContract.StudentlistEntry.COLUMN_ENGLISH_MARKS));

        int total_marks = hindi_marks + english_marks;
        double average_marks = (double) (total_marks)/2;

        //Retrieve the id from the cursor
        long id = mCursor.getLong(mCursor.getColumnIndex(StudentListContract.StudentlistEntry._ID));

        // Display the student data
        holder.name_textView.setText(name);
        holder.gender_textView.setText(gender);
        holder.age_textView.setText(String.valueOf(age));
        holder.hindi_marks_textView.setText(String.valueOf(hindi_marks));
        holder.english_marks_textView.setText(String.valueOf(english_marks));
        holder.total_marks_textView.setText(String.valueOf(total_marks));
        holder.average_marks_textView.setText(String.valueOf(average_marks));

        //Set the tag of the itemview in the holder to the id
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    //Swaps the Cursor currently held in the adapter with a new one & triggers a UI Refresh
    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }


    public class StudentListViewHolder extends RecyclerView.ViewHolder{


        TextView name_textView;
        TextView gender_textView;
        TextView age_textView;
        TextView hindi_marks_textView;
        TextView english_marks_textView;
        TextView total_marks_textView;
        TextView average_marks_textView;

        public StudentListViewHolder(final View itemView) {
            super(itemView);

            name_textView = (TextView) itemView.findViewById(R.id.student_name_tv);
            gender_textView = (TextView) itemView.findViewById(R.id.gender_tv);
            age_textView = (TextView) itemView.findViewById(R.id.age_tv);
            hindi_marks_textView = (TextView) itemView.findViewById(R.id.hindi_marks_tv);
            english_marks_textView = (TextView) itemView.findViewById(R.id.english_marks_tv);
            total_marks_textView = (TextView) itemView.findViewById(R.id.total_marks_tv);
            average_marks_textView = (TextView) itemView.findViewById(R.id.average_marks_tv);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long clickedItemViewId = (long) itemView.getTag();
                    String studentName = name_textView.getText().toString();
                    String studentGender = gender_textView.getText().toString();
                    String studentAge = age_textView.getText().toString();
                    String hindiMarks = hindi_marks_textView.getText().toString();
                    String englishMarks = english_marks_textView.getText().toString();

                    Intent intent = new Intent(itemView.getContext(), EditEntryActivity.class);
                    intent.putExtra("rowId", clickedItemViewId)
                          .putExtra("name", studentName)
                          .putExtra("gender", studentGender)
                          .putExtra("age", studentAge);
                    itemView.getContext().startActivity(intent);


                }
            });

        }
    }

}
