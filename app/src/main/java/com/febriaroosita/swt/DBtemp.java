package com.febriaroosita.swt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by febria on 11/16/15.
 */
public class DBtemp extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "iEditor";
    public static final String TABLE_NAME = "tempsalah";
    public static final String COLUMNS[] = {"id_kata", "kata", "weight", "tanggal", "Jumlah_digunakan"};


    private HashMap hp;

    public DBtemp(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table if not exists " + TABLE_NAME +
                        "(id_kata integer primary key, kata text, weight double, tanggal date, Jumlah_digunakan integer)"
        );

    }
    public void createtable(SQLiteDatabase db)
    {
        db.execSQL(
                "create table if not exists " + TABLE_NAME +
                        "(id_kata integer primary key, kata text, weight double, tanggal date, Jumlah_digunakan integer)"
        );


    }
    public Integer getJumDB(SQLiteDatabase db){
        CKata data = new CKata();
        int lastId = 0;
        Cursor cursor =  db.rawQuery( "select * from "+TABLE_NAME+" order by "+COLUMNS[0]+" desc", null );
        if(cursor.moveToFirst())
        {
            lastId = cursor.getInt(0);
        }
        if(cursor != null)
            cursor.close();


        return (lastId+1);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("drop table if exists " + TABLE_NAME );
        db.execSQL(
                "create table if not exists " + TABLE_NAME +
                        "(id_kata integer primary key, kata text, weight float, tanggal date, Jumlah_digunakan integer)"
        );
    }


    public void upgradeTable(SQLiteDatabase db)
    {
        db.execSQL("drop table if exists " + TABLE_NAME );
        db.execSQL(
                "create table if not exists " + TABLE_NAME +
                        "(id_kata integer primary key, kata text, weight float, tanggal date, Jumlah_digunakan integer)"
        );
    }
    public boolean getDataByKata(SQLiteDatabase db, String kata){
        CKata data = new CKata();

        Cursor cursor =  db.rawQuery( "select * from "+TABLE_NAME+" where "+COLUMNS[1]+"='"+kata.toLowerCase()+"'", null );
        if(cursor.moveToFirst())
        {
            if(cursor != null)
                cursor.close();
            return true;
            //data.id_kata = cursor.getInt(0);
            //data.kata = cursor.getString(1);
        }
        if(cursor != null)
            cursor.close();


        return false;
    }
    public Integer getJumKata(SQLiteDatabase db, String kata){
        CKata data = new CKata();

        Cursor cursor =  db.rawQuery( "select jumlah_digunakan from "+TABLE_NAME+" where "+COLUMNS[1]+"='"+kata.toLowerCase()+"'", null );
        int temp=0;
        if(cursor.moveToFirst())
        {
            temp = cursor.getInt(0);
            //data.id_kata = cursor.getInt(0);
            //data.kata = cursor.getString(1);
        }
        if(cursor != null)
            cursor.close();


        return temp;
    }
    public boolean insertData (SQLiteDatabase db, CHistory data)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put("id_kata", data.id_kata);
        contentValues.put("kata", data.kata);
        contentValues.put("weight", data.weight);
        contentValues.put("tanggal", data.tanggal);
        contentValues.put("Jumlah_digunakan", data.jumlah_digunakan);
        //"id_kata", "kata", "weight", "tanggal", "Jumlah_digunakan"

        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }
    public CHistory getDataById(SQLiteDatabase db, int id){
        CHistory data = new CHistory();

        Cursor cursor =  db.rawQuery( "select * from "+TABLE_NAME+" where "+COLUMNS[0]+"="+id+"", null );
        if(cursor.moveToFirst())
        {
            data.id_kata = cursor.getInt(0);
            data.kata = cursor.getString(1);
            data.weight = cursor.getDouble(2);
            data.tanggal = cursor.getString(3);
            data.jumlah_digunakan = cursor.getInt(4);

            //"id_kata", "kata", "weight", "tanggal", "Jumlah_digunakan"
        }
        if(cursor != null)
            cursor.close();

        return data;
    }
    public boolean cektabelexist(SQLiteDatabase db)
    {
        String mybase="sqlite_master";
        Cursor cursor;
        cursor = db.rawQuery("SELECT count(*) FROM " +mybase+ " WHERE name='"+ TABLE_NAME+"'",null);
        if (!cursor.moveToFirst())
        {
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }
    public boolean updateData (SQLiteDatabase db, CHistory data)
    {
        //"id_kata", "kata", "weight", "tanggal", "Jumlah_digunakan"
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_kata", data.id_kata);
        contentValues.put("kata", data.kata);
        contentValues.put("weight", data.weight);
        contentValues.put("tanggal", data.tanggal);
        contentValues.put("Jumlah_digunakan", data.jumlah_digunakan);

        db.update(TABLE_NAME, contentValues, COLUMNS[0] + " = ? ", new String[]{Integer.toString(data.id_kata)});
        return true;
    }
    public boolean updateDatacount (SQLiteDatabase db, String data,int jum)
    {
        //"id_kata", "kata", "weight", "tanggal", "Jumlah_digunakan"
        ContentValues contentValues = new ContentValues();
        contentValues.put("Jumlah_digunakan", jum);

        db.update(TABLE_NAME, contentValues, COLUMNS[1] +" = ? ", new String[] { data } );
        return true;
    }

    public Integer deleteData (SQLiteDatabase db,Integer id)
    {
        return db.delete(TABLE_NAME,  COLUMNS[0] +" = ? ",  new String[] { Integer.toString(id) });
    }

    public Integer deleteAllData (SQLiteDatabase db)
    {
        return db.delete(TABLE_NAME,  null,  null);
    }

    public ArrayList<CHistory> getAllData(SQLiteDatabase db)
    {
        ArrayList<CHistory> array_list = new ArrayList<CHistory>();
        //hp = new HashMap();
        Cursor cursor =  db.rawQuery( "select * from "+TABLE_NAME +"",null  );
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){

            CHistory data = new CHistory();
            data.id_kata = cursor.getInt(0);
            data.kata = cursor.getString(1);
            data.weight = cursor.getDouble(2);
            data.tanggal = cursor.getString(3);
            data.jumlah_digunakan = cursor.getInt(4);
            array_list.add(data);
            cursor.moveToNext();
        }
        if(cursor != null)
            cursor.close();
        return array_list;
    }
}
