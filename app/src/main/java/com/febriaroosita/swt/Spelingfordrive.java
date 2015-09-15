package com.febriaroosita.swt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.EditText;

import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by Febria on 5/13/2015.
 */
public class Spelingfordrive extends AsyncTask<String, Void, SpannableString>
{
    ProgressDialog pDialog;
    Activity ActiveActivity;
    String allText;

    DbKata kata;
    String par;
    ArrayList<CKata> myKata;
    private EditText ma;
    String filePath;

    DbHistory his;

    // public CBudayaAdapter budayaAdapter;

    public SQLiteDatabase db;
    public SQLiteDatabase db1;
    public DbKata DBKata;
    public Spelingfordrive (Activity pass)
    {
        ActiveActivity=pass;
        DBKata = new DbKata(pass);
        his=new DbHistory(pass);
        db = DBKata.getWritableDatabase();
        db1 = his.getWritableDatabase();
    }

    public int distanceKata(String kata1, String kata2){
        int m = kata1.length();
        int n = kata2.length();

        int lenMax = m;
        int lenMin = m;
        if(n < lenMin){
            lenMin = n;
        }
        if(n > lenMax){
            lenMax = n;
        }
        int distance = lenMax-lenMin;
        int i;
        for(i=0; i<lenMin; i++){
            if(kata1.toLowerCase().charAt(i) != kata2.toLowerCase().charAt(i)){
                distance++;
            }
        }
        return distance;



    }
    public String getKataSub(int pos, int end)
    {
        String par = allText;
        //Log.e("tess","start: "+String.valueOf(pos)+" | end : "+String.valueOf(end));
        String cekKata = par.substring(pos, end);

        return cekKata;
    }
    public boolean checkKataFromDB(String kataku)
    {

        boolean kataAda = kata.getDataByKata(db, kataku);

        return kataAda;
    }
    public boolean cekKataPos(int pos, int end){
        String par = allText;
        //   Log.e("tess","start: "+String.valueOf(pos)+" | end : "+String.valueOf(end));
        String cekKata = par.substring(pos,end);


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
    public boolean cekKata(String kataku){
//        String par = ma.getText().toString();
//        Log.e("tess","start: "+String.valueOf(pos)+" | end : "+String.valueOf(end));
//        String cekKata = par.substring(pos,end);


        boolean kataAda = kata.getDataByKata(db, kataku);

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

    public boolean stemming(String kataku)
    {
        boolean status=false;

        int awal = 0;
        int akhir1 = 3;//untuk yang jumlahnya 2 huruf
        int akhir2 = 2;//untuk yang jumlahnya 3 huruf
        String dasar1;
        int akhiran1;
        int len = kataku.length();

        int pot=0;
        String cekKataku = null, cekKataku2 = null,cekKataku3 = null,dasar=kataku;
        String cekAkhiran1=null, cekAkhiran2=null, cekAkhiran3 = null;
        String prefix="";
        int akhir3=4;//untuk yang jumlahnya 4 huruf
        try{
            //cek angka atau bukan
            Double kya = Double.parseDouble(dasar);
            status=true;
            return status;
        }catch (NumberFormatException e) {

            if (len > 1) {

                //ambil awalan kata
                if (len > 3) {
                    cekKataku = kataku.substring(awal, akhir1).toLowerCase();
                    cekKataku3 = kataku.substring(awal, akhir3).toLowerCase();
                    cekKataku2 = kataku.substring(awal, akhir2).toLowerCase();
                    //ambil akhiran kata
                    cekAkhiran1 = kataku.substring(len - 1, len).toLowerCase();
                    cekAkhiran2 = kataku.substring(len - 2, len).toLowerCase();
                    cekAkhiran3 = kataku.substring(len - 3, len).toLowerCase();
                } else {
                    cekKataku = kataku.toLowerCase();
                    cekKataku2 = kataku.toLowerCase();

                    cekKataku3 = kataku.toLowerCase();
                    cekAkhiran1 = kataku.substring(len - 1, len).toLowerCase();
                    cekAkhiran2 = kataku.substring(len - 2, len).toLowerCase();
                    cekAkhiran3 = kataku.substring(len - 1, len).toLowerCase();

                }
                if (cekKata(kataku)) {

                    kata.updateJumData(db,kata.getJumKata(db,kataku)+1,kataku);
                    status = true;
                }
                else if(cekKataHistori(kataku))
                {
                    status=true;
                }

                if (cekAkhiran3.equals("kah") || cekAkhiran3.equals("lah") || cekAkhiran3.equals("pun")) {
                    dasar1 = kataku;
                    dasar1 = dasar1.substring(awal, len - 3).toLowerCase();

                    if (cekKata(dasar1)) {
                        //kata ada di database
                        //Log.i("kata berakhiran i", "ada di db " + dasar);
                        kata.updateJumData(db, kata.getJumKata(db, dasar1)+1, dasar1);
                        status = true;
                        return status;
                    }
                }
                //cek akhiran inflectional possesive pronouns

                if (cekAkhiran2.equals("ku") || cekAkhiran2.equals("mu") || cekAkhiran3.equals("nya")) {
                    dasar1 = kataku;
                    if (cekAkhiran3.equals("nya")) {
                        dasar1 = dasar1.substring(awal, len - 3).toLowerCase();
                    } else {
                        dasar1 = dasar1.substring(awal, len - 2).toLowerCase();
                    }

                    if (cekKata(dasar1)) {
                        //kata ada di database
                        //Log.i("kata berakhiran i", "ada di db " + dasar);
                        kata.updateJumData(db,kata.getJumKata(db,dasar1)+1,dasar1);

                        status = true;
                        return status;
                    }
                }
                if (cekKataku3.equals("meng")) {
                    prefix = "meng";
                    dasar = kataku.substring(akhir3, len).toLowerCase();
                    pot=4;
                    if (cekKata(dasar)) {
                        kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);

                        status = true;
                        return status;
                    }
                }
                else if (cekKataku3.equals("meny")) {
                    prefix = "meny";
                    pot=4;
                    dasar = kataku.substring(akhir3, len).toLowerCase();
                    if (cekKata(dasar)) {
                        kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                        status = true;
                        return status;
                    }
                }
                else if (cekKataku3.equals("meny")) {
                    pot=4;
                    prefix = "meny";
                    dasar = kataku.substring(akhir3, len).toLowerCase();
                    if (cekKata(dasar)) {

                        kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                        status = true;
                        return status;
                    } else {
                        //kataserapan
                        String temp = "s".concat(dasar);
                        if (cekKata(temp)) {
                            kata.updateJumData(db,kata.getJumKata(db,temp)+1,temp);
                            //kalau ada di db
                            if (kata.getType(db, temp) == "v") {
                                status = stemming(temp);
                                return status;
                            }
                        }
                    }
                }
                else if (cekKataku.equals("men")) {
                    prefix = "men";
                    pot=3;
                    dasar = kataku.substring(akhir1, len).toLowerCase();
                    if (cekKata(dasar)) {
                        kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                        status = true;
                        return status;
                    }
                    else
                    {
                        String temp = "t".concat(dasar);
                        if (cekKata(temp)) {
                            if (kata.getType(db, temp) == "v") {
                                kata.updateJumData(db,kata.getJumKata(db,temp)+1,temp);
                                status = true;
                                return status;
                            }
                        }
                        else
                        {
                            status = stemming(temp);
                        }
                    }
                }
                else if (cekKataku.equals("mem")) {
                    prefix = "mem";
                    pot=3;
                    dasar = kataku.substring(akhir1, len).toLowerCase();
                    if (cekKata(dasar)) {
                        kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                        status = true;
                        return status;
                    } else {
                        //kataserapan
                        String temp = "p".concat(dasar);
                        if (cekKata(temp)) {
                            if (kata.getType(db, temp) == "v") {
                                kata.updateJumData(db,kata.getJumKata(db,temp)+1,temp);
                                status = true;
                                return status;
                            }

                            else
                            {
                                status = stemming(temp);
                            }
                        }

                    }
                }
                else if (cekKataku2.equals("me")) {
                    pot=2;
                    prefix = "me";
                    dasar = kataku.substring(akhir2, len);

                    if (cekKata(dasar)) {

                        kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                        status = true;
                        return status;
                    }
                }
                else if (cekKataku3.equals("peng")) {
                    pot=4;
                    prefix = "peng";
                    dasar = kataku.substring(akhir3, len).toLowerCase();
                    if (cekKata(dasar)) {
                        kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);

                        status = true;
                        return status;
                    }
                }
                else if (cekKataku.equals("peny")) {
                    pot=4;
                    prefix = "peny";
                    dasar = kataku.substring(akhir3, len).toLowerCase();
                    if (cekKata(dasar)) {

                        kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                        status = true;
                        return status;
                    } else {
                        //kataserapan
                        String temp = "s".concat(dasar);
                        if (cekKata(temp)) {

//                            kata.updateJumData(db,kata.getJumKata(db,temp)+1,temp);
                            //kalau ada di db
                            if (kata.getType(db, temp) == "v") {

                                kata.updateJumData(db,kata.getJumKata(db,temp)+1,temp);
                                status = true;
                                return status;
                            }

                            else
                            {
                                status = stemming(temp);
                            }
                        }

                    }
                }
                else if (cekKataku.equals("pen")) {
                    pot=3;prefix = "pen";
                    dasar = kataku.substring(akhir1, len).toLowerCase();
                    if (cekKata(dasar)) {

                        kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                        status = true;
                        return status;
                    }
                }
                else if (cekKataku.equals("pem")) {
                    pot=3;prefix = "pem";
                    dasar = kataku.substring(akhir3, len).toLowerCase();
                    if (cekKata(dasar)) {

                        kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                        status = true;
                        return status;
                    } else {
                        //kataserapan
                        String temp = "p".concat(dasar);
                        if (cekKata(temp)) {
                            //kalau ada di db
                            if (kata.getType(db, temp) == "v") {
                                status = true;
                                kata.updateJumData(db,kata.getJumKata(db,temp)+1,temp);
                                return status;
                            }
                        }

                        else
                        {
                            status = stemming(temp);
                        }

                    }
                }

                else if (cekKataku2.equals("di")) {
                    pot=2;prefix = "di";
                    dasar = kataku.substring(akhir2, len).toLowerCase();
                    if (cekKata(dasar)) {

                        kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                        status = true;

                    }
                }

                else if (cekKataku.equals("ter")) {
                    pot=3;prefix = "ter";
                    dasar = kataku.substring(akhir1, len).toLowerCase();
                    if (cekKata(dasar)) {

                        kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                        status = true;
                    }
                }

                else if (cekKataku2.equals("ke")) {
                    pot=2;prefix = "ke";
                    dasar = kataku.substring(akhir2, len).toLowerCase();
                    if (cekKata(dasar)) {

                        kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                        status = true;return status;
                    }
                }

                else if (cekKataku.equals("ber")) {
                    pot=3;prefix = "ber";
                    dasar = kataku.substring(akhir1, len).toLowerCase();
                    if (cekKata(dasar)) {

                        kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                        status = true;return status;
                    }
                }

                else if (cekKataku.equals("bel")) {
                    pot=3;prefix = "bel";
                    dasar = kataku.substring(akhir1, len).toLowerCase();
                    if (dasar.equals("ajar")) {

                        kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                        status = true;return status;
                    }
                }
                else if (cekKataku2.equals("be")) {
                    pot=2;prefix = "be";
                    dasar = kataku.substring(akhir2, len).toLowerCase();
                    if (cekKata(dasar)) {

                        kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                        status = true;return status;
                    }
                }
                else if (cekKataku.equals("per")) {
                    pot=3;prefix = "per";
                    dasar = kataku.substring(akhir1, len).toLowerCase();
                    if (cekKata(dasar)) {

                        kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                        status = true;return status;
                    }
                }
                else if (cekKataku.equals("pel")) {
                    pot=3;prefix = "pel";

                    dasar = kataku.substring(akhir1, len).toLowerCase();
                    if (dasar.equals("ajar")) {

                        kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                        status = true;return status;
                    }
                }
                else if (cekKataku2.equals("pe")) {
                    pot=2;prefix = "pe";
                    dasar = kataku.substring(akhir2, len).toLowerCase();
                    if (cekKata(dasar)) {

                        kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                        status = true;return status;
                    }
                }
                if (cekAkhiran3.equals("kan")) {

                    dasar = kataku.substring(pot, len-3).toLowerCase();
                    if(cekKata(dasar)) {
                        if (!prefix.equals("ke") || !prefix.equals("peng")) {

                            kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                            status = true;return status;
                        }
                    }
                    else
                    {
                        dasar = kataku.substring(awal, len-1).toLowerCase();
                        if(cekKata(dasar)) {
                            if (!prefix.equals("ke") || !prefix.equals("peng")) {

                                kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                                status = true;return status;
                            }
                        }
                    }

                }
                if (cekAkhiran2.equals("an")) {

                    dasar = kataku.substring(pot, len-2).toLowerCase();
                    if(cekKata(dasar)) {
                        if (!prefix.equals("meng") || !prefix.equals("di") || !prefix.equals("ter")) {

                            kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                            status = true;return status;
                        }
                    }
                    else
                    {
                        dasar = kataku.substring(awal, len-2).toLowerCase();
                        if(cekKata(dasar)) {

                            kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                            if (!prefix.equals("meng") || !prefix.equals("di") || !prefix.equals("ter")) {
                                status = true;return status;
                            }
                        }

                    }
                }
                if (cekAkhiran1.equals("i")) {

                    dasar = kataku.substring(pot, len-1).toLowerCase();
                    if(cekKata(dasar)) {

                        if (!prefix.equals("ber") || !prefix.equals("ke") || !prefix.equals("peng")) {

                            kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);
                            status = true;return status;
                        }
                    }
                    else
                    {
                        dasar = kataku.substring(awal, len - 1).toLowerCase();
                        if(cekKata(dasar)) {
                            if (!prefix.equals("ber") || !prefix.equals("ke") || !prefix.equals("peng")) {

                                kata.updateJumData(db,kata.getJumKata(db,dasar)+1,dasar);    status = true;return status;
                            }
                        }
                    }
                }

            }
        }
        //cek akhiran particles
        return status;
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

        while(ketemu){
            int pos=getPositionFromSeparator(firstChar);

            if(pos == -1){
                ketemu = false;
            }
            else{
                lastChar = pos;
                if(!cekKataPos(firstChar, lastChar)){
                    //cek stemming
                    String kata=getKataSub(firstChar, lastChar);
                    //kalau ada didatabase return true, kalo tidak return false
                    if (!stemming(kata))
                    {
                        myText.setSpan(new UnderlineSpan(), firstChar,lastChar, 0);

                    }
                    //myText.setSpan(new UnderlineSpan(), firstChar,lastChar, 0);
                }
                firstChar = lastChar+1;
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