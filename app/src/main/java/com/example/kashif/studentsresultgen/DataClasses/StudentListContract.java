package com.example.kashif.studentsresultgen.DataClasses;

import android.provider.BaseColumns;

/**
 * Created by kashif on 2/6/17.
 */

public class StudentListContract {

    public static final class StudentlistEntry implements BaseColumns {
        public static final String TABLE_NAME = "studentlist";
        public static final String COLUMN_STUDENT_NAME = "studentName";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_HINDI_MARKS = "hindiMarks";
        public static final String COLUMN_ENGLISH_MARKS = "englishMarks";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

}
