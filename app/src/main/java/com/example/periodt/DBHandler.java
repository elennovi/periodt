package com.example.periodt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "periodtdb";

    // below int is our database version
    private static final int DB_VERSION = 5;

    // below variable is for our table name.
    private static final String USER_TABLE = "PADUser";
    private static final String TRACKING_TABLE = "PADTracking";

    private static final String ID_COL = "id";
    private static final String UID_COL = "uid";

    private static final String EMAIL_COL = "email";
    private static final String PWD_COL = "pwd";

    private static final String LAST_PERIOD_COL = "lastPeriod";
    private static final String DURATION_PERIOD_COL = "durationPeriod";
    private static final String CYCLE_PERIOD_COL = "cyclePeriod";

    private static final String DATE_COL = "day";

    private static final String YES_COL = "period_yes_btn";
    private static final String NO_COL = "period_no_btn";

    private static final String NO_SYMPTONS_COL = "no_symptons_btn";
    private static final String COLICOS_COL = "colicos_btn";
    private static final String OVULACION_COL = "ovulacion_btn";
    private static final String SENSIBILIDAD_COL = "sensibilidad_btn";
    private static final String HEADACHE_COL = "headache_btn";
    private static final String NAUSEAS_COL = "nauseas_btn";
    private static final String LUMBARES_COL = "lumbares_btn";
    private static final String PIERNAS_COL = "piernas_btn";
    private static final String ARTICULACIONES_COL = "articulaciones_btn";
    private static final String VULVAR_COL = "vulvar_btn";

    private static final String BLANCO_COL = "blanco_btn";
    private static final String AMARILLO_COL = "amarillo_btn";
    private static final String TRANSPARENTE_COL = "transparente_btn";
    private static final String VERDE_COL = "verde_btn";
    private static final String MARRON_COL = "marron_btn";
    private static final String SECO_COL = "seco_btn";
    private static final String HUMEDO_COL = "humedo_btn";
    private static final String PEGAJOSO_COL = "pegajoso_btn";
    private static final String CREMOSO_COL = "cremoso_btn";

    private static final String BAJO_COL = "bajo_btn";
    private static final String MEDIO_COL = "medio_btn";
    private static final String ALTO_COL = "alto_btn";

    private static final String LIGERO_COL = "ligero_btn";
    private static final String MODERADO_COL = "moderado_btn";
    private static final String FUERTE_COL = "fuerte_btn";
    private static final String INTENSO_COL = "intenso_btn";

    private static final String ROJO_MANCHADO_COL = "rojo_manchado_btn";
    private static final String MARRON_MANCHADO_COL = "marron_manchado_btn";

    private static final String BIENESTAR_1_COL = "btn_1";
    private static final String BIENESTAR_2_COL = "btn_2";
    private static final String BIENESTAR_3_COL = "btn_3";
    private static final String BIENESTAR_4_COL = "btn_4";
    private static final String BIENESTAR_5_COL = "btn_5";

    private static final String SPM_COL = "spm_btn";



    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query_users = "CREATE TABLE " + USER_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EMAIL_COL + " TEXT,"
                + PWD_COL + " TEXT, "
                + DURATION_PERIOD_COL + " INTEGER,"
                + CYCLE_PERIOD_COL + " TEXT, "
                + LAST_PERIOD_COL + " TEXT)";

        db.execSQL(query_users);

        String query_tracking = "CREATE TABLE " + TRACKING_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UID_COL + " INTEGER,"
                + DATE_COL + " TEXT, "
                + YES_COL + " INTEGER,"
                + NO_COL + " TEXT, "
                + NO_SYMPTONS_COL + " TEXT,"
                + COLICOS_COL + " TEXT, "
                + OVULACION_COL + " TEXT,"
                + SENSIBILIDAD_COL + " TEXT, "
                + HEADACHE_COL + " TEXT,"
                + NAUSEAS_COL + " TEXT, "
                + LUMBARES_COL + " TEXT,"
                + PIERNAS_COL + " TEXT, "
                + ARTICULACIONES_COL + " TEXT,"
                + VULVAR_COL + " TEXT, "
                + BLANCO_COL + " TEXT,"
                + AMARILLO_COL + " TEXT, "
                + TRANSPARENTE_COL + " TEXT,"
                + VERDE_COL + " TEXT, "
                + MARRON_COL + " TEXT,"
                + SECO_COL + " TEXT, "
                + HUMEDO_COL + " TEXT,"
                + PEGAJOSO_COL + " TEXT, "
                + CREMOSO_COL + " TEXT,"
                + BAJO_COL + " TEXT, "
                + MEDIO_COL + " TEXT,"
                + ALTO_COL + " TEXT, "
                + LIGERO_COL + " TEXT,"
                + MODERADO_COL + " TEXT, "
                + FUERTE_COL + " TEXT,"
                + INTENSO_COL + " TEXT, "
                + ROJO_MANCHADO_COL + " TEXT,"
                + MARRON_MANCHADO_COL + " TEXT, "
                + BIENESTAR_1_COL + " TEXT,"
                + BIENESTAR_2_COL + " TEXT, "
                + BIENESTAR_3_COL + " TEXT,"
                + BIENESTAR_4_COL + " TEXT, "
                + BIENESTAR_5_COL + " TEXT,"
                + SPM_COL + " TEXT)";

        db.execSQL(query_tracking);
    }

    public void registerPeriod(String lastPeriod, String duration, String cycle, String uid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(LAST_PERIOD_COL, lastPeriod);
        values.put(DURATION_PERIOD_COL, duration);
        values.put(CYCLE_PERIOD_COL, cycle);

        String[] args = new String[]{uid};
        db.update(USER_TABLE, values, "id=?", args);

        db.close();
    }

    public void addNewUser(String email, String pwd) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(EMAIL_COL, email);
        values.put(PWD_COL, pwd);

        db.insert(USER_TABLE, null, values);

        db.close();
    }

    public boolean findByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + USER_TABLE + " WHERE email = ? ";
        String[] selectionArgs = new String[]{email};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean correctPwd(String email, String pwd) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + EMAIL_COL + " = ?"
                + " AND " + PWD_COL + " = ?";
        String[] selectionArgs = new String[]{email, pwd};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    public String getIdByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + EMAIL_COL + " = ?";
        String[] selectionArgs = new String[]{email};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        String id = "0";
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                id = cursor.getString(0);
                Log.i("CURSOR_ID", id);
                cursor.close();
            }
        }
        return id;
    }

    public String getLastPeriod(String uid) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + ID_COL + " = ?";
        String[] selectionArgs = new String[]{uid};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        String lastPeriod = "";
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                // column 5 is lastperiod
                lastPeriod = cursor.getString(5);

                cursor.close();
            }
        }
        return lastPeriod;
    }

    public String getDuration(String uid) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + ID_COL + " = ?";
        String[] selectionArgs = new String[]{uid};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        String duration = "";
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                // column 3 is duration
                duration = cursor.getString(3);

                cursor.close();
            }
        }
        return duration;
    }

    public String getCycle(String uid) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + ID_COL + " = ?";
        String[] selectionArgs = new String[]{uid};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        String regular = "";
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                // column 3 is duration
                regular = cursor.getString(4);

                cursor.close();
            }
        }
        return regular;
    }

    public void updateTracker(Set<String> tracker, String uid){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String day = sdf.format(date);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UID_COL, uid);
        values.put(DATE_COL, day);

        for(String s : tracker){
            values.put(s, "Sí");
        }
        db.insert(TRACKING_TABLE, null, values);
        db.close();
    }
}