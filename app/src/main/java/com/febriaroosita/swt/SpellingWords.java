package com.febriaroosita.swt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.widget.EditText;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Febria on 5/13/2015.
 */
public class SpellingWords extends AsyncTask<String, Void, SpannableString> {
    ProgressDialog pDialog;
    Activity ActiveActivity;
    String allText;

    DbKata kata;
    String par;
    ArrayList<CKata> myKata;
    private EditText ma;
    String filePath;
    int firstchar, lastchar;
    ArrayList<removalClass> myremoval;


    // public CBudayaAdapter budayaAdapter;

    DbHistory his;

    // public CBudayaAdapter budayaAdapter;

    public SQLiteDatabase db;
    public SQLiteDatabase db1;
    public DbKata DBKata;
    public SpellingWords(Activity pass, int first, int last) {
        ActiveActivity=pass;
        DBKata = new DbKata(pass);
        his=new DbHistory(pass);
        db = DBKata.getWritableDatabase();
        db1 = his.getWritableDatabase();
        firstchar = first;
        lastchar = last;
        myremoval = new ArrayList<removalClass>();
    }

    public int distanceKata(String kata1, String kata2) {
        int m = kata1.length();
        int n = kata2.length();

        int lenMax = m;
        int lenMin = m;
        if (n < lenMin) {
            lenMin = n;
        }
        if (n > lenMax) {
            lenMax = n;
        }
        int distance = lenMax - lenMin;
        int i;
        for (i = 0; i < lenMin; i++) {
            if (kata1.toLowerCase().charAt(i) != kata2.toLowerCase().charAt(i)) {
                distance++;
            }
        }
        return distance;
    }


    public String getKataSub(int pos, int end) {
        String par = allText;
        //Log.e("tess","start: "+String.valueOf(pos)+" | end : "+String.valueOf(end));
        String cekKata = par.substring(pos, end);

        return cekKata;
    }

    public boolean checkKataFromDB(String kataku) {

        //boolean kataAda = kata.getDataByKata(db, kataku);
        // return kataAda;
        return kata.getDataByKata(db, kataku);
    }

    public boolean cekKataPos(int pos, int end) {
        String par = allText;
        //   Log.e("tess","start: "+String.valueOf(pos)+" | end : "+String.valueOf(end));
        String cekKata = par.substring(pos, end);


        boolean kataAda = kata.getDataByKata(db, cekKata);

        //Log.e("tess",cekKata);
        //int i;
        //int minDistance = 99999999;
        /*for(i=0; i<myKata.size(); i++){
            int distance = distanceKata(cekKata,myKata.get(i).kata);
            if(myKata.get(i).kata.equals("saya"))
            {
                Log.e("tess","ada kata saya");
            }
            if(distance < minDistance){
                minDistance = distance;
            }
            if(minDistance <= 0){
                kataSalah = false;
                break;
            }
        }*/

        return kataAda;

    }
    private String findMatch1a(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^ber([aiueo].*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match = m.group(1);
        }
        return match;
    }
    private String findMatch1b(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^ber([aiueo].*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match = "r" + m.group(1);
        }
        return match;
    }
    private String findMatch2(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^ber([bcdfghjklmnpqrstvwxyz])([a-z])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            String pattern2 = "^er(.*)$";
            regEx = Pattern.compile(pattern2);
            Matcher m1 =regEx.matcher(m.group(3));
            if(!m1.find())
            {
                match=m.group(1)+m.group(2)+m.group(3);
            }
        }
        return match;
    }
    private String findMatch3(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^ber([bcdfghjklmnpqrstvwxyz])([a-z])er([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            if(m.group(1)!="r")
            {
                match=m.group(1)+m.group(2)+"er"+m.group(3)+m.group(4);
            }
        }
        return match;
    }
    private String findMatch4(String myString) {

        String match = "";

        if (myString.equals("belajar")) {
            return "ajar";
        }
        return match;
    }
    // Rule 5 : beC1erC2 -> be-C1erC2 where C1 != 'r'
    private String findMatch5(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^be([bcdfghjklmnpqstvwxyz])(er[bcdfghjklmnpqrstvwxyz])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2)+m.group(3);
        }

        return match;
    }
    private String findMatch6a(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^ter([aiueo].*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1);
        }

        return match;
    }
    private String findMatch6b(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^ter([aiueo].*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match="r"+m.group(1);
        }

        return match;
    }

    private String findMatch7(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^ter([bcdfghjklmnpqrstvwxyz])er([aiueo].*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            if(m.group(1)!="r")
            {
                match=m.group(1)+"er"+m.group(2);
            }
        }
        return match;
    }
    private String findMatch8(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^ter([bcdfghjklmnpqrstvwxyz])([a-z])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            String pattern2 = "^er(.*)$";
            regEx = Pattern.compile(pattern2);
            Matcher m1 =regEx.matcher(m.group(3));
            if(!m1.find() && m.group(1)!="r")
            {
                match=m.group(1)+m.group(2)+m.group(3);
            }
        }
        return match;
    }
    private String findMatch9(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^te([bcdfghjklmnpqrstvwxyz])er([bcdfghjklmnpqrstvwxyz])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            if(m.group(1)!="r")
            {
                match=m.group(1)+"er"+m.group(2)+m.group(3);
            }
        }
        return match;
    }

    private String findMatch10(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^me([lrwy])([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            if(m.group(1)!="r")
            {
                match=m.group(1)+m.group(2)+m.group(3);
            }
        }
        return match;
    }
    //
    private String findMatch11(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^mem([bfv])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2);
        }
        return match;
    }
    //^mempe(.*)$
    private String findMatch12(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^mempe(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match="pe"+m.group(1);
        }
        return match;
    }
    private String findMatch13a(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "mem([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match="m"+m.group(1)+m.group(2);
        }
        return match;
    }

    private String findMatch13b(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^mem([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match="p"+m.group(1)+m.group(2);
        }
        return match;
    }
    //^men([cdjstz])(.*)$

    private String findMatch14(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^men([cdjstz])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2);
        }
        return match;
    }

    private String findMatch15a(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^men([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match="n"+m.group(1)+m.group(2);
        }
        return match;
    }
    private String findMatch15b(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^men([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match="t"+m.group(1)+m.group(2);
        }
        return match;
    }
    private String findMatch16(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^meng([g|h|q|k])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2);
        }
        return match;
    }

    private String findMatch17a(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^meng([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2);
        }
        return match;
    }
    private String findMatch17b(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^meng([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match="k"+m.group(1)+m.group(2);
        }
        return match;
    }
    private String findMatch17c(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^menge(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1);
        }
        return match;
    }
    private String findMatch17d(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^meng([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match="ng"+m.group(1)+m.group(2);
        }
        return match;
    }
    private String findMatch18a(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^meny([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match="ny"+m.group(1)+m.group(2);
        }
        return match;
    }


    private String findMatch18b(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^meny([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match="s"+m.group(1)+m.group(2);
        }
        return match;
    }


    private String findMatch19(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^memp([abcdfghijklmopqrstuvwxyz])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match="p"+m.group(1)+m.group(2);
        }
        return match;
    }

    //^pe([wy])([aiueo])(.*)$
    private String findMatch20(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^pe([wy])([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2)+m.group(3);
        }
        return match;
    }

    private String findMatch21a(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^per([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2);
        }
        return match;
    }
    private String findMatch21b(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^pe(r[aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2);
        }
        return match;
    }
    private String findMatch23(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^per([bcdfghjklmnpqrstvwxyz])([a-z])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            String pattern2 = "^er(.*)$";
            regEx = Pattern.compile(pattern2);
            Matcher m1 =regEx.matcher(m.group(3));
            if(!m1.find() && m.group(1)!="r")
            {
                match=m.group(1)+m.group(2)+m.group(3);
            }
        }
        return match;
    }
    //^per([bcdfghjklmnpqrstvwxyz])([a-z])er([aiueo])(.*)$

    private String findMatch24(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^per([bcdfghjklmnpqrstvwxyz])([a-z])er([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {

            if(m.group(1)!="r")
            {
                match=m.group(1)+m.group(2)+"er"+m.group(3)+m.group(4);
            }
        }
        return match;
    }
    private String findMatch25(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^pem([bfv])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2);

        }
        return match;
    }
    private String findMatch26a(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^pem([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match="m"+m.group(1)+m.group(2);
        }
        return match;
    }
    private String findMatch26b(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^pem([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match="p"+m.group(1)+m.group(2);
        }
        return match;
    }
    private String findMatch27(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^pen([cdjz])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2);
        }
        return match;
    }
    private String findMatch28a(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^pen([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match="n"+m.group(1)+m.group(2);
        }
        return match;
    }
    private String findMatch28b(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^pen([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match="t"+m.group(1)+m.group(2);
        }
        return match;
    }

    //
    private String findMatch29(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^peng([bcdfghjklmnpqrstvwxyz])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2);
        }
        return match;
    }
    //
    private String findMatch30a(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^peng([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2);
        }
        return match;
    }

    private String findMatch30b(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^peng([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match="k"+m.group(1)+m.group(2);
        }
        return match;
    }
    private String findMatch30c(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^penge(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1);
        }
        return match;
    }
    private String findMatch31a(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^peny([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match="ny"+m.group(1)+m.group(2);
        }
        return match;
    }
    private String findMatch31b(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^peny([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match="s"+m.group(1)+m.group(2);
        }
        return match;
    }
    private String findMatch32(String myString) {

        String match = "";
        if(myString.equals("pelajar"))
        {
            match="ajar";
        }
        else {
            // Pattern to find code
            String pattern = "^pe(l[aiueo])(.*)$";  // Sequence of 8 digits'

            Pattern regEx = Pattern.compile(pattern);
            Matcher m = regEx.matcher(myString);
            if (m.find()) {
                match = m.group(1) + m.group(2);
            }
        }
        return match;
    }
    private String findMatch34(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^pe([bcdfghjklmnpqrstvwxyz])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            String pattern2 = "^er(.*)$";
            regEx = Pattern.compile(pattern2);
            Matcher m1 =regEx.matcher(m.group(2));
            if(!m1.find())
            {
                match=m.group(1)+m.group(2);
            }
        }
        return match;
    }
    private String findMatch35(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^ter([bcdfghjkpqstvxz])(er[bcdfghjklmnpqrstvwxyz])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2)+m.group(3);
        }
        return match;
    }
    //^pe([bcdfghjkpqstvxz])(er[bcdfghjklmnpqrstvwxyz])(.*)$
    private String findMatch36(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^pe([bcdfghjkpqstvxz])(er[bcdfghjklmnpqrstvwxyz])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2)+m.group(3);
        }
        return match;
    }
    private String findMatch37a(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^([bcdfghjklmnpqrstvwxyz])(er[aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2)+m.group(3);
        }
        return match;
    }
    private String findMatch37b(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^([bcdfghjklmnpqrstvwxyz])er([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2)+m.group(3);
        }
        return match;
    }
    private String findMatch38a(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^([bcdfghjklmnpqrstvwxyz])(el[aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2)+m.group(3);
        }
        return match;
    }
    private String findMatch38b(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^([bcdfghjklmnpqrstvwxyz])el([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2)+m.group(3);
        }
        return match;
    }
    private String findMatch39a(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^([bcdfghjklmnpqrstvwxyz])(em[aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2)+m.group(3);
        }
        return match;
    }
    private String findMatch39b(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^([bcdfghjklmnpqrstvwxyz])em([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2)+m.group(3);
        }
        return match;
    }
    private String findMatch40a(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^([bcdfghjklmnpqrstvwxyz])(in[aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2)+m.group(3);
        }
        return match;
    }
    private String findMatch40b(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^([bcdfghjklmnpqrstvwxyz])in([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1)+m.group(2)+m.group(3);
        }
        return match;
    }
    private String findMatch41(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^ku(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1);
        }
        return match;
    }
    private String findMatch42(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^kau(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            match=m.group(1);
        }
        return match;
    }
    public String removeSuffix(String kata){
        //(is|isme|isasi|i|kan|an)$
        String match = "";

        // Pattern to find code
        String pattern = "(is|isme|isasi|i|kan|an)$";  // Sequence of 8 digits'
        match = kata.replaceAll(pattern,"");
        /*
        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(kata);
        if (m.find()) {
            match=m.group(0);
            removalClass temp1 = new removalClass();
            temp1.affixType = "DS";
            temp1.removedPart = match;
            match = kata.split(match)[0];
            myremoval.add(temp1);
        }*/
        return match;
    }
    //-*(lah|kah|tah|pun)$
    public String removeInflectionalParticle(String kata){
        //(is|isme|isasi|i|kan|an)$
        String match = "";

        // Pattern to find code
        String pattern = "(lah|kah|tah|pun)$";  // Sequence of 8 digits'
        match = kata.replaceAll(pattern,"");
        /*Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(kata);
        if (m.find()) {
            match=m.group(1);
            removalClass temp1 = new removalClass();
            temp1.affixType = "P";
            temp1.removedPart = match;
            match = kata.split(match)[0];
            myremoval.add(temp1);
        }*/
        return match;
    }
    //inflectional possessive pronoun
    public String removeInflectionalPossesivePronoun(String kata){
        //(is|isme|isasi|i|kan|an)$
        String match = "";

        // Pattern to find code
        String pattern = "(ku|mu|nya)$";  // Sequence of 8 digits'
        match = kata.replaceAll(pattern,"");
        /*Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(kata);
        if (m.find()) {
            match=m.group(1);
            removalClass temp1 = new removalClass();
            temp1.affixType = "DS";
            temp1.removedPart = match;
            match = kata.split(match)[0];
            myremoval.add(temp1);
        }*/
        return match;
    }

    //^(di|ke|se)
    private String removePlainPrefix(String kata){
        String match = "";
        String temp = kata.toLowerCase();
        // Pattern to find code
        String pattern = "^(di|ke|se)";  // Sequence of 8 digits'
        match = temp.replaceAll(pattern,"");
        /*Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(kata);
        if (m.find()) {
            match=m.group(1);
            removalClass temp1 = new removalClass();
            temp1.affixType = "DP";
            temp1.removedPart = match;
            match = kata.split(match)[0];
            myremoval.add(temp1);
        }*/
        return match;

    }
    private String checkPrural(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^(.*)-(ku|mu|nya|lah|kah|tah|pun)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            if(myString.contains("-"))
            {
                stemPrural(m.group(1));

                return m.group(1);

            }
        }
        return myString;
    }


    private String stemPrural(String myString) {

        String match = "";

        // Pattern to find code
        String pattern = "^(.*)-(ku|mu|nya|lah|kah|tah|pun)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            if(myString.contains("-"))
            {
                //^(.*)-(.*)$
                String pattern1 = "^(.*)-(.*)$";  // Sequence of 8 digits'

                Pattern regEx1 = Pattern.compile(pattern);
                Matcher m1 = regEx.matcher(myString);
                if(!m1.group(1).isEmpty() || !m1.group(2).isEmpty())
                {
/*
* // malaikat-malaikat-nya -> malaikat malaikat-nya
        $suffix = $words[2];
        if (in_array($suffix, array('ku', 'mu', 'nya', 'lah', 'kah', 'tah', 'pun')) &&
            preg_match('/^(.*)-(.*)$/', $words[1], $words)) {
            $words[2] .= '-' . $suffix;
        }

        // berbalas-balasan -> balas
        $rootWord1 = $this->stemSingularWord($words[1]);
        $rootWord2 = $this->stemSingularWord($words[2]);

        // meniru-nirukan -> tiru
        if (!$this->dictionary->contains($words[2]) && $rootWord2 === $words[2]) {
            $rootWord2 = $this->stemSingularWord('me' . $words[2]);
        }

        if ($rootWord1 == $rootWord2) {
            return $rootWord1;
        } else {
            return $plural;
        }
* */

                }
                return m.group(1);

            }
        }
        return myString;
    }

    private String check(String myString) {
        String match="";
        //check prural
        myString = myString.toLowerCase();
        String temp = myString;
        myString = checkPrural(myString);

        //cek short kata

        if (myString.length() <= 3) {
            Log.i("stem", "kata terlalu singkat, tidak bisa berimbuhan");
            match = myString;
        } else {
            temp = myString;

            //suffix
            if (!removeInflectionalParticle(myString).equals("")) {
                myString = removeInflectionalParticle(myString);
                if (cekKata(myString)) {
                    return myString;
                }
            }
            if (!removeInflectionalPossesivePronoun(myString).equals("")) {

                myString = removeInflectionalPossesivePronoun(myString);
                if (cekKata(myString)) {
                    return myString;
                }
            }
            if (!removeSuffix(myString).equals("")) {

                myString = removeSuffix(myString);
                if (cekKata(myString)) {
                    return myString;
                }
            }
            // {di|ke|se}

            String sementara = "";
            if (!removePlainPrefix(myString).equals("")) {
                match = removePlainPrefix(myString);
                if (cekKata(match)) {
                    return match;
                }
            }

            if (!findMatch1a(myString).equals("")) {
                sementara = findMatch1a(myString);
                if (cekKata(sementara)) {
                    match = sementara;
                } else {
                    if (!findMatch1b(myString).equals("")) {
                        match = findMatch1b(myString);
                    }
                }

            } else if (!findMatch2(myString).equals("")) {
                match = findMatch2(myString);
            } else if (!findMatch3(myString).equals("")) {
                match = findMatch3(myString);
            } else if (!findMatch4(myString).equals("")) {
                match = findMatch4(myString);
            } else if (!findMatch5(myString).equals("")) {
                match = findMatch5(myString);
            } else if (!findMatch6a(myString).equals("")) {
                sementara = findMatch6a(myString);
                if (cekKata(sementara)) {
                    match = sementara;
                } else {
                    if (!findMatch6b(myString).equals("")) {
                        match = findMatch6b(myString);

                    }
                }

            } else if (!findMatch7(myString).equals("")) {
                match = findMatch7(myString);
            } else if (!findMatch8(myString).equals("")) {
                match = findMatch8(myString);
            } else if (!findMatch9(myString).equals("")) {
                match = findMatch9(myString);
            } else if (!findMatch10(myString).equals("")) {
                match = findMatch10(myString);
            } else if (!findMatch11(myString).equals("")) {
                match = findMatch11(myString);
            } else if (!findMatch12(myString).equals("")) {
                match = findMatch12(myString);
            } else if (!findMatch13a(myString).equals("")) {
                sementara = findMatch13a(myString);
                if (cekKata(sementara)) {
                    match = sementara;
                } else {
                    if (!findMatch13b(myString).equals("")) {
                        match = findMatch13b(myString);

                    }
                }

            } else if (!findMatch14(myString).equals("")) {
                match = findMatch14(myString);
            } else if (!findMatch15a(myString).equals("")) {
                sementara = findMatch15a(myString);
                if (cekKata(sementara)) {
                    match = sementara;
                } else {
                    if (!findMatch15b(myString).equals("")) {
                        match = findMatch15b(myString);

                    }
                }
            } else if (!findMatch16(myString).equals("")) {
                match = findMatch16(myString);
            } else if (!findMatch17a(myString).equals("")) {
                sementara = findMatch17a(myString);
                if (cekKata(sementara)) {
                    match = sementara;
                } else {
                    sementara = findMatch17b(myString);
                    if (cekKata(sementara)) {
                        match = sementara;
                    } else if (!findMatch17c(myString).equals("")) {
                        sementara = findMatch17c(myString);
                        if (cekKata(sementara)) {
                            match = sementara;
                        } else if (!findMatch17d(myString).equals("")) {

                            match = findMatch17d(myString);

                        }

                    }
                }
            } else if (!findMatch18a(myString).equals("")) {
                sementara = findMatch18a(myString);
                if (cekKata(sementara)) {
                    match = sementara;
                } else {
                    if (!findMatch18b(myString).equals("")) {
                        match = findMatch18b(myString);

                    }
                }
            } else if (!findMatch19(myString).equals("")) {
                match = findMatch19(myString);
            } else if (!findMatch20(myString).equals("")) {
                match = findMatch20(myString);
            } else if (!findMatch21a(myString).equals("")) {
                sementara = findMatch21a(myString);
                if (cekKata(sementara)) {
                    match = sementara;
                } else {
                    if (!findMatch21b(myString).equals("")) {
                        match = findMatch21b(myString);

                    }
                }
            } else if (!findMatch23(myString).equals("")) {
                match = findMatch23(myString);
            } else if (!findMatch24(myString).equals("")) {
                match = findMatch24(myString);
            } else if (!findMatch25(myString).equals("")) {
                match = findMatch25(myString);
            } else if (!findMatch26a(myString).equals("")) {
                sementara = findMatch26a(myString);
                if (cekKata(sementara)) {
                    match = sementara;
                } else {
                    if (!findMatch26b(myString).equals("")) {
                        match = findMatch26b(myString);

                    }
                }
            } else if (!findMatch27(myString).equals("")) {
                match = findMatch27(myString);
            } else if (!findMatch28a(myString).equals("")) {
                sementara = findMatch28a(myString);
                if (cekKata(sementara)) {
                    match = sementara;
                } else {
                    if (!findMatch28b(myString).equals("")) {
                        match = findMatch28b(myString);

                    }
                }
            } else if (!findMatch29(myString).equals("")) {
                return findMatch29(myString);
            } else if (!findMatch30a(myString).equals("")) {
                sementara = findMatch30a(myString);
                if (cekKata(sementara)) {
                    match = sementara;
                } else {
                    sementara = findMatch30b(myString);
                    if (cekKata(sementara)) {
                        match = sementara;
                    } else if (!findMatch30c(myString).equals("")) {

                        match = findMatch30c(myString);

                    }
                }
            } else if (!findMatch31a(myString).equals("")) {
                sementara = findMatch31a(myString);
                if (cekKata(sementara)) {
                    match = sementara;
                } else {
                    if (!findMatch31b(myString).equals("")) {
                        match = findMatch31b(myString);

                    }
                }
            } else if (!findMatch32(myString).equals("")) {
                match = findMatch32(myString);
            } else if (!findMatch34(myString).equals("")) {
                match = findMatch34(myString);
            } else if (!findMatch35(myString).equals("")) {
                match = findMatch35(myString);
            } else if (!findMatch36(myString).equals("")) {
                match = findMatch36(myString);
            } else if (!findMatch37a(myString).equals("")) {
                sementara = findMatch37a(myString);
                if (cekKata(sementara)) {
                    match = sementara;
                } else {
                    if (!findMatch37b(myString).equals("")) {
                        match = findMatch37b(myString);

                    }
                }
            } else if (!findMatch38a(myString).equals("")) {
                sementara = findMatch38a(myString);
                if (cekKata(sementara)) {
                    match = sementara;
                } else {
                    if (!findMatch38b(myString).equals("")) {
                        match = findMatch38b(myString);

                    }
                }
            } else if (!findMatch39a(myString).equals("")) {
                sementara = findMatch39a(myString);
                if (cekKata(sementara)) {
                    match = sementara;
                } else {
                    if (!findMatch39b(myString).equals("")) {
                        match = findMatch39b(myString);

                    }
                }
            } else if (!findMatch40a(myString).equals("")) {
                sementara = findMatch40a(myString);
                if (cekKata(sementara)) {
                    match = sementara;
                } else {
                    if (!findMatch40b(myString).equals("")) {
                        match = findMatch40b(myString);

                    }
                }
            } else if (!findMatch41(myString).equals("")) {
                match = findMatch41(myString);
            } else if (!findMatch42(myString).equals("")) {
                match = findMatch42(myString);
            }

        }

        return match;
    }

    public String checkValidImbuhan(String kata)
    {
        String pattern2[] = {"^be(.*)lah$",
                "^be(.*)an$",
                "^me(.*)i$",
                "^di(.*)i$",
                "^pe(.*)i$",
                "^ter(.*)i$"};
        String pattern1 = "^me(.*)kan$";
        String pattern[] = {"^be(.*)i$",
                "^di(.*)an$",
                "^ke(.*)i$",
                "^ke(.*)kan$",
                "^me(.*)an$",
                "^se(.*)i$",
                "^se(.*)kan$",
                "^te(.*)an$"};

//        String pattern[] = {"^ber(.*)i$",
//                "^di(.*)an$",
//                "^ke(.*)i$",
//                "^ke(.*)kan$",
//                "^me(.*)an$",
//                "^me(.*)an$",
//                "^ter(.*)an$",
//                "^per(.*)an$",};
        boolean statusCheck = true;
        int i=0;
        int count=0;

        String match = "";

        // Pattern to find code

        Pattern regEx = Pattern.compile(pattern1);
        Matcher m = regEx.matcher(kata);
        if (m.find()) {
            match = check(kata);
        }
        else {
            if (kata.equals("ketahui")) {
                match = kata;
            }
            else if(kata.equals("kebijakan"))
            {
                match = "bijak";
            }
            else if(kata.equals("setelah"))
            {
                match = "telah";
            }
            else if(kata.equals("senilai"))
            {
                match = "nilai";
            }
            else if(kata.equals("sehari"))
            {
                match = "hari";
            }
            else if(kata.equals("perbaikan"))
            {
                match = "baik";
            }
            else if(kata.equals("memperoleh"))
            {
                match = "oleh";
            }
            else if(kata.equals("sejumlah"))
            {
                match = "jumlah";
            }
            else if(kata.equals("peneliti"))
            {
                match = "teliti";
            }
            else if(kata.equals("mengantisipasi"))
            {
                match = "antisipasi";
            }
            else if(kata.equals("berjumlah"))
            {
                match = "jumlah";
            }
            else if(kata.equals("terdiri"))
            {
                match = "diri";
            }
            else if(kata.equals("sepekan"))
            {
                match = "pekan";
            }
            else if(kata.equals("berjalan"))
            {
                match = "jalan";
            }
            else if(kata.equals("Sejumlah"))
            {
                match = "jumlah";
            }
            else {
                while (true) {
                    if(i >= pattern.length)
                    {
                        break;
                    }
                    statusCheck = lala(kata, pattern[i]);
                    if (!statusCheck) {
                        count++;
                    }
                    i++;
                }

                if (count == pattern.length) {
                    //remove prefix
                    match = check(kata);
                }
            }
        }
        return match;
    }



    public boolean startStemming(String kataku)
    {
        boolean status=false;
        //cek kata ada di dictionary ga
        if(cekKata(kataku))
        {
            status=true;
        }
        else
        {

            String hsl = checkValidImbuhan(kataku);
            Log.i("hasil", hsl);
            if (cekKata(hsl)) {
                status = true;
            }

        }

        return status;


    }
    public boolean cekKata(String kataku) {
//        String par = ma.getText().toString();
//        Log.e("tess","start: "+String.valueOf(pos)+" | end : "+String.valueOf(end));
//        String cekKata = par.substring(pos,end);


        boolean kataAda = DBKata.getDataByKata(db, kataku);

        //Log.e("tess",kataku);
        //int i;
        //int minDistance = 99999999;
        /*for(i=0; i<myKata.size(); i++){
            int distance = distanceKata(cekKata,myKata.get(i).kata);
            if(myKata.get(i).kata.equals("saya"))
            {
                Log.e("tess","ada kata saya");
            }
            if(distance < minDistance){
                minDistance = distance;
            }
            if(minDistance <= 0){
                kataSalah = false;
                break;
            }
        }*/

        return kataAda;

    }

    public boolean checkAkhiran(String dasar) {
        int awal = 0;
        int akhir1 = 3;//untuk yang jumlahnya 2 huruf
        int akhir2 = 2;//untuk yang jumlahnya 3 huruf
        String dasar1;
        int akhiran1;
        int len = dasar.length();


        String cekKataku, cekKataku2;
        String cekAkhiran1=null, cekAkhiran2=null, cekAkhiran3 = null;
        boolean status = false;
        if (len > 1) {

            //ambil awalan kata
            if (len > 3) {
                cekKataku = dasar.substring(awal, akhir1).toLowerCase();
                cekKataku2 = dasar.substring(awal, akhir2).toLowerCase();
                //ambil akhiran kata
                cekAkhiran1 = dasar.substring(len - 1, len).toLowerCase();
                cekAkhiran2 = dasar.substring(len - 2, len).toLowerCase();
                cekAkhiran3 = dasar.substring(len - 3, len).toLowerCase();
            } else {
                cekKataku = dasar.toLowerCase();
                cekKataku2 = dasar.toLowerCase();
                cekAkhiran1 = dasar.substring(len - 1, len).toLowerCase();
                cekAkhiran2 = dasar.substring(len - 2, len).toLowerCase();

            }
        }
            if (cekAkhiran1.equals("i")) {
                dasar1 = dasar;
                dasar1 = dasar1.substring(awal, len - 1);

                if (cekKata(dasar1)) {
                    //kata ada di database
                    //Log.i("kata berakhiran i", "ada di db " + dasar);
                    status = true;
                    return status;
                } else {
                    //cek akhiran
                    status = stemming(dasar1);
                    //-kan, -an, -i, -lah, dan -nya
                    //Log.i("kata berakhiran i", "coba cek di akhiran "+ dasar);


                }

            }
            if (status == false && len > 2 && (cekAkhiran2.equals("an") || cekAkhiran2.equals("mu") || cekAkhiran2.equals("ku"))) {
                dasar1 = dasar;
                dasar1 = dasar1.substring(awal, len - 2);

                if (cekKata(dasar1)) {
                    //kata ada di database
                    //Log.i("kata berakhiran an", "ada di db " + dasar);
                    status = true;
                    return status;
                } else {
                    status = stemming(dasar1);
                    //cek akhiran
                    // stemming(dasar);
                    //-kan, -an, -i, -lah, dan -nya
                    //Log.i("kata berakhiran an", "coba cek di akhiran "+ dasar);


                }

            }
            // -man, -wan, -wati
            if (status == false && len > 3 && (cekAkhiran3.equals("kan") || cekAkhiran3.equals("lah") || cekAkhiran3.equals("nya") || cekAkhiran3.equals("wati") || cekAkhiran3.equals("wan") || cekAkhiran3.equals("man") || cekAkhiran3.equals("kah"))) {
                dasar1 = dasar;
                dasar1 = dasar1.substring(awal, len - 3);

                if (cekKata(dasar1)) {
                    //kata ada di database
                    //Log.i("kata berakhiran kan", "ada di db "+ dasar);
                    status = true;
                    return status;
                } else {
                    //cek akhiran
                    status = stemming(dasar1);
                    //-kan, -an, -i, -lah, dan -nya
                    //Log.i("kata berakhiran kan", "coba cek di akhiran "+ dasar);
                }

            }


            return status;

    }
    public boolean cekKataHistori(String kataku)
    {
        if(his.getDataByKata(db1,kataku))
        {
            if(his.getJumKata(db1,kataku)>5)
            {
                his.updateDatacount(db1,kataku,his.getJumKata(db1,kataku)+1);

                return true;
            }
            his.updateDatacount(db1,kataku,his.getJumKata(db1,kataku)+1);

        }
        return false;
    }


  public int getPositionFromSeparator(int firstChar)
    {
        //postBefore buat bandingin sama yang sebelomnya
        int posBefore=-1;
        //ascii 32-47
        //58-64
        //91-96
        //123-126
        //posEnter != -1 && (posEnter < pos || pos == -1)
        for(int i=32;i<=47;i++)
        {
            int pos = par.indexOf((char)i,firstChar);
            if(pos != -1 && (pos < posBefore|| posBefore== -1))
            {
                posBefore=pos;
            }
        }
        for(int i=58;i<=64;i++)
        {
            int pos = par.indexOf((char)i,firstChar);
            if(pos != -1 && (posBefore > pos || posBefore == -1))
            {
                posBefore=pos;
            }
        }
        for(int i=91;i<=96;i++)
        {
            int pos = par.indexOf((char)i,firstChar);
            if(pos != -1 && (posBefore > pos || posBefore == -1))
            {
                posBefore=pos;
            }
        }
        for(int i=123;i<=126;i++)
        {
            int pos = par.indexOf((char)i,firstChar);
            if(pos != -1 && (posBefore > pos || posBefore == -1))
            {
                posBefore=pos;
            }
        }
        int pos = par.indexOf("\t",firstChar);
        if(pos != -1 && (posBefore > pos || posBefore == -1))
        {
            posBefore=pos;
        }
        pos = par.indexOf("\r",firstChar);
        if(pos != -1 && (posBefore > pos || posBefore == -1))
        {
            posBefore=pos;
        }
        pos = par.indexOf("\n",firstChar);
        if(pos != -1 && (posBefore > pos || posBefore == -1))
        {
            posBefore=pos;
        }
        return posBefore;
    }
    public boolean lala(String kata,String pattern)
    {
        String match = "";

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(kata);
        if (m.find()) {
            return true;

        }
        return false;

    }
//prosesnya yang ini, ada algo yang lama belum tak apus wkwkkw
    public boolean stemming(String kataku)
    {
        boolean status=false;

        int awal = 0;
        int akhir1 = 3;//untuk yang jumlahnya 2 huruf
        int akhir2 = 2;//untuk yang jumlahnya 3 huruf
        String dasar1;
        int akhiran1;
        // ^[a-zA-Z]+$
        stemming a = new stemming(ActiveActivity);
        try{

            //check angka
            Double bee = Double.parseDouble(kataku);
            status = true;
        }
        catch (NumberFormatException nfe) {
            if(kataku.equals(""))
            {
                status =true;
            }
            String temp = kataku.replaceAll("[^A-Za-z0-9]","").toLowerCase();

            if(cekKata(a.stem(temp)) )
            {
                status = true;

            }
            //nyalakan ini kalo mau ke semula
            //kataku = kataku.replaceAll("[^\\p{L}\\p{Z}]", "");
            //int len = kataku.length();
            //status = startStemming(kataku);


        }
        return status;
    }




    protected void onPreExecute() {
        // TODO Auto-generated method stub
        //create progress dialog
        super.onPreExecute();
        pDialog = new ProgressDialog(ActiveActivity);
        pDialog.setMessage("Mengambil Data dari Server...");
        pDialog.show();

    }

    @Override
    protected SpannableString doInBackground(String... data) {
        // TODO Auto-generated method stub
        SpannableString myspan;
        String semuaDataPath=data[0];
        kata = new DbKata(ActiveActivity);
        db = kata.getWritableDatabase();
        myKata = kata.getAllData(db);

        SpannableString myText = null;
        FileInputStream aa=null;

        allText=data[0].toString();
        par=allText;
        int firstChar = 0;
        int lastChar = -1;
        boolean ketemu = true;

        myText = new SpannableString(allText);
        if(!cekKata(allText)) {
            if (!stemming(allText)) {
                myText.setSpan(new UnderlineSpan(), firstChar, lastChar, 0);

            }
        }


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