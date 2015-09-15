package com.febriaroosita.swt;

/**
 * Created by Febria on 5/4/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Febria on 5/4/2015.
 */
public class DbFile extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "iEditor";
    public static final String TABLE_NAME = "file";
    public static final String COLUMNS[] = {"id_file", "Nama_file", "Tanggal_create", "Tanggal_update", "path"};


    public DbFile(Context context)
    {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("drop table if exists " + TABLE_NAME );

        db.execSQL(
                "create table if not exists " + TABLE_NAME +
                        "(id_file  integer primary key, Nama_file text, Tanggal_create text, Tanggal_update text, path text)"
        );

    }
    public void create(SQLiteDatabase db)
    {
        db.execSQL(
                "create table if not exists " + TABLE_NAME +
                        "(id_file  integer primary key, Nama_file text, Tanggal_create text, Tanggal_update text, path text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("drop table if exists " + TABLE_NAME );
        db.execSQL(
                "create table if not exists " + TABLE_NAME +
                        "(id_file  integer primary key, Nama_file text, Tanggal_create text, Tanggal_update text, path text)"
        );
    }


    public void upgradeTable(SQLiteDatabase db)
    {
        db.execSQL("drop table if exists " + TABLE_NAME);
        db.execSQL(
                "create table if not exists " + TABLE_NAME +
                        "(id_file  integer primary key, Nama_file text, Tanggal_create text, Tanggal_update text, path text)"
        );
    }

    public boolean insertData  (SQLiteDatabase db, CFile data)
    {
        ContentValues contentValues = new ContentValues();
        //`id_file`, `Nama_file`, `Tanggal_create`, `Tanggal_update`, `path`
        contentValues.put("id_file", data.id_file);
        contentValues.put("Nama_file", data.nama_file);
        contentValues.put("Tanggal_create", data.tanggal_create);
        contentValues.put("Tanggal_update", data.tanggal_update);
        contentValues.put("path", data.pathku);

        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }
    public CFile getDataById(SQLiteDatabase db, int id){
        CFile data = new CFile();

        Cursor cursor =  db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMNS[0] + "=" + id + "", null);
        if(cursor.moveToFirst())
        {
            //`id_file`, `Nama_file`, `Tanggal_create`, `Tanggal_update`, `path`

            data.id_file = cursor.getInt(0);
            data.nama_file = cursor.getString(1);
            data.tanggal_create = cursor.getString(2);
            data.tanggal_update = cursor.getString(3);
            data.pathku = cursor.getString(4);
        }


        return data;
    }
    public boolean getFileName(SQLiteDatabase db, String name){
        CFile data = new CFile();
        boolean status=false;
        if(cektabelexist(db)) {

            Cursor cursor = db.rawQuery("select count(*) from " + TABLE_NAME + " where " + COLUMNS[1] + "='" + name + "'", null);
            if (!cursor.moveToFirst()) {
                return status;
            }
            int count = cursor.getInt(0);
            cursor.close();
            status=count > 0;

        }
        else
        {
            db.execSQL(
                    "create table if not exists " + TABLE_NAME +
                            "(id_file  integer primary key, Nama_file text, Tanggal_create text, Tanggal_update text, path text)"
            );




        }
        return status;
    }
    public String getPathFile(SQLiteDatabase db, String name){
        CFile data = new CFile();
        boolean status=false;
        String path="";
        if(cektabelexist(db)) {
            String[] mypat = name.split("/");
            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMNS[1] + "='" + mypat[1] + "'", null);
            if (!cursor.moveToFirst()) {

            }
            path= cursor.getString(4);
            cursor.close();


        }
        return path;
    }
    public int getJumData(SQLiteDatabase db){
//        CFile data = new CFile();
        int jum=-1;
        Cursor cursor =  db.rawQuery( "select count(*) from "+TABLE_NAME , null );
        if(cursor.moveToFirst())
        {
            //`id_file`, `Nama_file`, `Tanggal_create`, `Tanggal_update`, `path`

            jum=cursor.getInt(0);

        }


        return jum;
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
    public boolean updateData (SQLiteDatabase db, CFile data)
    {
        ContentValues contentValues = new ContentValues();
        //`id_file`, `Nama_file`, `Tanggal_create`, `Tanggal_update`, `path`
        contentValues.put("id_file", data.id_file);
        contentValues.put("Nama_file", data.nama_file);
        contentValues.put("Tanggal_create", data.tanggal_create);
        contentValues.put("Tanggal_update", data.tanggal_update);
        contentValues.put("path", data.pathku);

        db.update(TABLE_NAME, contentValues, COLUMNS[0] + " = ? ", new String[]{Integer.toString(data.id_file)});
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
    public ArrayList<String> getAllJudul(SQLiteDatabase db)
    {
        ArrayList<String> array_list = new ArrayList<String>();
        //hp = new HashMap();
        Cursor cursor =  db.rawQuery( "select Nama_file from "+TABLE_NAME +"",null  );
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){

            String data;
            //`id_file`, `Nama_file`, `Tanggal_create`, `Tanggal_update`, `path`

            data= cursor.getString(0);
            array_list.add(data);
            cursor.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getAllPath(SQLiteDatabase db)
    {
        ArrayList<String> array_list = new ArrayList<String>();
        //hp = new HashMap();
        Cursor cursor =  db.rawQuery( "select path from "+TABLE_NAME +"",null  );
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){

            String data;
            //`id_file`, `Nama_file`, `Tanggal_create`, `Tanggal_update`, `path`

            data= cursor.getString(0);
            array_list.add(data);
            cursor.moveToNext();
        }
        return array_list;
    }
    public ArrayList<CFile> getAllData(SQLiteDatabase db)
    {
        ArrayList<CFile> array_list = new ArrayList<CFile>();
        //hp = new HashMap();
        Cursor cursor =  db.rawQuery( "select * from "+TABLE_NAME +"",null  );
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){

            CFile data = new CFile();
            //`id_file`, `Nama_file`, `Tanggal_create`, `Tanggal_update`, `path`

            data.id_file = cursor.getInt(0);
            data.nama_file = cursor.getString(1);
            data.tanggal_create = cursor.getString(2);
            data.tanggal_update = cursor.getString(3);
            data.pathku = cursor.getString(4);
            array_list.add(data);
            cursor.moveToNext();
        }
        return array_list;
    }
}
