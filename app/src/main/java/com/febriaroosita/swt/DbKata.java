package com.febriaroosita.swt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;

public class DbKata extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "iEditor";
    public static final String TABLE_NAME = "kata";
    public static final String COLUMNS[] = {"id_kata","kata", "Jumlah_digunakan","tipe"};

    public DbKata(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table if not exists " + TABLE_NAME +
                        "(id_kata integer primary key, kata text  primary key,jumlah_digunakan integer, tipe text)"
        );
        db.execSQL(
                "create table if not exists tempsalah" +
                        "(id_kata integer primary key, kata text, weight double, tanggal date, Jumlah_digunakan integer)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
       // db.execSQL("drop table if exists " + TABLE_NAME );
        db.execSQL(
                "create table if not exists " + TABLE_NAME +
                        "(id_kata integer primary key, kata text  primary key,jumlah_digunakan integer, tipe text)"
        );
    }

    public void create(SQLiteDatabase db)
    {
        db.execSQL(
                "create table if not exists " + TABLE_NAME +
                        "(id_kata integer primary key, kata text,jumlah_digunakan integer, tipe text)"
        );
    }
    public void upgradeTable(SQLiteDatabase db)
    {
        db.execSQL("drop table if exists " + TABLE_NAME);
        db.execSQL(
                "create table if not exists " + TABLE_NAME +
                        "(id_kata integer primary key, kata text,jumlah_digunakan integer, tipe text)"
        );
    }

    public boolean insertData  (SQLiteDatabase db, CKata data)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put("id_kata", data.id_kata);
        contentValues.put("kata", data.kata);
        contentValues.put("jumlah_digunakan", 0);
        contentValues.put("tipe", data.tipe);

        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean getDataByKata(SQLiteDatabase db, String kata){
        //CKata data = new CKata(); g dipake

        Cursor cursor =  db.rawQuery( "select * from "+TABLE_NAME+" where "+COLUMNS[1]+"='"+kata.toLowerCase()+"'", null );
        if(cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
    public Integer getJumKata(SQLiteDatabase db, String kata){
        CKata data = new CKata();

        Cursor cursor =  db.rawQuery( "select jumlah_digunakan from "+TABLE_NAME+" where "+COLUMNS[1]+"='"+kata.toLowerCase()+"'", null );
        if(cursor.moveToFirst())
        {
            int temp= cursor.getInt(0);
            if(cursor != null)
                cursor.close();

            return temp;
            //data.id_kata = cursor.getInt(0);
            //data.kata = cursor.getString(1);
        }
        if(cursor != null)
            cursor.close();


        return 0;
    }
    public String getDataByKataku(SQLiteDatabase db, String kata){
        CKata data = new CKata();

        Cursor cursor =  db.rawQuery( "select * from "+TABLE_NAME+" where "+COLUMNS[1]+"='"+kata.toLowerCase()+"'", null );
        if(cursor.moveToFirst())
        {
            if(cursor != null)
                cursor.close();
            return "ada";
            //data.id_kata = cursor.getInt(0);
            //data.kata = cursor.getString(1);
        }
        if(cursor != null)
            cursor.close();


        return "tidakada";
    }
    public String getCekKataVowel(SQLiteDatabase db, String kata){
        CKata data = new CKata();
        String myKata = "%".concat("kata");

        Cursor cursor =  db.rawQuery( "select kata from "+TABLE_NAME+" where "+COLUMNS[1]+"='"+myKata.toLowerCase()+"'", null );
        if(cursor.moveToFirst())
        {
            if(cursor != null)
                cursor.close();

            //data.id_kata = cursor.getInt(0);
            return cursor.getString(0);
        }
        if(cursor != null)
            cursor.close();


        return null;
    }
    public int getNumberDB(SQLiteDatabase db){
        CKata data = new CKata();

        Cursor cursor =  db.rawQuery( "select count(*) from "+TABLE_NAME, null );
        if(cursor.moveToFirst())
        {
            int my=cursor.getInt(0);
            if(cursor != null)
                cursor.close();
            return my;
            //data.id_kata = cursor.getInt(0);
            //data.kata = cursor.getString(1);
        }
        if(cursor != null)
            cursor.close();

        return 0;
        //return false;
    }

    public String getType(SQLiteDatabase db,String kata){
        CKata data = new CKata();

        Cursor cursor =  db.rawQuery("select tipe from "+TABLE_NAME+" where "+COLUMNS[1]+"='"+kata.toLowerCase()+"'",null );
        if(cursor.moveToFirst())
        {
            String my=cursor.getString(0);
            if(cursor != null)
                cursor.close();
            return my;
            //data.id_kata = cursor.getInt(0);
            //data.kata = cursor.getString(1);
        }
        if(cursor != null)
            cursor.close();

        return "";
        //return false;
    }



    public CKata getDataById(SQLiteDatabase db, int id){
        CKata data = new CKata();

        Cursor cursor =  db.rawQuery( "select * from "+TABLE_NAME+" where "+COLUMNS[0]+"="+id+"", null );
        if(cursor.moveToFirst())
        {
            data.id_kata = cursor.getInt(0);
            data.kata = cursor.getString(1);
            data.jumlah = cursor.getInt(2);
            data.tipe = cursor.getString(3);
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
    public boolean updateData (SQLiteDatabase db, CKata data)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_kata", data.id_kata);
        contentValues.put("kata", data.kata);
        contentValues.put("jumlah_digunakan", data.jumlah);
        contentValues.put("tipe", data.tipe);

        db.update(TABLE_NAME, contentValues, COLUMNS[0] + " = ? ", new String[]{Integer.toString(data.id_kata)});

        return true;
    }
    public boolean updateJumData (SQLiteDatabase db, int data,String kata)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("jumlah_digunakan", data);

        db.update(TABLE_NAME, contentValues, COLUMNS[1] + " = ? ", new String[]{kata} );

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

    public ArrayList<CKata> getAllData(SQLiteDatabase db)
    {
        ArrayList<CKata> array_list = new ArrayList<CKata>();
        //hp = new HashMap();
        Cursor cursor =  db.rawQuery( "select * from "+TABLE_NAME +"",null  );
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){

            CKata data = new CKata();
            data.id_kata = cursor.getInt(0);
            data.kata = cursor.getString(1);
            data.jumlah = cursor.getInt(2);
            data.tipe= cursor.getString(3);
            array_list.add(data);
            cursor.moveToNext();

        }
        if(cursor != null)
            cursor.close();
        return array_list;
    }
    public String[] getAllKata(SQLiteDatabase db)
    {
        String[] array_list = new String[getNumberDB(db)];
        //hp = new HashMap();
        Cursor cursor =  db.rawQuery( "select * from "+TABLE_NAME +"",null  );
        cursor.moveToFirst();
        int i=0;
        while(cursor.isAfterLast() == false){
            String kata = cursor.getString(1);
            array_list[i]=kata;
            cursor.moveToNext();
            i++;
        }
        if(cursor != null)
            cursor.close();
        return array_list;
    }


    public ArrayList<String> getAllKataHistory1(SQLiteDatabase db)
    {
        ArrayList<String> array_list = new ArrayList<String>();
        ArrayList<Integer> array_list_lev_temp = new ArrayList<Integer>();
        ArrayList<Integer> array_list_lev = new ArrayList<Integer>();
        ArrayList<String> array_list_temp = new ArrayList<String>();
        Cursor cursor =  db.rawQuery( "select DISTINCT * from ( select id_kata,kata,jumlah_digunakan from kata union select id_kata, kata,jumlah_digunakan from history ) a  order by jumlah_digunakan desc",null  );
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            String kata = cursor.getString(1);
            array_list.add(kata);
            cursor.moveToNext();
        }

        if(cursor != null)
            cursor.close();

        return array_list;

    }

    /*private void quickSort(int lowerIndex, int higherIndex) {

        int i = lowerIndex;
        int j = higherIndex;
        int pivot = tempint[lowerIndex+(higherIndex-lowerIndex)/2];
        int temp;
        while (i <= j) {
            while (tempint[i] < pivot) {
                i++;
            }
            while (tempint[j] > pivot) {
                j--;
            }
            if(tempint[i] == pivot || tempint[j] == pivot || tempint[i] == tempint[j])
            {
                if(jum.get(meme[i])>jum.get(meme[j]) || jum.get(meme[i]) > jum.get(meme[lowerIndex+(higherIndex-lowerIndex)/2]))
                {
                    temp = tempint[i];
                    tempint[i] = tempint[j];
                    tempint[j] = temp;
                }
                continue;
            }
            if (i <= j) {
                temp = tempint[i];
                tempint[i] = tempint[j];
                tempint[j] = temp;

                temp=meme[i];
                meme[i]=meme[j];
                meme[j]=temp;

                i++;
                j--;
            }
        }
        if (lowerIndex < j)
            quickSort(lowerIndex, j);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }*/

    //private static int[] tempint;
    //private static int[] meme;
    //private static ArrayList<Integer> jum;
    public ArrayList<String> getAllKataHistory(SQLiteDatabase db,String s)
    {
        ArrayList<String> array_list = new ArrayList<String>();
        ArrayList<Integer> array_list_lev_temp = new ArrayList<Integer>();
        ArrayList<Integer> array_list_lev = new ArrayList<Integer>();
        ArrayList<String> array_list_temp = new ArrayList<String>();
        ArrayList<Integer> jum=new ArrayList<Integer>();
        Cursor cursor =  db.rawQuery( "select DISTINCT * from ( select id_kata,kata,jumlah_digunakan from kata union select id_kata, kata,jumlah_digunakan from history ) a  where a.kata like '"+s+"%' order by jumlah_digunakan desc limit 0,100",null  );
        cursor.moveToFirst();
        int i=0;
        while(cursor.isAfterLast() == false){
            String kata = cursor.getString(1);
            int jj=cursor.getInt(2);
            array_list.add(kata);
            jum.add(jj);
            cursor.moveToNext();
            i++;
        }
        int me;
        int []meme=new int[i];
        int []tempint=new int[i];
        for(int j=0;j<i;j++)
        {
            me=LevenshteinDistance(array_list.get(j),s);
            array_list_lev_temp.add(me);
            //ini isinya distance
            tempint[j]=me;
            meme[j]=j;

        }
        //urutin
        String [] temp=new String[i];
        int y;
        for(int j=0; j<i; j++)
        {
            for(int k=j; k>0; k--)
            {
                if(tempint[k] < tempint[k-1])
                {
                    y = tempint[k];
                    tempint[k] = tempint[k-1];
                    tempint[k-1] = y;

                    y = meme[k];
                    meme[k] = meme[k-1];
                    meme[k-1] = y;
                }
                else if(tempint[k] == tempint[k-1])
                {
                    if(jum.get(meme[k])>jum.get(meme[k-1]))
                    {
                        y = tempint[k];
                        tempint[k] = tempint[k-1];
                        tempint[k-1] = y;

                        y = meme[k];
                        meme[k] = meme[k-1];
                        meme[k-1] = y;
                    }
                }
            }
        }
        for(int j=0; j<i; j++)
        {
            Log.i(String.valueOf(j),
                    String.valueOf(tempint[j]));
        }
        //quickSort(0, tempint.length-1);
        /*int y;
        for(int j=0;j<i;j++)
        {
            for(int k=0;k<i-1;k++)
            {
                if(tempint[j]<tempint[k])
                {
                    y=tempint[j];
                    tempint[j]=tempint[k];
                    tempint[k]=y;

                    y=meme[j];
                    meme[j]=meme[k];
                    meme[k]=y;
                }
                else if(tempint[j]==tempint[k])
                {
                    if(jum.get(meme[j])>jum.get(meme[k])) //ini wajib ya? klo kembar aku mesti cek ini? bntr ko aq lp ini buat opo
                    //oh ini buat klo distace e sm, diliat jumlah e klo jumlah e sg stue lebi akeh, diutuker posisine
                    {
                        y=tempint[j];
                        tempint[j]=tempint[k];
                        tempint[k]=y;

                        y=meme[j];
                        meme[j]=meme[k];
                        meme[k]=y;
                    }
                }
            }

        }*/
        for(int j=0;j<i;j++)
        {
            array_list_temp.add(array_list.get(meme[j]));
     //       Log.i("myLog", array_list_temp.get(j).toString() + "weight:" + tempint[j]);
        }

        if(cursor != null)
            cursor.close();
        return array_list_temp;
    }
    public ArrayList<String> CekLev(ArrayList<String> array_list,String s)
    {
        ArrayList<Integer> array_list_lev_temp = new ArrayList<Integer>();
        ArrayList<Integer> array_list_lev = new ArrayList<Integer>();
        ArrayList<String> array_list_temp = new ArrayList<String>();
        int me;
        int [] meme=new int[array_list.size()];
        int [] tempint=new int[array_list.size()];
        for(int j=0;j<array_list.size();j++)
        {
            me=LevenshteinDistance(array_list.get(j),s);
            array_list_lev_temp.add(me);
            tempint[j]=me;
            meme[j]=j;

        }
        //urutin
        String [] temp=new String[array_list.size()];
        int y;
        for(int j=0;j<array_list.size();j++)
        {
            for(int k=0;k<array_list.size()-1;k++)
            {
                if(tempint[j]<tempint[k])
                {
                    y=tempint[j];
                    tempint[j]=tempint[k];
                    tempint[k]=y;

                    y=meme[j];
                    meme[j]=meme[k];
                    meme[k]=y;
                }
            }

        }
        for(int j=0;j<array_list.size();j++)
        {
            array_list_temp.add(array_list.get(meme[j]));
       //     Log.i("myLog", array_list_temp.get(j).toString() + "weight:" + tempint[j]);
        }
        return array_list_temp;
    }
    public int LevenshteinDistance (String s, String t) {
        int panjangS = s.length() + 1;
        int panjangT = t.length() + 1;
        int[] weig = new int[panjangS];
        int[] weigbaru = new int[panjangS];
        for (int i = 0; i < panjangS; i++)
        {
            weig[i] = i;
        }
        for (int j = 1; j < panjangT; j++) {
            weigbaru[0] = j;
            for(int i = 1; i < panjangS; i++) {
                int match = (s.charAt(i - 1) == t.charAt(j - 1)) ? 0 : 1;
                int cost_replace = weig[i - 1] + match;
                int cost_insert  = weig[i] + 1;
                int cost_delete  = weigbaru[i - 1] + 1;
                weigbaru[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }
            int[] swap = weig; weig = weigbaru; weigbaru = swap;
        }
        return weig[panjangS - 1];
    }
}