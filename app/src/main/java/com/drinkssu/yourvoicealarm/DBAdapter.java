package com.drinkssu.yourvoicealarm;

/**
 * Created by KSM on 2014-12-04.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
    private final Context context;
    static final String DATABASE_NAME = "drinkssu.db";
    static final String TABLE_ALARM = "alarm";
    static final int DB_VERSION = 1;


    // alarm table
    public static final String ALARM_ON        = "onoff";     // ON / OFF
    public static final String ALARM_APDAY     = "apday";  // 적용일
    public static final String ALARM_HOUR      = "hour";   // 적용일 : 시
    public static final String ALARM_MINUTE    = "minute"; // 적용일 : 분
    public static final String ALARM_RINGTONE  = "ring";   // 링톤
    public static final String ALARM_VIBRATE   = "vibrate";// vibrate

    static final String CREATE_ALARM = "create table " + TABLE_ALARM +
            " (_id integer primary key autoincrement, " +
            ALARM_ON + " integer, " +
            ALARM_APDAY + " integer, " +
            ALARM_HOUR + " integer, " +
            ALARM_MINUTE + " integer, " +
            ALARM_VIBRATE + " integer, " +
            ALARM_RINGTONE + " text);";


    static final String DROP = "drop table ";
    private SQLiteDatabase db;
    private NoteOpenHelper dbHelper;

    public DBAdapter(Context ctx) {
        context = ctx;
    }

    private static class NoteOpenHelper extends SQLiteOpenHelper {
        public NoteOpenHelper(Context c) {
            super(c, DATABASE_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_ALARM);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
            // TODO Auto-generated method stub
        }
    }

    public DBAdapter open() throws SQLException {
        dbHelper = new NoteOpenHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public Cursor fetchAllAlarm() {
        return db.query(TABLE_ALARM, null, null, null, null, null, ALARM_HOUR + " asc, " + ALARM_MINUTE + " asc");
    }

    public String addAlarm(int on, int day, int hour, int min, int vib, String ring) {
        ContentValues values = new ContentValues();
        values.put(ALARM_ON, on);
        values.put(ALARM_APDAY, day);
        values.put(ALARM_HOUR, hour);
        values.put(ALARM_MINUTE, min);
        values.put(ALARM_RINGTONE, ring);
        values.put(ALARM_VIBRATE, vib);

        long id = db.insert(TABLE_ALARM, null, values);
        if (id < 0) {
            return "";
        }
        return Long.toString(id);
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    public void delAlarm(String id) {
        db.delete(TABLE_ALARM, "_id = ?", new String[] {id});
    }

    public void modifyAlarm(long id, int on, int day, int hour, int min, int vib, String ring) {
        ContentValues values = new ContentValues();
        values.put(ALARM_ON, on);
        values.put(ALARM_APDAY, day);
        values.put(ALARM_HOUR, hour);
        values.put(ALARM_MINUTE, min);
        values.put(ALARM_RINGTONE, ring);
        values.put(ALARM_VIBRATE, vib);

        db.update(TABLE_ALARM, values, "_id" + "='" + id + "'", null);
    }

    public void modifyAlarmOn(long id, int on) {
        ContentValues values = new ContentValues();
        values.put(ALARM_ON, on);

        db.update(TABLE_ALARM, values, "_id" + "='" + id + "'", null);
    }



}
