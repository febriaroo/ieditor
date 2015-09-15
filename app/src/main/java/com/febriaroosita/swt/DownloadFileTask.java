package com.febriaroosita.swt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Febria on 5/4/2015.
 */
public class DownloadFileTask extends AsyncTask<String, Void, String>
{
    ProgressDialog pDialog;
    Activity ActiveActivity;


    public GridView gridViewBudaya;
    //public ArrayList<CKata> arrayListBudaya;
    // public CBudayaAdapter budayaAdapter;

    public SQLiteDatabase db;
    // public DbFile DBFile;
    public DownloadFileTask(Activity pass)
    {
        ActiveActivity=pass;

    }

    protected void onPreExecute() {
        // TODO Auto-generated method stub
        //create progress dialog
        pDialog = new ProgressDialog(ActiveActivity);
        pDialog.setCancelable(true);
        pDialog.setMessage("Mengambil Data dari Server...");
        pDialog.show();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... urls) {
        // TODO Auto-generated method stub
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(urls[0]);

        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                String message;
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }
                message = builder.toString();
                return message;
            } else {
                Log.e("failed", "1");
                return null;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.e("failed","2");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("failed", "3");
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        pDialog.dismiss();
        if(result != null)
        {

            Toast.makeText(ActiveActivity,result,Toast.LENGTH_LONG).show();
            EditText a=(EditText) ActiveActivity.findViewById(R.id.ku);
            a.setText(result);


        }
        else
        {
            Toast.makeText(ActiveActivity, "Gagal mengunduh data...", Toast.LENGTH_SHORT).show();
        }
        super.onPostExecute(result);
    }
}