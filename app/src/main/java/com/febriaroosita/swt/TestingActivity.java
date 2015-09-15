package com.febriaroosita.swt;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;

import java.util.ArrayList;


public class TestingActivity extends ActionBarActivity {
    ArrayList<String> kaataku;
    DbKata kataku;
    DbHistory history;
    String par;
    ArrayList<CFile> dataFileku;
    SQLiteDatabase db;
    SQLiteDatabase dbHis;
    ArrayList<String> kaata;
    ArrayList<String> path;
    MultiAutoCompleteTextView myText;
    ArrayAdapter<String> adapter;
    int start1,before1,count1;

    private final TextWatcher passwordWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            start1=start;
            before1=before;
            count1=count;



        }

        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                myText.setVisibility(View.GONE);
            } else{
                Log.i("start","start: "+start1);
                Log.i("before", "before: " + before1);
                Log.i("count", "count: " + count1);
                Log.i("length", "lenght: " + s.length());
                if(s.length()>=start1+1 && start1>0)
                {
                    String s2=s.toString().substring(start1,start1+1);
                    Log.i("ss",s.toString());
                    Log.i("s2",s2);
                    if(s2.equals(" "))
                    {
                        String []s3=s.toString().split(" ");
                        String s4 = s3[s3.length-1];
                        Log.i("S44", s4);
                        //Toast.makeText(TestingActivity.this, s4, Toast.LENGTH_LONG).show();
                        //kata ada di database
                        if(kataku.getDataByKata(db,s4))
                        {
                            int jum_kata=kataku.getJumKata(db,s4);

                            Log.i("kata",s4);
                            Log.i("jumlahkata","jumlah kata:"+jum_kata);
                            jum_kata++;
                            kataku.updateJumData(db, jum_kata, s4);
                            kaataku=kataku.getAllKataHistory(db,s4);
                            adapter = new ArrayAdapter<String>(TestingActivity.this,android.R.layout.simple_list_item_1,kaataku);
                            myText.setAdapter(adapter);
                        }
                        else
                        {
                            //kata gak ada di database, cek di history.
                            history = new DbHistory(TestingActivity.this);

                            dbHis =history.getWritableDatabase();
                            int jum=history.getJumDB(dbHis);
                            CHistory data = new CHistory();
                            data.id_kata=jum;
                            data.kata=s4;
                            data.tanggal="";
                            data.weight=1.0;
                            Log.i("baru",s4);
                            if(history.getDataByKata(dbHis,s4))
                            {
                                data.jumlah_digunakan=history.getJumKata(dbHis,s4)+1;
                                history.updateData(dbHis,data);
                            }
                            else
                            {

                                data.jumlah_digunakan=1;
                                history.insertData(dbHis,data);
                            }
                            kaataku=kataku.getAllKataHistory(db,s4);
                            adapter = new ArrayAdapter<String>(TestingActivity.this,android.R.layout.simple_list_item_1,kaataku);
                            myText.setAdapter(adapter);

                        }



                    }
                   // Toast.makeText(TestingActivity.this, s2,Toast.LENGTH_LONG).show();
                    myText.setVisibility(View.VISIBLE);
                }
               // Toast.makeText(TestingActivity.this, s.toString(),Toast.LENGTH_LONG).show();
                //myText.setText("You have entered : " + myText.getText());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        history = new DbHistory(TestingActivity.this);

        dbHis =history.getWritableDatabase();
        history.createtable(dbHis);

        kataku = new DbKata(this);

        db =kataku.getWritableDatabase();
        kaataku=kataku.getAllKataHistory(db,"");

        ListView listDocument = (ListView) findViewById(R.id.listViewResults);

        // get the defined string-array
        //String[] colors = getResources().getStringArray(R.array.colorList);


        adapter = new ArrayAdapter<String>(TestingActivity.this,android.R.layout.simple_list_item_1,kaataku);
         myText=(MultiAutoCompleteTextView)findViewById(R.id.textviewku);
        myText.setThreshold(1);
        myText.setDropDownBackgroundResource(R.color.primary);
        myText.setAdapter(adapter);
        myText.setTokenizer(new SpaceTokenizer());
        myText.addTextChangedListener(passwordWatcher);
        ((ArrayAdapter) myText.getAdapter()).notifyDataSetChanged();

        // when the user clicks an item of the drop-down list
        myText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override


            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,

                                    long arg3) {

//                Toast.makeText(getBaseContext(), "MultiAutoComplete: " +
//
//                                "you add color " + arg0.getItemAtPosition(arg2),
//
//                        Toast.LENGTH_LONG).show();
                //nambahi jumlah digunakan
                int jum_kata=kataku.getJumKata(db,arg0.getItemAtPosition(arg2).toString());
                Log.i("kata",arg0.getItemAtPosition(arg2).toString());
                Log.i("jumlahkata","jumlah kata:"+jum_kata);
                jum_kata++;
                kataku.updateJumData(db,jum_kata,arg0.getItemAtPosition(arg2).toString());


            }


        });



//        EditText myEditText=(EditText)findViewById(R.id.myText);
//        myEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                //disini dicek buat rekomendasinya
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(getPositionFromSeparator(s,s.length()-1)==1)
//                {
//                    //masuk disini ngecek buat ngasiin garis;
//                    Toast.makeText(TestingActivity.this,"ayo dicek",Toast.LENGTH_SHORT).show();
//                }
//                //Toast.makeText(TestingActivity.this,s,Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
    }

    public int getPositionFromSeparator(CharSequence firstChar,int in)
    {
        //postBefore buat bandingin sama yang sebelomnya
        int check=0;
        //ascii 32-47
        //58-64
        //91-96
        //123-126
        //posEnter != -1 && (posEnter < pos || pos == -1)
        for(int i=32;i<=47;i++)
        {
            if((int)firstChar.charAt(in)==i)
            {
                check=1;
            }
        }
        for(int i=58;i<=64;i++)
        {
            if((int)firstChar.charAt(in)==i)
            {
                check=1;
            }
        }
        for(int i=91;i<=96;i++)
        {
            if((int)firstChar.charAt(in)==i)
            {
                check=1;
            }
        }
        for(int i=123;i<=126;i++)
        {
            if((int)firstChar.charAt(in)==i)
            {
                check=1;
            }
        }

        if(firstChar.charAt(in)=='\r'||firstChar.charAt(in)=='\t'||firstChar.charAt(in)=='\n')
        {
            check=1;
        }
        return check;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_testing, menu);
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
            Intent ii = new Intent(this, SplashScreenActivity.class);
            startActivity(ii);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
