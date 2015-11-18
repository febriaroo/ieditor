package com.febriaroosita.swt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Febria on 5/4/2015.
 */
public class DownloadKataTask extends AsyncTask<String, Integer, Boolean>
{
    ProgressDialog pDialog;
    Activity ActiveActivity;

    //lucene
    Directory directory;
    IndexWriter indexWriter;
    Analyzer analyzer;

    public GridView gridViewBudaya;
    public ArrayList<CKata> arrayListBudaya;
    // public CBudayaAdapter budayaAdapter;

    public SQLiteDatabase db;
    public DbKata DBKata;
    private NumberProgressBar bnp;
    public DownloadKataTask(Activity pass)
    {
        ActiveActivity=pass;
        DBKata = new DbKata(pass);
        db = DBKata.getWritableDatabase();
        bnp = (NumberProgressBar) ActiveActivity.findViewById(R.id.number1);
        Log.i("tes","masuk");
        bnp.setProgress(0);
    }
    protected void onProgressUpdate(Integer...a){
        bnp.setProgress(a[0]);
    }

    protected void onPreExecute() {
        // TODO Auto-generated method stub
        //create progress dialog

        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... urls) {
        // TODO Auto-generated method stub
//        StringBuilder builder = new StringBuilder();
//        HttpClient client = new DefaultHttpClient();
//        HttpGet httpGet = new HttpGet(urls[0]);
        Log.i("doinbackground","masuk background");
        String json = null;
        try {

            InputStream is = ActiveActivity.getResources().openRawResource(R.raw.openkata);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();
            directory = new RAMDirectory();

            analyzer= new StandardAnalyzer();
            indexWriter = null;
            json = new String(buffer, "UTF-8");
            String result=json;
            if(result != null)
            {
                try {
                    //baca hasil web service ke json (decode json)
                    //Toast.makeText(testingdb.this,"masuk sini",Toast.LENGTH_LONG).show();
                    JSONObject obj = new JSONObject(result);
                    if(obj.getInt("status") == 1)
                    {
                        Log.i("tes","masuk1");
                        if(DBKata.cektabelexist(db))
                        {
                            Log.i("tes", "masuk2");
                            //Toast.makeText(ActiveActivity, "dbnya ada kok", Toast.LENGTH_SHORT).show();
                            Log.i("Database ada","Ada kok databasenya");
                            DBKata.deleteAllData(db);
                        }
                        Log.i("tes",result);

                        JSONArray jsonbudaya = obj.getJSONArray("data");
                        String sql = "INSERT INTO kata (id_kata,kata,tipe, jumlah_digunakan) VALUES (?, ?, ?, ?)";
                        SQLiteStatement stmt = db.compileStatement(sql);
                        db.beginTransaction();
                        indexWriter = new IndexWriter(directory, analyzer,
                                true);
                        for(int i=0; i<jsonbudaya.length(); i++)
                        {
                            JSONObject objbudaya = jsonbudaya.getJSONObject(i);

                            Document doc = new Document();
                            doc.add(new Field("fieldname", objbudaya.getString("kata"), Field.Store.YES,
                                    Field.Index.TOKENIZED));
                            indexWriter.addDocument(doc);
                            /*CKata budaya = new CKata(); //wah kok ada aync task e.. dimana ne?
                            budaya.id_kata = objbudaya.getInt("id_kata");
                            budaya.kata = objbudaya.getString("kata");
                            budaya.tipe = objbudaya.getString("tipe");
                            budaya.jumlah = 0;
                            //arrayListBudaya.add(budaya);
                            DBKata.insertData(db, budaya);*/
                            stmt.clearBindings();
                            stmt.bindLong(1, objbudaya.getInt("id_kata"));
                            stmt.bindString(2, objbudaya.getString("kata"));
                            stmt.bindString(3, objbudaya.getString("tipe"));
                            stmt.bindLong(4, 0);
                            stmt.execute();
                            int p=i*100/jsonbudaya.length();
                            publishProgress(p);
                        }


                        //directory = new FSDirectory().openInput("");

                        indexWriter.optimize();
                        indexWriter.close();


                        db.setTransactionSuccessful();
                        db.endTransaction();
                        return true;
                    }
                    else if(obj.getInt("status") == 0)
                    {
                        return false;
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            else
            {
                Toast.makeText(ActiveActivity, "download data failed", Toast.LENGTH_SHORT).show();
            }


        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        // TODO Auto-generated method stub
       //Log.i("hasilku",result);
       // Log.i("hasilku", "masu maus mamama");
        if(result != null) {

            Toast.makeText(ActiveActivity, "download sukses", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ActiveActivity, Landingpage.class);
            ActiveActivity.startActivity(i);

            // Tutup activitynya
            ActiveActivity.finish();
        }
        else
        {
            Toast.makeText(ActiveActivity, "download data failed", Toast.LENGTH_SHORT).show();
        }

        super.onPostExecute(result);
    }
}