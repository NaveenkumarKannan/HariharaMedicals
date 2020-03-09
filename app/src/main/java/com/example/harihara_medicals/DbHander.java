package com.example.harihara_medicals;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.jar.Attributes;

public class DbHander extends SQLiteOpenHelper {
    private static final String DN="Harihara Medicals";
    private  static final int DV=1;
    private static final String TN="remainder";
    private  static final  String ID="id";
    private static final String TITLE="title";
    private static final String LOCATION="location";
    private static final String DATE="date";
    private static final String TIME="time";
    private static final String DESCRIPTION="description";

    public DbHander(Context context) {
        super(context, DN, null, DV);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TN+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+TITLE+" VARCHAR" +
                ","+LOCATION+"VARCHAR,"+DATE+"VARCHAR,"+TIME+"VARCHAR,"+DESCRIPTION+"VARCHAR)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TN);
        onCreate(db);

    }
    public String insert(String title,String location,String date, String time,String description){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(TITLE,title);
        values.put(LOCATION,location);
        values.put(TITLE,date);
        values.put(TIME,time);
        values.put(DESCRIPTION,description);
        return null;
    }
    public  String get(){
        String query="SELECT * FROM "+TN;
        String result="";
        SQLiteDatabase dp=this.getReadableDatabase();
        Cursor cursor=dp.rawQuery(query,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            result+=cursor.getString(0)+" "+cursor.getString(1)+"\n";
            cursor.moveToNext();
        }
        dp.close();
        return  result;
    }
}

