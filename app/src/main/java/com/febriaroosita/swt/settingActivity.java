package com.febriaroosita.swt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class settingActivity extends ActionBarActivity {

    private static final int REQUEST_CODE = 6384;
    private static final int REQUEST_CHOOSER =123 ;
    private static EditText textView;
    CheckBox aa,aa1;
    SessionManagement session;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private static final int CREATE_REQUEST_CODE = 40;
    private static final int OPEN_REQUEST_CODE = 41;
    private static final int SAVE_REQUEST_CODE = 42;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

         session= new SessionManagement(getApplicationContext());
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        aa=(CheckBox) findViewById(R.id.checkBox);
        aa1=(CheckBox) findViewById(R.id.checkBox2);
        aa.setChecked(session.isLoggedIn());
        aa1.setChecked(session.isLoggedIn1());

        Button mybutton=(Button) findViewById(R.id.button);
        mybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v);
            }
        });
    }
    public void save(View v)
    {

        SQLiteDatabase db1;

        DbHistory his = new DbHistory(settingActivity.this);

        db1 =his.getWritableDatabase();
        session.createLoginSession(aa.isChecked(),aa1.isChecked());
        if(aa1.isChecked())
        {
            his.deleteAllData(db1);
            Toast.makeText(getBaseContext(),"Data history berhasil dihapus",Toast.LENGTH_SHORT).show();
        }
        finish();
    }
    public void saveFile(View view)
    {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/msword");
        startActivityForResult(intent, SAVE_REQUEST_CODE);
    }
        public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
                 Uri currentUri = null;

        if (resultCode == Activity.RESULT_OK)
        {

            if (requestCode == CREATE_REQUEST_CODE)
            {
                if (resultData != null) {
                    textView.setText("");
                }
            } else if (requestCode == SAVE_REQUEST_CODE) {

                if (resultData != null) {
                    currentUri = resultData.getData();
                    writeFileContent(currentUri);
                }
            } else if (requestCode == OPEN_REQUEST_CODE)
            {


            }
        }
    }
    private void writeFileContent(Uri uri)
    {
        try{
            ParcelFileDescriptor pfd =
                    this.getContentResolver().
                            openFileDescriptor(uri, "w");

            FileOutputStream fileOutputStream =
                    new FileOutputStream(pfd.getFileDescriptor());


            Intent a=getIntent();

            String textContent =a.getStringExtra("data");
            fileOutputStream.write(textContent.getBytes());

            fileOutputStream.close();
            pfd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(settingActivity.this,"File tidak ketemu",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(settingActivity.this,"File tidak io",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
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
