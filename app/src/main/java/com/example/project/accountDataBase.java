package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class accountDataBase extends SQLiteOpenHelper {
    private static String dataBaseName = "accountDataBase";
    SQLiteDatabase accountDataBase;

    public accountDataBase(Context context){
        super(context,dataBaseName,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table account (id integer primary key,"+ "name text not null, password text not null)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
        db.execSQL("drop table if exists account");
        onCreate(db);
    }
    public void createNewAccount(String name,String password){
        ContentValues row=new ContentValues();
        row.put("name",name);
        row.put("password",password);
        accountDataBase=getWritableDatabase();
        accountDataBase.insert("account",null,row);
        accountDataBase.close();

    }
    public Cursor getAllAccounts()
    {
        accountDataBase=getReadableDatabase();
        String [] rowDetails={"name","password","id"};
        Cursor cursor=accountDataBase.query("account",rowDetails,null,null,null,null,null,null);
        if(cursor!=null)
            cursor.moveToFirst();
        accountDataBase.close();
        return cursor;
    }


}
