package com.febriaroosita.swt;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Created by febria on 11/4/15.
 */
public class CContext {

    public String originalWord;
    public int jumRemovals = 0;
    DbKata kata;
    ArrayList<CKata> myKata;
    public SQLiteDatabase db;
    public SQLiteDatabase db1;
    public DbKata DBKata;

    public String currentWord;

    public Boolean processIsStopped = false;

    public ArrayList<RemovalInterface> removals;

    public VisitorProvider visitorProvider;

    public ArrayList<VisitorInterface> visitors;

    public ArrayList<VisitorInterface> suffixVisitors;

    public ArrayList<VisitorInterface> prefixVisitors;

    public CheckSisipan ceksisipan;

    public String result;
    public CContext(String originalWord, VisitorProvider vp, Context pass){
        kata = new DbKata(pass);

        db = kata.getWritableDatabase();
        this.originalWord = originalWord;
        this.currentWord = this.originalWord;
        this.visitorProvider = vp;

        visitors = new ArrayList<VisitorInterface>();
        suffixVisitors = new ArrayList<VisitorInterface>();
        prefixVisitors = new ArrayList<VisitorInterface>();
        removals = new ArrayList<RemovalInterface>();
        ceksisipan = new CheckSisipan();
        initVisitor();


    }


    public void initVisitor() {
        this.visitors = this.visitorProvider.getVisitors();
        this.suffixVisitors = this.visitorProvider.getSuffixVisitors();
        this.prefixVisitors = this.visitorProvider.getPrefixVisitors();
    }
    public void stopProcess() {
        this.processIsStopped = true;
    }
    public boolean processIsStopped() {
        return this.processIsStopped;
    }

    public void addRemoval(RemovalInterface removal)
    {
        this.removals.add(removal);
        this.jumRemovals++;
    }
    public ArrayList<RemovalInterface> getRemovals()
    {
        return this.removals;
    }
    public String getResult()
    {
        return this.result;
    }
    public boolean cekKata(String kataku){
        boolean kataAda = kata.getDataByKata(db, kataku);
        return kataAda;

    }
    public void execute()
    {
        // step 1 - 5
        this.startStemmingProcess();
        // step 6
        //cek ke dictionary
        if(cekKata(this.currentWord))
        {
            this.result = this.currentWord;
        }
        else {

            this.result = this.originalWord;
        }
        //kalo nemu resultnya adalah currentword,
        //kalo salah original word;


    }

    protected void startStemmingProcess()
    {
        // step 1
        //
        if(cekKata(this.currentWord))
        {
            this.result = this.currentWord;
        }
        this.acceptVisitors(this.visitors);
        if(cekKata(this.currentWord))
        {
            return;
        }

        CPrecedenceAdjustmentSpecification csPrecedenceAdjustmentSpecification = new CPrecedenceAdjustmentSpecification();
        /*
         * Confix Stripping
         * Try to remove prefix before suffix if the specification is met
         */
        if (csPrecedenceAdjustmentSpecification.isSatisfiedBy(this.originalWord)) {
            // step 4, 5
            this.removePrefixes();

            if(cekKata(this.currentWord))
            {
                return;
            }
            // step 2, 3
            this.removeSuffixes();

            for(int i=0;i<this.removals.size();i++) {
                String temp = removals.get(i).subject.replaceAll(removals.get(i).removedPart,"");
                if (temp.equals("men")) {
                    if (this.currentWord.charAt(0) == 't') {
                        if (cekKata(this.currentWord)) {
                            this.currentWord =this.originalWord;
                            return;
                        }
                    }
                }

            }
            if(cekKata(this.currentWord))
            {
               return;
            } else {
                // if the trial is failed, restore the original word
                // and continue to normal rule precedence (suffix first, prefix afterwards)
                this.currentWord= this.originalWord;
                this.removals.clear();
            }
        }
        // step 2, 3
        this.removeSuffixes();
        if(cekKata(this.currentWord))
        {
            return;
        }

        // step 4, 5
        this.removePrefixes();
        for(int i=0;i<this.removals.size();i++) {
            String temp = removals.get(i).subject.replaceAll(removals.get(i).removedPart,"");
            if (temp.equals("men")) {
                if (this.currentWord.charAt(0) == 't') {
                    if (cekKata(this.currentWord)) {
                        this.currentWord =this.originalWord;
                        return;
                    }
                }
            }

        }
        if(cekKata(this.currentWord))
        {
            return;
        }

       // this.cekSisipan();
        // ECS loop pengembalian akhiran
        this.loopPengembalianAkhiran();
    }



    protected void removePrefixes()
    {
        for (int i = 0; i < 3; i++) {
            this.acceptPrefixVisitors(this.prefixVisitors);
            if(cekKata(this.currentWord))
            {
                return;
            }
        }
    }
    protected void removeSuffixes()
    {
        this.acceptVisitors(this.suffixVisitors);
    }
    public void accept(VisitorInterface visitor)
    {
        visitor.visit(this);
    }
    protected String acceptVisitors(ArrayList<VisitorInterface> visitors)
    {
        for(VisitorInterface visitor : visitors) {
            this.accept(visitor);
            if(cekKata(this.currentWord))
            {
                return this.currentWord;
            }
            if(this.processIsStopped())
            {

                return this.currentWord;
            }

        }
        return null;
    }
    protected String acceptPrefixVisitors(ArrayList<VisitorInterface> visitors)
    {
        int i=0;
        int removalCount = this.removals.size();
        for(VisitorInterface visitor : visitors) {
            this.accept(visitor);

            if(cekKata(this.currentWord)) {
                return this.currentWord;
            }
            if (this.processIsStopped()) {
                return this.currentWord;
            }
            if (this.removals.size() > removalCount) {
                return null;
            }
            i++;
        }
        return null;
    }
    /**
     * ECS Loop Pengembalian Akhiran
     * @param removals
     */
    public ArrayList<RemovalInterface> array_reverse(ArrayList<RemovalInterface> removals) {
        for (int i = 0; i < removals.size() / 2; i++) {
            RemovalInterface temp = removals.get(i);
            removals.set(i,removals.get(removals.size() - i - 1));
            removals.set(removals.size() - i - 1, temp);
        }
        return removals;
    }
    public void loopPengembalianAkhiran()
    {
        // restore prefix to form [DP+[DP+[DP]]] + Root word
        restorePrefix();
        ArrayList<RemovalInterface> removals = this.removals;
        ArrayList<RemovalInterface> reversedRemovals = array_reverse(removals);
        ArrayList<RemovalInterface> tempRemovals = new ArrayList<RemovalInterface>();
        RemovalInterface temptemp =null;
        String tempCurrentWord = this.currentWord;
        try{
        for(RemovalInterface removal: reversedRemovals) {
            if (!this.isSuffixRemoval(removal)) {

            } else {
                if ("kan".equals(removal.removedPart)) {
                    this.currentWord = removal.result + 'k';
                    // step 4, 5
                    this.removePrefixes();
                    if (cekKata(this.currentWord)) {
                        return;
                    }
                    temptemp = removal;

                    this.currentWord = temptemp.result + "kan";
                    tempRemovals.add(temptemp);
                } else {
                    temptemp = removal;

                    this.currentWord = temptemp.subject;
                    tempRemovals.add(temptemp);
                }
                // step 4, 5
                this.removePrefixes();
                if (cekKata(this.currentWord)) {
                    return;
                }
                this.removals = removals;
                this.currentWord = tempCurrentWord;
            }

            }
        }
        catch(ConcurrentModificationException e)
        {
            Log.i("lagi lagi error", this.currentWord);
        }
    }
    /**
     * Check wether the removed part is a suffix
     *
     * @param  \Sastrawi\Stemmer\Context\RemovalInterface $removal
     * @return boolean
     */
    protected boolean isSuffixRemoval(RemovalInterface removal)
    {
        return removal.getAffixType() == "DS"
                || removal.getAffixType() == "PP"
                || removal.getAffixType() == "P";
    }
    /**
     * Restore prefix to proceed with ECS loop pengembalian akhiran
     *
     * @return void
     */
    public void restorePrefix()
    {
        int i= 0;
        for(RemovalInterface removal: this.removals) {
        if (removal.getAffixType() == "DP") {
            // return the word before precoding (the subject of first prefix removal)
            this.currentWord = removal.subject;
            break;
        }
        }
        ArrayList<RemovalInterface> tempRemove = new ArrayList<RemovalInterface>();
        for(RemovalInterface removal: this.removals) {
            if (removal.getAffixType() == "DP") {
                // return the word before precoding (the subject of first prefix removal)
                tempRemove.add(removal);
            }
            i++;

        }
        for(int j=0;j<tempRemove.size();j++) {

            removals.remove(tempRemove.get(j));
        }
    }

    public void cekSisipan()
    {
        this.ceksisipan.cekSisipan(this.currentWord);
    }


}
