package com.febriaroosita.swt;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends ActionBarActivity {

    final private String CLIENT_ID = "abc123.apps.googleusercontent.com";
    final private List<String> SCOPES = Arrays.asList(new String[]{
            "https://www.googleapis.com/auth/plus.login",
            "https://www.googleapis.com/auth/drive"
    });
   // private GoogleAccountCredential mCredential;

    private EditText mExchangeCodeEditText;
    private EditText mIdTokenEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
