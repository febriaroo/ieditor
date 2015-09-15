package com.febriaroosita.swt;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import io.filepicker.Filepicker;
import io.filepicker.models.FPFile;


public class GoogleDriveOpenActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_drive_open);
        Filepicker.setKey("AFdni1Rr1Ruu50OrQbf6lz");
        Intent intent = new Intent(GoogleDriveOpenActivity.this, Filepicker.class);
       // String[] services = {"GOOGLE_DRIVE"};
        String[] services = {"FACEBOOK", "CAMERA", "GMAIL"};
        intent.putExtra("services", services);
        String[] mimetypes = {"application/msword"};
        intent.putExtra("mimetype", mimetypes);

        startActivityForResult(intent, Filepicker.REQUEST_CODE_GETFILE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_google_drive_open, menu);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Filepicker.REQUEST_CODE_GETFILE) {
            if(resultCode == RESULT_OK) {

                // Filepicker always returns array of FPFile objects
                ArrayList<FPFile> fpFiles = data.getParcelableArrayListExtra(Filepicker.FPFILES_EXTRA);

                // Option multiple was not set so only 1 object is expected
                FPFile file = fpFiles.get(0);

                Toast.makeText(GoogleDriveOpenActivity.this, file.getUrl() + "/convert?format=txt", Toast.LENGTH_LONG).show();
                //https://www.filepicker.io/api/file/aycLx36RSe2FwWkHbNUQ/convert?format=txt
                // Do something cool with the result
                String ku=file.getUrl()+"/convert?format=txt";
                DownloadFileTask my=new DownloadFileTask(this);
                my.execute(ku);

            } else {
                // Handle errors here
            }

        }
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
