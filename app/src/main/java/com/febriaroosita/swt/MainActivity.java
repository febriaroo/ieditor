package com.febriaroosita.swt;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.StringTokenizer;

import static android.inputmethodservice.KeyboardView.OnKeyListener;


public class MainActivity extends ActionBarActivity {
    DbKata kata;
    ArrayList<CKata> myKata;
    private EditText ma;
    String filePath;
    String new_file;
    String check;
    //database
    DbFile Fileku;
    SQLiteDatabase db1;
    ArrayList<String> judul;
    ArrayList<String> path;

    SessionManagement session;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;


    //rekomendasi
    ArrayList<String> kaataku;
    DbKata kataku;
    DbHistory history;
    String par;
    ArrayList<CFile> dataFileku;
    SQLiteDatabase db;
    SQLiteDatabase dbHis;
    ArrayList<String> kaata;
    MultiAutoCompleteTextView myText;
    ArrayAdapter<String> adapter;
    int start1,before1,count1;
    public LinkedList<Integer> posisi;
    public LinkedList<String> ListWords;
    String all;

    private static final int REQUEST_CODE = 6384;
    private static final int REQUEST_CHOOSER =123 ;
    private static EditText textView;

    private static final int CREATE_REQUEST_CODE = 40;
    private static final int OPEN_REQUEST_CODE = 41;
    private static final int SAVE_REQUEST_CODE = 42;
    @Override
    public boolean onKeyUp(int keyCode,@NonNull KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_TAB:
            case KeyEvent.KEYCODE_SPACE:
                String temp=all;
                //Toast.makeText(MainActivity.this,all+ " spansi>.<",Toast.LENGTH_LONG).show();
                ListWords.add(temp);

                for(int i=0;i<ListWords.size();i++)
                {
                    Log.i("ListWord", ListWords.get(i));

                }
                break;
            case KeyEvent.KEYCODE_DEL:
                // Toast.makeText(MainActivity.this,a+ "dihapus >.<",Toast.LENGTH_LONG).show();
                break;


        }
        return super.onKeyUp(keyCode, event);
    }
    @Override
    public boolean onKeyDown(int keyCode,@NonNull KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_TAB:
            case KeyEvent.KEYCODE_SPACE:
                String temp=all;
                //Toast.makeText(MainActivity.this,all+ " spansi>.<",Toast.LENGTH_LONG).show();
                ListWords.add(temp);
                for(int i=0;i<ListWords.size();i++)
                {
                    Log.i("ListWord", ListWords.get(i));

                }
                break;
            case KeyEvent.KEYCODE_BACK:
                exitByBackKey();
                break;

        }
        return super.onKeyDown(keyCode, event);
    }

    private final TextWatcher passwordWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        all=s.toString();
        //Toast.makeText(MainActivity.this,all,Toast.LENGTH_SHORT).show();
        }
        public void onTextChanged(CharSequence s, int start, int before, int count) {
           //terminateToken(s);
            String [] l=s.toString().split(" ");
            String kirim=l[l.length-1];
       if(posisi.size()!=0) {
           kaataku=kataku.getAllKataHistory(db,kirim);
           kaataku=kataku.CekLev(kaataku,kirim);
           adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,kaataku);
           myText.setAdapter(adapter);
       }
        }
        public void afterTextChanged(Editable s) {


            if(s.length()>1)
            {
                if (s.length() == 0) {
                    kaataku=kataku.getAllKataHistory(db,"");
                } else{
                    Log.i("start", "start: " + start1);
                    Log.i("before", "before: " + before1);
                    Log.i("count", "count: " + count1);
                    Log.i("length", "lenght: " + s.length());

                    // Toast.makeText(TestingActivity.this, s.toString(),Toast.LENGTH_LONG).show();
                    //myText.setText("You have entered : " + myText.getText());
                }
                if(!all.equals("")) {
                    if(s.length()>2) {
                        if (s.charAt(s.length() - 2) == ' ') {
                            String[] splitku = s.toString().split(" ");
                            //Log.i("hasilku",splitku[splitku.length-1]);

                            String kata = splitku[splitku.length - 1];
                            kaataku = kataku.getAllKataHistory(db, kata);
                            adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, kaataku);
                            myText.setAdapter(adapter);
                            ((ArrayAdapter) myText.getAdapter()).notifyDataSetChanged();

                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                    }
                        if (s.charAt(s.length()-1)==' ') {
                            //Toast.makeText(MainActivity.this,all+ " spansi>.<",Toast.LENGTH_LONG).show();
                            if(posisi.size()==0)
                            {
                                ListWords.add("-1");
                                ListWords.add("");
                                posisi.add(-1);
                                posisi.add(0);
                            }
                            String []splitku=s.toString().split(" ");
                            //Log.i("hasilku",splitku[splitku.length-1]);

                                    String kata=splitku[splitku.length - 1];
                                ListWords.add(splitku[splitku.length - 1]);
                                posisi.add(s.length() - 1);
                                    SpellingWords a=new SpellingWords(MainActivity.this,1,2);
                                   // myText = new SpannableString(kata);
                                    if(!cekKata(kata)) {
                                        if (!a.stemming(kata)) {
                                            int ii = s.length() - 1;
                                            s.setSpan(new UnderlineSpan(), ii-kata.length(), s.length() - 1, 0);
                                       //     Toast.makeText(MainActivity.this,"gagal sukses",Toast.LENGTH_SHORT).show();

                                        }
                                        history = new DbHistory(MainActivity.this);

                                        dbHis =history.getWritableDatabase();
                                        int jum=history.getJumDB(dbHis);
                                        CHistory data = new CHistory();
                                        data.id_kata=jum;
                                        data.kata=kata;
                                        data.tanggal="";
                                        data.weight=1.0;
                                        Log.i("baru", kata);
                                        /*if(history.getDataByKata(dbHis,kata))
                                        {
                                            data.jumlah_digunakan=history.getJumKata(dbHis,kata)+1;
                                            history.updateData(dbHis,data);
                                            ListWords.add(kata);
                                        }
                                        else
                                        {
                                            data.jumlah_digunakan=1;
                                            history.insertData(dbHis,data);
                                        }
                                    */
                                    }
                                    else
                                    {
                                        int jum_kata=kataku.getJumKata(db,kata);
                                        jum_kata++;
                                        kataku.updateJumData(db, jum_kata, kata);
                                        kaataku=kataku.getAllKataHistory(db, all);
                                        adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,kaataku);
                                        myText.setAdapter(adapter);
                                        ((ArrayAdapter) myText.getAdapter()).notifyDataSetChanged();

                                    }


                                    all = "";
                                 //   Toast.makeText(MainActivity.this, all, Toast.LENGTH_LONG).show();
                            ((ArrayAdapter) myText.getAdapter()).notifyDataSetChanged();


                        }

                    //}
                }

            }
           /* */
        }
    };

    public boolean cekKata(String kataku1){

        boolean kataAda = kataku.getDataByKata(db, kataku1);
        return kataAda;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session= new SessionManagement(getApplicationContext());
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        ListWords=new LinkedList<String>();
        posisi=new LinkedList<Integer>();
        //punya rekomendasi
        history = new DbHistory(MainActivity.this);

        dbHis =history.getWritableDatabase();
        history.createtable(dbHis);
        kataku = new DbKata(this);
        db =kataku.getWritableDatabase();
        kaataku = kataku.getAllKataHistory1(db);
        ListView listDocument = (ListView) findViewById(R.id.listViewResults);
        adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,kaataku);
        myText=(MultiAutoCompleteTextView)findViewById(R.id.ku);
        myText.setThreshold(1);
        myText.setDropDownBackgroundResource(R.color.white);
        myText.setAdapter(adapter);
        myText.setTokenizer(new SpaceTokenizer());
        StringTokenizer myToken=new StringTokenizer(myText.toString()," ");


        myText.addTextChangedListener(passwordWatcher);
        ((ArrayAdapter) myText.getAdapter()).notifyDataSetChanged();
        myText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int key, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && key == KeyEvent.KEYCODE_ENTER) {
                    //operation that you want on key press
                    //   Toast.makeText(MainActivity.this,"yesa",Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        ((ArrayAdapter) myText.getAdapter()).notifyDataSetChanged();
//        if(session.isLoggedIn())
//        {
//            EditText myText1=(EditText)findViewById(R.id.machine);
//            myText.setVisibility(View.GONE);
//            myText1.setVisibility(View.VISIBLE);
//        }

        /*my Text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override


            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,

                                    long arg3) {
                //nambahi jumlah digunakan
                int jum_kata = kataku.getJumKata(db, arg0.getItemAtPosition(arg2).toString());
                Log.i("kata", arg0.getItemAtPosition(arg2).toString());
                Log.i("jumlahkata", "jumlah kata:" + jum_kata);
                jum_kata++;
                kataku.updateJumData(db, jum_kata, arg0.getItemAtPosition(arg2).toString());


            }


        });
*/
        //end punya rekomendasi
        Fileku = new DbFile(MainActivity.this);

        db1 =Fileku.getWritableDatabase();

        Intent apa=getIntent();
        try {
            String mimi = apa.getStringExtra("FilePath");
            if (!mimi.contains("com.google.android.apps.docs.storage"))
            {
                filePath = apa.getStringExtra("FilePath");
                check = apa.getStringExtra("status");
                Log.i("filepath", filePath);
                try {
                    if (!filePath.isEmpty() && check.equals("open")) {
                      //  Toast.makeText(MainActivity.this, filePath, Toast.LENGTH_LONG).show();

                        //  Log.i("masuk","ini masuk");
                        String kyaa = "Dokumen Baru";
                        String[] paat = filePath.split("/storage/emulated/0/");
                        if (paat.length >= 1) {
                            kyaa = paat[1];
                        }
                        setTitle(kyaa);
                        CheckingFile(filePath);
                    }
                    else if (!check.isEmpty() && check.equals("drive")) {
                        new_file = apa.getStringExtra("namefile");
                        filePath = "/storage/emulated/0/ieditor/" + new_file;
                        setNew_file(filePath);
                        setTitle(new_file);

                        String isi = apa.getStringExtra("isiDoc");
                        myText.setText(isi);
                        CheckingFiledrive(isi);
                        //  setTitle(new_file);
                    }
                } catch (NullPointerException e) {
                    check = apa.getStringExtra("status");

                    try {
                        if (!check.isEmpty() && check.equals("new_file")) {
                            new_file = apa.getStringExtra("namefile");
                            filePath = "/storage/emulated/0/ieditor/" + new_file;
                            setNew_file(new_file);
                            setTitle(new_file);
                            //  setTitle(new_file);
                        } else if (!check.isEmpty() && check.equals("drive")) {
                            new_file = apa.getStringExtra("namefile");
                            filePath = "/storage/emulated/0/ieditor/" + new_file;
                            setNew_file(filePath);
                            setTitle(new_file);

                            String isi = apa.getStringExtra("isiDoc");
                            myText.setText(isi);
                            CheckingFiledrive(isi);
                            //  setTitle(new_file);
                        }
                    } catch (NullPointerException e1) {
                        new_file = apa.getStringExtra("namefile");
                        String isi = apa.getStringExtra("isiDoc");
                        filePath = "/storage/emulated/0/ieditor/" + new_file;
                        setNew_file(new_file);
                        setTitle(new_file);
                        myText.setText(isi);
                    }


                }
            }
            else {
                check = apa.getStringExtra("status");

                if (!check.isEmpty() && check.equals("drive")) {
                    new_file = apa.getStringExtra("namefile");

                    filePath = "/storage/emulated/0/ieditor/" + new_file;
                    setNew_file(new_file);
                    setTitle(new_file);

                    String isi = apa.getStringExtra("isiDoc");
                    myText.setText(isi);
                    CheckingFiledrive(isi);
                    //  setTitle(new_file);
                }
            }
            //getActionBar().setIcon(R.drawable.my_icon);
        }
        catch (NullPointerException e)
        {
            try {
                check = apa.getStringExtra("status");

                if (!check.isEmpty() && check.equals("new_file")) {
                    new_file = apa.getStringExtra("namefile");
                    filePath = "/storage/emulated/0/ieditor/" + new_file;
                    setNew_file(new_file);
                    setTitle(new_file);
                    //  setTitle(new_file);
                } else if (!check.isEmpty() && check.equals("drive")) {
                    new_file = apa.getStringExtra("namefile");
                    filePath = "/storage/emulated/0/ieditor/" + new_file;
                    setNew_file(new_file);
                    setTitle(new_file);

                    String isi = apa.getStringExtra("isiDoc");
                    myText.setText(isi);
                    CheckingFiledrive(isi);
                    //  setTitle(new_file);
                }
            } catch (NullPointerException e1) {
                check = apa.getStringExtra("status");

                new_file = apa.getStringExtra("namefile");
                String isi = apa.getStringExtra("isiDoc");
                filePath = "/storage/emulated/0/ieditor/" + new_file;
                setNew_file(new_file);
                setTitle(new_file);
                myText.setText(isi);
            }
        }

    }
    public void setNew_file(String FileName)
    {

        File sdCard = Environment.getExternalStorageDirectory();
        File dir1 = new File(sdCard.getAbsolutePath() + "/ieditor/ee.doc");
        //File dir1 = new File(sdCard.getAbsolutePath() + "/ieditor/eekk.doc");
       // File dir1 = new File(sdCard.getAbsolutePath()+"/" + "ieditor/"+FileName);
        File dir11 = new File(sdCard.getAbsolutePath() +"/" + "ieditor");
        File dir;
        if(!dir11.exists())
        {
            dir = new File(sdCard.getAbsolutePath() +"/" + "ieditor/"+FileName);
        }
        else {
            if(!dir11.mkdirs())
            {
                //gagal
                dir = new File(sdCard.getAbsolutePath() +"/" + FileName);
            }
            else
            {
                dir = new File(sdCard.getAbsolutePath() +"/" + "ieditor/"+FileName);
            }
        }


        FileInputStream aa=null;
        try {
            aa = new FileInputStream(dir1);
            HWPFDocument a = new HWPFDocument(aa);

            Paragraph hwPar;
            org.apache.poi.hwpf.usermodel.Range range = a.getRange();

            Paragraph paragraph = range.getParagraph(0);
            EditText mytext=(EditText) findViewById(R.id.ku);
            CharacterRun charRun = paragraph.insertBefore(mytext.getText().toString());
            OutputStream out = new FileOutputStream(dir);
            a.write(out);
            out.close();

            String temp[]=filePath.split("/storage/emulated/0/");
            if(Fileku.cektabelexist(db1)) {
                if (!Fileku.getFileName(db1, temp[1])) {
                    DateFormat dateFormatter = new SimpleDateFormat("MMM dd hh.mm");
                    dateFormatter.setLenient(false);
                    Date today = new Date();
                    String s = dateFormatter.format(today);
                    CFile myFile = new CFile();
                    int jum=Fileku.getJumData(db1)+1;
                    myFile.id_file=jum;
                    myFile.nama_file = temp[1].toString();
                    myFile.pathku = filePath;
                    myFile.tanggal_create = s;
                    myFile.tanggal_update = s;

                    Fileku.insertData(db1, myFile);
                    Toast.makeText(this,"File sukses dibuat",Toast.LENGTH_LONG).show();

                }
        }
    } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CheckingFile(String url) {
        SpellingCheckerTask task = new SpellingCheckerTask(MainActivity.this);
       // Log.i("urlnya", url);
        task.execute(url);

    }
    public void CheckingFiledrive(String all) {
        Spelingfordrive task = new Spelingfordrive(MainActivity.this);
        // Log.i("urlnya", url);
        task.execute(all);

    }


    public void exitByBackKey() {
        // Write your code here
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Konfirmasi keluar");

        // Setting Dialog Message
        alertDialog.setMessage("Apakah anda yakin akan keluar?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Ya dan Simpan", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                simpan();

                // Write your code here to invoke YES event
                //Toast.makeText(getApplicationContext(), "menyimpan dokumen anda...", Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Tidak Keluar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void simpan()
    {
        Log.i("filepath",filePath);
        String[] parts = filePath.split("/storage/emulated/0/");

        String mPart=filePath;
        if(parts.length>=1)
        {
           mPart=parts[1];
        }
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/ieditor/ee.doc");
        File dir11 = new File(sdCard+"/iEditor");
        File stu= new File(sdCard.getAbsolutePath()+"/" +mPart);
        if (dir11.exists()) {

        } else {
            if (!dir11.mkdirs()) {
                Log.i("mkdir", "MKDIR Gagal");
                //gagal
            } else {


                Log.i("mkdir", "MKDIR sukses");
            }
        }

        InputStream in = getResources().openRawResource(R.raw.ee);

        FileOutputStream out = null;

        try {
            out = new FileOutputStream(dir);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] buff = new byte[1024];
        int read = 0;

        try {
            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File dir1;
        //dir = new File(sdCard.getAbsolutePath() +"/" + mPart);
        dir1 = new File(sdCard.getAbsolutePath() +"/" + mPart);
        FileInputStream aa=null;
        try {
            aa = new FileInputStream(dir);
            HWPFDocument a = new HWPFDocument(aa);

            Paragraph hwPar;
            org.apache.poi.hwpf.usermodel.Range range = a.getRange();
            ;
            Paragraph paragraph = range.getParagraph(0);
            //Log.i("mytag", a.getText().toString());
            //aaa.createParagraph;
            EditText mytext=(EditText) findViewById(R.id.ku);
            CharacterRun charRun = paragraph.insertBefore(mytext.getText().toString());
            //charRun.setBold(true);
            //charRun.setFontSize(32);
            OutputStream out11 = new FileOutputStream(stu);
            a.write(out11);
            out.close();
            Toast.makeText(this,"File sukses disimpan",Toast.LENGTH_LONG).show();
            Intent ii = new Intent(this, Landingpage.class);
            startActivity(ii);
            finish();
        }
        catch (IOException e) {
            e.printStackTrace();
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
            Intent a=new Intent(MainActivity.this,settingActivity.class);

            a.putExtra("data",myText.getText().toString());
            startActivity(a);
            return true;
        }
        if(id == R.id.action_drive)
        {
            saveFile();
        }
        else if(id==R.id.action_save)
        {
                simpan();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void saveFile()
    {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/msword");
        startActivityForResult(intent, SAVE_REQUEST_CODE);
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

            String textContent =myText.getText().toString();
            fileOutputStream.write(textContent.getBytes());

            fileOutputStream.close();
            pfd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,"File tidak ketemu",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,"File tidak io",Toast.LENGTH_SHORT).show();
        }
    }

}
