package com.febriaroosita.swt;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;


public class CheckDBKata extends ActionBarActivity {
    //database
    DbFile Fileku;
    DbKata Kataku;
    String par;
    ArrayList<CFile> dataFileku;
    SQLiteDatabase db;
    SQLiteDatabase dbkata;
    ArrayList<String> judul;
    ArrayList<String> path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_dbkata);
        Button mub=(Button) findViewById(R.id.nextLevel);
        Fileku = new DbFile(this);
        Kataku = new DbKata(this);
        db =Fileku.getWritableDatabase();
        dbkata=Kataku.getWritableDatabase();
        Kataku.create(dbkata);
        Fileku.create(db);
        if(Kataku.getNumberDB(dbkata)==0)
        {
            DownloadDataKata();
        }
        else {
            Intent i = new Intent(CheckDBKata.this, Landingpage.class);
            startActivity(i);
            finish();
        }
    }
    public void DownloadDataKata() {
        DownloadKataTask task = new DownloadKataTask(CheckDBKata.this);
        Log.i("makan0","saya makan0");
        String url = "openkata.json";
        Log.i("urlnya", url);
        task.execute(url);
        Log.i("makan1", "saya makan1");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_check_dbkata, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
