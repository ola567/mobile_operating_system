package com.example.lab3;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "lab3_database";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "lab3_table";
    private static final String NAME_COLUMN = "name";
    private Context contextM;
    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        contextM = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + NAME_COLUMN + " TEXT)";
        sqLiteDatabase.execSQL(query);

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
    public void insertIntoDatabase(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COLUMN, name);
        db.insert(TABLE_NAME, null, values);
        db.close();

        // SharedPreferences
        SharedPreferences sharedPreferences = contextM.getSharedPreferences("lab3_preferences", Context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        File dbFile = contextM.getDatabasePath(DB_NAME);
        Date date = new Date(dbFile.lastModified());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        editor.putString("date", sdf.format(date));
        editor.apply();
    }
    public ArrayList<String> readFromDatabase(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<String> resultNames = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                resultNames.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return resultNames;
    }
}
