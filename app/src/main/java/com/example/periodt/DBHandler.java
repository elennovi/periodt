package com.example.periodt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Serializable;
import java.util.Date;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "periodtdb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "PADUser";

    private static final String ID_COL = "id";

    private static final String EMAIL_COL = "email";
    private static final String PWD_COL = "pwd";

    private static  final String LAST_PERIOD_COL = "lastPeriod";
    private static  final String DURATION_PERIOD_COL = "durationPeriod";
    private static  final String REGULAR_PERIOD_COL = "regularPeriod";


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EMAIL_COL + " TEXT,"
                + PWD_COL + " TEXT, "
                + DURATION_PERIOD_COL + " INTEGER,"
                + REGULAR_PERIOD_COL + " TEXT, "
                + LAST_PERIOD_COL + "TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    public void registerPeriod(String lastPeriod, String duration, String regular, String uid){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(LAST_PERIOD_COL, lastPeriod);
        values.put(DURATION_PERIOD_COL, duration);
        values.put(REGULAR_PERIOD_COL, regular);

        String[] args = new String []{uid};
        db.update(TABLE_NAME, values, "id=?", args);

        db.close();
    }

    public void addNewUser(String email, String pwd) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(EMAIL_COL, email);
        values.put(PWD_COL, pwd);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public boolean findByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        //String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + EMAIL_COL + " = " + email;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE email = ? ";
        String[] selectionArgs = new String[]{email};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean correctPwd(String email, String pwd) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + EMAIL_COL + " = ?"
                + " AND " + PWD_COL + " = ?";
        String[] selectionArgs = new String[]{email, pwd};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public String getIdByEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + EMAIL_COL + " = ?";
        String[] selectionArgs = new String[]{email};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        String id = "0";
        if(cursor != null){
            if (cursor.moveToFirst()) {
                id = cursor.getString(0);
                Log.i("CURSOR_ID", id);
                cursor.close();
            }
        }
        return id;
    }
}
