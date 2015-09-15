package com.febriaroosita.swt;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;


public class testingdb extends ActionBarActivity {
    public GridView gridViewBudaya;
    public ArrayList<CKata> arrayListBudaya;
    public SQLiteDatabase db;
    public DbKata DBKata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testingdb);
        DBKata = new DbKata(testingdb.this);
        db = DBKata.getWritableDatabase();
        DownloadDataKata();
        String a=Integer.toString(DBKata.getNumberDB(db));
        String c=DBKata.getDataByKataku(db,"lupa");
        TextView kya=(TextView) findViewById(R.id.mymy);
        kya.setText(c.toString());
    }
    public void DownloadDataKata() {
        DownloadKataTask task = new DownloadKataTask(testingdb.this);
        String url = "openKata.json";
        Log.i("urlnya", url);
        task.execute(url);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_testingdb, menu);
        return true;
    }

    public void RefreshDataBudaya() {
        arrayListBudaya = DBKata.getAllData(db);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
