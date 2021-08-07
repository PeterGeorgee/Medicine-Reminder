package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class drugDataBase extends SQLiteOpenHelper {
    private static String dataBaseName = "drugDataBase";
    SQLiteDatabase drugDataBase;

    public drugDataBase(Context context){
        super(context,dataBaseName,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table drugs (id integer primary key,"+ "name text not null,pillName text not null, portion real not null, numPerDay integer not null, startTime integer not null, imgString blob not null)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
        db.execSQL("drop table if exists drugs");
        onCreate(db);
    }

    public void createNewDrug(String name,String pillName,float portion,int numPerDay,int startDate,byte[] imgString ){
        ContentValues row=new ContentValues();
        row.put("name",name);
        row.put("pillName",pillName);
        row.put("portion",portion);
        row.put("numPerDay",numPerDay);
        row.put("startTime",startDate);
        row.put("imgString",imgString);
        drugDataBase=getWritableDatabase();
        drugDataBase.insert("drugs",null,row);
        drugDataBase.close();

    }
    public Cursor getAllDrugs()
    {

        drugDataBase=getReadableDatabase();
        String [] rd={"name","pillName","portion","numPerDay","startTime","imgString","id"};
        Cursor cursor=drugDataBase.query("drugs",rd,null,null,null,null,null,null);

        if(cursor!=null)
            cursor.moveToFirst();

        drugDataBase.close();
        return cursor;
    }
}
