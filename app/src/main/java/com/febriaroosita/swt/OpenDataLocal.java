package com.febriaroosita.swt;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
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

import com.melnykov.fab.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.filepicker.Filepicker;


public class OpenDataLocal extends ActionBarActivity {

    int FILE_CODE=1;
    ArrayAdapter documentlistAdapter;


    private static final int CREATE_REQUEST_CODE = 40;
    private static final int OPEN_REQUEST_CODE = 41;
    private static final int SAVE_REQUEST_CODE = 42;


    //database
    DbFile Fileku;
    DbKata Kataku;
    String par;
    ArrayList<CFile> dataFileku;
    SQLiteDatabase db;
    SQLiteDatabase dbkata;
    ArrayList<String> judul;
    ArrayList<String> path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_data_local);
        Filepicker.setKey("AFdni1Rr1Ruu50OrQbf6lz");
        Fileku = new DbFile(OpenDataLocal.this);
        Kataku = new DbKata(OpenDataLocal.this);

        db =Fileku.getWritableDatabase();
        dbkata=Kataku.getWritableDatabase();
        Kataku.create(dbkata);
        Fileku.create(db);
        if(Kataku.getNumberDB(dbkata)==0)
        {
            DownloadDataKata();
        }
        ListView listDocument = (ListView) findViewById(R.id.listViewResults);
        //cek ada filenya gak?
        if(Fileku.cektabelexist(db))
        {
           // Toast.makeText(this,"db File ada",Toast.LENGTH_LONG).show();
            int jumdata=Fileku.getJumData(db);
            if(jumdata!=0)
            {
               // Toast.makeText(this,"Jumlah data file="+jumdata,Toast.LENGTH_LONG).show();
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





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(listDocument);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(OpenDataLocal.this);
                dialog.setContentView(R.layout.popupnamefile);
                dialog.setTitle("Masukkan Nama File:");

                // set the custom dialog components - text, image and button
                final EditText text = (EditText) dialog.findViewById(R.id.id_filename);
                //sString mystring;
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mystring;
                        mystring = text.getText().toString();
                        mystring.replaceAll("\\s+", "");
                        Toast.makeText(OpenDataLocal.this, mystring, Toast.LENGTH_LONG).show();

                        if (!mystring.equals("")) {
                            dialog.dismiss();
                            Intent abc = new Intent(OpenDataLocal.this, MainActivity.class);

                            //kirim extra for main activity
                            abc.putExtra("status", "new_file");
                            abc.putExtra("namefile", text.getText() + ".doc");
                            abc.putExtra("FilePath", "/storage/emulated/0/");
                            startActivity(abc);
                            finish();
                            System.exit(0);
                        }
                    }
                });

                dialog.show();


            }
        });
//        listDocument.setAdapter(documentlistAdapter);
        setTitle("iEditor");
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        //getSupportActionBar().setIcon(R.drawable.welcome);
        listDocument.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //moveFragment();
                Intent ii = new Intent(OpenDataLocal.this, MainActivity.class);

                ii.putExtra("FilePath", path.get(position));
                startActivity(ii);
                finish();
                System.exit(0);
                //Toast.makeText(OpenDataLocal.this,,Toast.LENGTH_SHORT).show();
            }
        });


//        Button open= (Button) findViewById(R.id.open);
//        open.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // This always works
//            }
//        });

    }
    public void DownloadDataKata() {
        DownloadKataTask task = new DownloadKataTask(OpenDataLocal.this);
        String url = "openKata.json";
        Log.i("urlnya", url);
        task.execute(url);
    }


    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        Uri currentUri = null;

        if (resultCode == Activity.RESULT_OK)
        {

            if (requestCode == CREATE_REQUEST_CODE)
            {
                if (resultData != null) {
                    //textView.setText("");
                }
            } else if (requestCode == SAVE_REQUEST_CODE) {

                if (resultData != null) {
                    currentUri = resultData.getData();
                    //writeFileContent(currentUri);
                }
            } else if (requestCode == OPEN_REQUEST_CODE)
            {

                if (resultData != null) {
                    currentUri = resultData.getData();

                    try {
                        String content =
                                readFileContent(currentUri);
                        Toast.makeText(this,content,Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        // Handle error here
                    }
                }
            }
        }
    }


    private String readFileContent(Uri uri) throws IOException {
        String allText="";
        File dir=null;File dir1=null;
        InputStream inputStream =
                getContentResolver().openInputStream(uri);
        Toast.makeText(OpenDataLocal.this,uri.toString(),Toast.LENGTH_LONG).show();

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(
                        inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String currentline;
        while ((currentline = reader.readLine()) != null) {
            stringBuilder.append(currentline + "\n");
        }

        // Get the file path from the URI
        //final String path = FilesUtils.getPath(this, uri);


//            TextView aaa = (TextView) findViewById(R.id.filename_text);
//            aaa.setText(sdCard.getAbsolutePath() + "/" + mypath[1]);


        inputStream.close();
        return stringBuilder.toString();
    }

    public void openFile(View view)
    {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        String [] type={"application/msword"};
        intent.setType("file/*");
        startActivityForResult(intent, OPEN_REQUEST_CODE);
    }


    //
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //TextView pathku=(TextView) findViewById(R.id.path);
//        Intent ii = new Intent(this, MainActivity.class);
//        String []temp = new String[0];
//        String lengkap="";
//        if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {
//            if (data.getBooleanExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false)) {
//                // For JellyBean and above
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    ClipData clip = data.getClipData();
//
//                    if (clip != null) {
//                        for (int i = 0; i < clip.getItemCount(); i++) {
//                            Uri uri = clip.getItemAt(i).getUri();
//                            // Do something with the URI
//                            //pathku.setText(uri.toString());
//                            lengkap=uri.toString();
//                            ii.putExtra("FilePath", uri.toString());
//
//                            temp=uri.toString().split("file:///storage/emulated/0/");
//
//                        }
//                    }
//                    // For Ice Cream Sandwich
//                } else {
//                    ArrayList<String> paths = data.getStringArrayListExtra
//                            (FilePickerActivity.EXTRA_PATHS);
//
//                    if (paths != null) {
//                        for (String path: paths) {
//                            Uri uri = Uri.parse(path);
//                            // Do something with the URI
//                           // pathku.setText(uri.toString());
//                    ii.putExtra("FilePath", uri.toString());
//                            lengkap=uri.toString();
//                            temp=uri.toString().split("file:///storage/emulated/0/");
//
//                        }
//                    }
//                }
//
//            } else {
//                Uri uri = data.getData();
//                temp=uri.toString().split("file:///storage/emulated/0/");
//                // Do something with the URI
//               // pathku.setText(uri.toString());
//                ii.putExtra("FilePath", uri.toString());
//                lengkap=uri.toString();
//            }
//
//            if(Fileku.cektabelexist(db)) {
//               // Toast.makeText(this,"ada tabelnya",Toast.LENGTH_LONG).show();
//                if (!Fileku.getFileName(db, temp[1])) {
//                    DateFormat dateFormatter = new SimpleDateFormat("MMM dd hh.mm");
//                    dateFormatter.setLenient(false);
//                    Date today = new Date();
//                    String s = dateFormatter.format(today);
//                    CFile myFile = new CFile();
//                    int jum=Fileku.getJumData(db)+1;
//                    myFile.id_file=jum;
//                    myFile.nama_file = temp[1].toString();
//                    myFile.pathku = lengkap;
//                    myFile.tanggal_create = s;
//                    myFile.tanggal_update = s;
//
//                    Fileku.insertData(db, myFile);
//                    startActivity(ii);
//                    finish();
//                }
//                else
//                {
//                    startActivity(ii);
//                    finish();
//                }
//            }
//
//        }
//
//    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exitByBackKey() {
        // Write your code here
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(OpenDataLocal.this);

        // Setting Dialog Title
        alertDialog.setTitle("Keluar dari iEditor");

        // Setting Dialog Message
        alertDialog.setMessage("Apakah anda yakin akan keluar dari aplikasi?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //simpan();
                // Write your code here to invoke YES event
                Toast.makeText(getApplicationContext(), "Terima Kasih", Toast.LENGTH_SHORT).show();
                finish();
                System.exit(0);
                //android.os.Process.killProcess(android.os.Process.myPid());
                //System.exit(1);
            }
        });

        // Setting Negative
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
        getMenuInflater().inflate(R.menu.menu_open_data_local, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_buka) {
            openFile(getCurrentFocus());
            //Intent i = new Intent(OpenDataLocal.this, FilePickerActivity.class);
            // This works if you defined the intent filter
            // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

            // Set these depending on your use case. These are the defaults.
//            i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
//            i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
//            i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

            // Configure initial directory like so
//            i.putExtra(FilePickerActivity.EXTRA_START_PATH, "/storage/emulated/0/");
//
//            startActivityForResult(i, FILE_CODE);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
