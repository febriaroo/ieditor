package com.febriaroosita.swt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.widget.EditText;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by febria on 11/4/15.
 */

public class noSpellingCheck extends AsyncTask<String, Void, SpannableString>
{
    ProgressDialog pDialog;
    Activity ActiveActivity;
    String allText;

    DbKata kata;
    String par;
    ArrayList<CKata> myKata;
    private EditText ma;
    String filePath;
    Context pass;


    // public CBudayaAdapter budayaAdapter;
    DbHistory his;

    // public CBudayaAdapter budayaAdapter;

    public SQLiteDatabase db;
    public SQLiteDatabase db1;
    public DbKata DBKata;
    public noSpellingCheck (Activity pass)
    {
        ActiveActivity=pass;
        DBKata = new DbKata(pass);
        his=new DbHistory(pass);
        db = DBKata.getWritableDatabase();
        db1 = his.getWritableDatabase();
    }


    public boolean cekKata(String kataku){
        boolean kataAda = kata.getDataByKata(db, kataku);
        return kataAda;

    }



    protected void onPreExecute() {
        // TODO Auto-generated method stub
        //create progress dialog
        super.onPreExecute();
        pDialog = new ProgressDialog(ActiveActivity);
        pDialog.setMessage("Sedang dipersiapkan...");
        pDialog.show();

    }

    @Override
    protected SpannableString doInBackground(String... data) {
        // TODO Auto-generated method stub
        SpannableString myspan;
        String semuaDataPath= (data[0]);
        kata = new DbKata(ActiveActivity);
        db = kata.getWritableDatabase();
        myKata = kata.getAllData(db);

        String[] parts = semuaDataPath.split("/storage/emulated/0/");
        File sdCard = Environment.getExternalStorageDirectory();
        Log.i("testing",semuaDataPath);
        Log.i("testing",parts[0]);
        //File dir = new File(sdCard.getAbsolutePath() + "/ieditor/ee.doc");
        //File dir1 = new File(sdCard.getAbsolutePath() + "/ieditor/eekk.doc");
        File dir1 = new File(sdCard.getAbsolutePath()+"/" + parts[1]);
        File dir = new File(sdCard.getAbsolutePath() +"/" + parts[1]);
        SpannableString myText = null;
        FileInputStream aa=null;
        try {
            aa = new FileInputStream(dir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HWPFDocument a= null;
        try {
            a = new HWPFDocument(aa);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Paragraph hwPar;
        org.apache.poi.hwpf.usermodel.Range range = a.getRange();
        ;
        Paragraph paragraph = range.getParagraph(0);
        // Log.i("mytag", a.getText().toString());

        //ini diganti sementara
        allText=a.getText().toString();
        //allText="menyanyi";
        par=allText;
        //aaa.createParagraph;
        CharacterRun charRun = paragraph.insertBefore("");
        charRun.setBold(true);
        charRun.setFontSize(32);
        OutputStream out = null;
        try {
            out = new FileOutputStream(dir1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            a.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int firstChar = 0;
        int lastChar = -1;
        boolean ketemu = true;

        myText = new SpannableString(allText);
        //stemming(allText);
        return myText;
    }

    @Override
    protected void onPostExecute(SpannableString result) {
        // TODO Auto-generated method stub
        pDialog.dismiss();
        if(result != null) {
            //Inflater myInf=new
            EditText ma=(EditText)ActiveActivity.findViewById(R.id.ku);
            ma.setText(result);
        }
        super.onPostExecute(result);
    }
}