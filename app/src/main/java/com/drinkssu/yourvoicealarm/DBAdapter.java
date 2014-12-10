package com.drinkssu.yourvoicealarm;

/**
 * Created by KSM on 2014-12-04.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "drinkssu.db";
    static final String TABLE_ALARM = "alarm";
    static final int DB_VERSION = 1;


    // alarm table
    public static final String ALARM_ON = "onoff";     // ON / OFF
    public static final String ALARM_APDAY = "apday";  // 적용일
    public static final String ALARM_HOUR = "hour";   // 적용일 : 시
    public static final String ALARM_MINUTE = "minute"; // 적용일 : 분
    public static final String ALARM_VIBRATE = "vibrate";// vibrate
    public static final String ALARM_RINGTONE = "ring";   // 링톤 name
    public static final String ALARM_RINGPATH = "ringpath"; // 링톤 path

    Context mContext = null;
    private static DBAdapter mDBManager = null;
    private SQLiteDatabase mDatabase = null;


    public static DBAdapter getInstance(Context context) {
        if (mDBManager == null) {
            mDBManager = new DBAdapter(context, DATABASE_NAME, null, DB_VERSION);
        }
        return mDBManager;
    }

    public DBAdapter(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbName, factory, version);

        mContext = context;
    }

    public Cursor fetchAllAlarm() {
        return mDBManager.query(null, null, null, null, null, ALARM_HOUR + " asc, " + ALARM_MINUTE + " asc");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ALARM +
                "(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "onoff    INTEGER, " +
                "apday    INTEGER, " +
                "hour     INTEGER, " +
                "minute   INTEGER, " +
                "vibrate  INTEGER, " +
                "ring     TEXT, " +
                "ringpath TEXT ); ");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public long insert(ContentValues addRowValue) {
        return getWritableDatabase().insert(TABLE_ALARM, null, addRowValue);
    }


    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return getReadableDatabase().query(TABLE_ALARM,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy);
    }

    public int update(ContentValues updateRowValue, String whereClause, String[] whereArgs) {
        return getWritableDatabase().update(TABLE_ALARM,
                updateRowValue,
                whereClause,
                whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs) {
        return getWritableDatabase().delete(TABLE_ALARM,
                whereClause,
                whereArgs);
    }

    public void modifyAlarmOn(long id, int on) {
        ContentValues values = new ContentValues();
        values.put(ALARM_ON, on);

        mDBManager.update(values, "_id" + "='" + id + "'", null);
    }

    public void delAlarm(String id) {
        mDBManager.delete("_id = ?", new String[]{id});

    }

    public String addAlarm(int on, int day, int hour, int min, int vib, String ring) {
        ContentValues values = new ContentValues();
        values.put(ALARM_ON, on);
        values.put(ALARM_APDAY, day);
        values.put(ALARM_HOUR, hour);
        values.put(ALARM_MINUTE, min);
        values.put(ALARM_RINGTONE, ring);
        values.put(ALARM_VIBRATE, vib);

        long id = mDBManager.insert(values);
        if (id < 0) {
            return "";
        }
        return Long.toString(id);
    }

    public void modifyAlarm(long id, int on, int day, int hour, int min, int vib, String ring) {
        ContentValues values = new ContentValues();
        values.put(ALARM_ON, on);
        values.put(ALARM_APDAY, day);
        values.put(ALARM_HOUR, hour);
        values.put(ALARM_MINUTE, min);
        values.put(ALARM_RINGTONE, ring);
        values.put(ALARM_VIBRATE, vib);

        mDBManager.update(values, "_id" + "='" + id + "'", null);
    }
}
