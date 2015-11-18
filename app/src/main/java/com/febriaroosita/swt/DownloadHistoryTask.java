package com.febriaroosita.swt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Febria on 5/4/2015.
 */
public class DownloadHistoryTask extends AsyncTask<String, Void, String>
{
    ProgressDialog pDialog;
    Activity ActiveActivity;

    public GridView gridViewBudaya;
    public ArrayList<CKata> arrayListBudaya;
    // public CBudayaAdapter budayaAdapter;

    public SQLiteDatabase db;
    public DbHistory DBHistory;
    public DBtemp dbtem;
    public DownloadHistoryTask(Activity pass)
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
                    builder.append(line);
                }
                message = builder.toString();
                // Log.i("datakata",message);
                return message;
                //Log.v("Getter", "Your data: " + builder.toString()); //response data
            } else {
                Log.e("failed", "1");
                //Log.e("Getter", "Failed to download file");
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
        Log.i("hasilku",result);
        Log.i("hasilku", "masu maus mamama");
        if(result != null)
        {
            try {
                //baca hasil web service ke json (decode json)
                //Toast.makeText(testingdb.this,"masuk sini",Toast.LENGTH_LONG).show();
                JSONObject obj = new JSONObject(result);
                if(obj.getInt("status") == 1)
                {
                    if(DBHistory.cektabelexist(db))
                    {

                        Toast.makeText(ActiveActivity, "dbnya ada kok", Toast.LENGTH_SHORT).show();
                        Log.i("Database ada","Ada kok databasenya");
                        DBHistory.deleteAllData(db);
                    }
                    if(dbtem.cektabelexist(db))
                    {

                        Toast.makeText(ActiveActivity, "dbnya ada kok", Toast.LENGTH_SHORT).show();
                        Log.i("Database ada","Ada kok databasenya");
                        dbtem.deleteAllData(db);
                    }

                    JSONArray jsonbudaya = obj.getJSONArray("data");

                    for(int i=0; i<jsonbudaya.length(); i++)
                    {
                        JSONObject objbudaya = jsonbudaya.getJSONObject(i);
                        CHistory history = new CHistory();

                        //`id_kata`, `kata`, `weight`, `tanggal`, `Jumlah_digunakan`
                        history.id_kata = objbudaya.getInt("id_kata");
                        history.kata = objbudaya.getString("kata");
                        history.weight = objbudaya.getDouble("weight");
                        history.tanggal = objbudaya.getString("tanggal");
                        history.jumlah_digunakan = objbudaya.getInt("Jumlah_digunakan");
                        //arrayListBudaya.add(budaya);
                        DBHistory.insertData(db, history);
                    }
                    // Toast.makeText(testingdb.this,"selesai",Toast.LENGTH_LONG).show();
                    //RefreshDataBudaya();

                    //////////////////////////
                }
                else if(obj.getInt("status") == 0)
                {
                    Toast.makeText(ActiveActivity, "Gagal mengunduh data...", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        else
        {
            Toast.makeText(ActiveActivity, "Gagal mengunduh data...", Toast.LENGTH_SHORT).show();
        }
        super.onPostExecute(result);
    }
}
