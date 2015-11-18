package com.febriaroosita.swt;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;
import com.melnykov.fab.FloatingActionButton;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.filepicker.Filepicker;

public class Landingpage extends ActionBarActivity{
    int FILE_CODE=1;

    int Position=0;
    ArrayAdapter documentlistAdapter;
    private static final int REQUEST_CODE = 6384;
    private static final int CREATE_REQUEST_CODE = 40;
    private static final int OPEN_REQUEST_CODE = 6034;
    private static final int SAVE_REQUEST_CODE = 42;
    String allText="";
    //database
    DbFile Fileku;
    DbKata Kataku;
    String par;
    ArrayList<CFile> dataFileku;
    SQLiteDatabase db;
    SQLiteDatabase dbkata;
    ArrayList<String> judul;
    ArrayList<String> path;
    private MenuItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_data_local);
        Filepicker.setKey("AFdni1Rr1Ruu50OrQbf6lz");
        Fileku = new DbFile(this);
        Kataku = new DbKata(this);
        db =Fileku.getWritableDatabase();
        dbkata=Kataku.getWritableDatabase();
        Kataku.create(dbkata);
        Fileku.create(db);
//        if(Kataku.getNumberDB(dbkata)==0)
//        {
//            DownloadDataKata();
//        }
        ListView listDocument = (ListView) findViewById(R.id.listViewResults);
        //cek ada filenya gak?
        if(Fileku.cektabelexist(db))
        {
            int jumdata=Fileku.getJumData(db);
            if(jumdata!=0)
            {
                judul=Fileku.getAllJudul(db);
                path=Fileku.getAllPath(db);
                List<String> weekForecast = new ArrayList<String>(judul);
                documentlistAdapter = new ArrayAdapter<String>(this, R.layout.listdocument_item, R.id.listdocument_item_judul, weekForecast);
                listDocument.setAdapter(documentlistAdapter);
                TextView id_none=(TextView) findViewById(R.id.id_none);
                id_none.setVisibility(View.GONE);
            }
            else {
                TextView id_none=(TextView) findViewById(R.id.id_none);
                id_none.setVisibility(View.VISIBLE);
                listDocument.setVisibility(View.GONE);
            }
        }
        try{
            ((ArrayAdapter) listDocument.getAdapter()).notifyDataSetChanged();
        }
        catch (NullPointerException e)
        {

        }
        //tombol dokumen baru
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(listDocument);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(Landingpage.this);
                dialog.setContentView(R.layout.popupnamefile);
                dialog.setTitle("Masukkan Nama File:");
                final EditText text = (EditText) dialog.findViewById(R.id.id_filename);
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mystring;
                        mystring = text.getText().toString();
                        mystring.replaceAll("\\s+", "");
                        Toast.makeText(Landingpage.this, mystring, Toast.LENGTH_LONG).show();
                        if (!mystring.equals("")) {
                            dialog.dismiss();
                            Intent abc = new Intent(Landingpage.this, MainActivity.class);
                            abc.putExtra("status", "new_file");
                            abc.putExtra("namefile", text.getText() + ".doc");
                            startActivity(abc);
                            finish();
                        }
                    }
                });
                dialog.show();
            }
        });
        setTitle("iEditor");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        listDocument.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Position = position;
                final String mSelectedItems;  // Where we track the selected items
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                // Set the dialog title

                builder.setTitle("Apakah anda ingin dokumen anda dilakukan pengecekan?")
                        // Specify the list array, the items to be selected by default (null for none),
                        // and the listener through which to receive callbacks when items are selected

                                // Set the action buttons
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent ii = new Intent(Landingpage.this, MainActivity.class);
                                ii.putExtra("FilePath", path.get(Position));
                                ii.putExtra("status", "open");
                                startActivity(ii);
                                finish();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent ii = new Intent(Landingpage.this, tanpaCheckActivity.class);
                                ii.putExtra("FilePath", path.get(Position));
                                ii.putExtra("status", "open");
                                startActivity(ii);
                                finish();
                            }
                        });
                builder.show();

            }
        });
    }
    public void DownloadDataKata() {
        Log.i("makan","saya makan");
        DownloadKataTask task = new DownloadKataTask(Landingpage.this);
        //cari insert ke db
        String url = "openKata.json";
        Log.i("makan","saya makan");
        task.execute(url);
        Log.i("makan", "saya makan1");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        final Uri uri = data.getData();
                        try {
                            readFileContent(uri);
                            final String path = FileUtils.getPath(this, uri);
                        } catch (Exception e) {
                            Log.e("FileSelect", "File select error", e);
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
        public void openFile(View view)
        {
            Intent getContentIntent = FileUtils.createGetContentIntent();
            Intent intent = Intent.createChooser(getContentIntent, "Select a file");
            try {
                startActivityForResult(intent, REQUEST_CODE);
            } catch (ActivityNotFoundException e) {
                // The reason for the existence of aFileChooser
            }
        }
    private void readFileContent(Uri uri) throws IOException {
        File dir=null;File dir1=null;
        String uriku=uri.toString();
        final Intent ii = new Intent(this, MainActivity.class);
        if(uriku.contains("com.google.android.apps.docs.storage")) {
            Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();
            grantUriPermission(getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            ParcelFileDescriptor pfd =
                    this.getContentResolver().
                            openFileDescriptor(uri, "r");
            pfd.getFileDescriptor().toString();
            Log.i("pfd","lalala"+pfd.getFileDescriptor().toString());
            Toast.makeText(this, pfd.toString(), Toast.LENGTH_LONG).show();
            FileInputStream fileOutputStream =
                    new FileInputStream(pfd.getFileDescriptor());
            InputStream inputStream =
                    getContentResolver().openInputStream(uri);
            final String title = uri.toString();

            try {
                final HWPFDocument a = new HWPFDocument(fileOutputStream);
                Paragraph hwPar;
                org.apache.poi.hwpf.usermodel.Range range = a.getRange();
                Paragraph paragraph = range.getParagraph(0);
                allText = a.getText().toString();
                String par = allText;
                int firstChar = 0;
                int lastChar = -1;
                boolean ketemu = true;
                final Dialog dialog = new Dialog(Landingpage.this);
                dialog.setContentView(R.layout.popupnamefile);
                dialog.setTitle("Masukkan Nama File:");
                final EditText text = (EditText) dialog.findViewById(R.id.id_filename);
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mystring;
                        mystring = text.getText().toString();
                        mystring.replaceAll("\\s+", "");
                        Toast.makeText(Landingpage.this, mystring, Toast.LENGTH_LONG).show();
                        if (!mystring.equals("")) {
                            dialog.dismiss();
                            Intent abc = new Intent(Landingpage.this, MainActivity.class);
                            File sdCard = Environment.getExternalStorageDirectory();
                            File dir11 = new File(sdCard.getAbsolutePath() + "/" + "ieditor");
                            File dir;
                            //cek apakah foldernya ada?
                            if (!dir11.exists()) {
                            } else {
                                if (!dir11.mkdirs()) {
                                    //gagal
                                } else {
                                }
                            }
                            abc.putExtra("status", "drive");
                            abc.putExtra("namefile", text.getText() + ".doc");
                            String aaaa = text.getText().toString() + ".doc";
                            abc.putExtra("isiDoc", allText);
                            abc.putExtra("FilePath", "/storage/emulated/0/ieditor/" + aaaa);
                            if (Fileku.cektabelexist(db)) {
                                if (!Fileku.getFileName(db, "/storage/emulated/0/ieditor/" + aaaa)) {
                                    DateFormat dateFormatter = new SimpleDateFormat("MMM dd hh.mm");
                                    dateFormatter.setLenient(false);
                                    Date today = new Date();
                                    String s = dateFormatter.format(today);
                                    CFile myFile = new CFile();
                                    int jum = Fileku.getJumData(db) + 1;
                                    myFile.id_file = jum;
                                    myFile.nama_file = aaaa.toString();
                                    myFile.pathku = "/storage/emulated/0/ieditor/" + aaaa;
                                    myFile.tanggal_create = s;
                                    myFile.tanggal_update = s;
                                    Fileku.insertData(db, myFile);
                                    startActivity(abc);
                                    finish();
                                } else {
                                    startActivity(abc);
                                    finish();
                                }
                            }
                        }
                    }


                });
                dialog.show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "erornyas:" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            inputStream.close();
        }else if(uriku.contains("com.ianhanniballake.localstorage.documents1/document/"))
        {
            Log.i("ianhanni",uriku);
            final String lengkap = FileUtils.getPath(this, uri);
            uriku=uriku.replace("%2F","/");
            final String[] lengkap1 = uriku.split("content://com.ianhanniballake.localstorage.documents1/document/");
            String[] temp = uriku.split("content://com.ianhanniballake.localstorage.documents1/document//storage/emulated/0/");
            ii.putExtra("FilePath", lengkap1[1]);
            ii.putExtra("status", "open");
            if (Fileku.cektabelexist(db))
            {
                if (!Fileku.getFileName(db, temp[1])) {
                    DateFormat dateFormatter = new SimpleDateFormat("MMM dd hh.mm");
                    dateFormatter.setLenient(false);
                    Date today = new Date();
                    String s = dateFormatter.format(today);
                    CFile myFile = new CFile();
                    int jum = Fileku.getJumData(db) + 1;
                    myFile.id_file = jum;
                    myFile.nama_file = temp[1].toString();
                    myFile.pathku = lengkap1[1];
                    myFile.tanggal_create = s;
                    myFile.tanggal_update = s;
                    Fileku.insertData(db, myFile);
                    startActivity(ii);
                    finish();
                } else {
                    startActivity(ii);
                    finish();
                }
            }
        }
        else
        { //untuk openfile
            final String lengkap = FileUtils.getPath(this, uri);
            String[] temp = lengkap.split("/storage/emulated/0/");
            ii.putExtra("FilePath", lengkap);
            ii.putExtra("status", "open");
                if (Fileku.cektabelexist(db)) {
                    if (!Fileku.getFileName(db, temp[1])) {
                        DateFormat dateFormatter = new SimpleDateFormat("MMM dd hh.mm");
                        dateFormatter.setLenient(false);
                        Date today = new Date();
                        String s = dateFormatter.format(today);
                        CFile myFile = new CFile();
                        int jum = Fileku.getJumData(db) + 1;
                        myFile.id_file = jum;
                        myFile.nama_file = temp[1].toString();
                        myFile.pathku = lengkap;
                        myFile.tanggal_create = s;
                        myFile.tanggal_update = s;
                        Fileku.insertData(db, myFile);
                        startActivity(ii);
                        finish();
                    } else {
                        startActivity(ii);
                        finish();
                    }

                }

            }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void exitByBackKey() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Keluar dari iEditor");
        alertDialog.setMessage("Apakah anda yakin akan keluar dari aplikasi?");
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Terima Kasih", Toast.LENGTH_SHORT).show();
                finish();
                System.exit(0);
            }
        });
        alertDialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_open_data_local, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.item = item;
        int id = item.getItemId();
        if (id == R.id.action_buka) {
            openFile(getCurrentFocus());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
