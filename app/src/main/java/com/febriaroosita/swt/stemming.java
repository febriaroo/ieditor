package com.febriaroosita.swt;

import android.content.Context;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by febria on 11/4/15.
 */
public class stemming {

    Context myActivity;
    CContext myCContext;

    protected VisitorProvider visitorProvider;

    public stemming(Context myAct)
    {
        this.myActivity = myAct;
        this.visitorProvider = new VisitorProvider();
        myCContext = new CContext("",this.visitorProvider,myActivity);
    }

    public String stem(String word)
    {
        if(word.equals("amarah"))
        {
             return "marah";
        }
        String stems = stemWord(word);

        return stems;
    }

    protected String stemWord(String word)
    {
        if (this.isPlural(word)) {
            return this.stemPluralWord(word);
        } else {
            return this.stemSingularWord(word);
        }
    }

    protected boolean isPlural(String word)
    {
        // -ku|-mu|-nya
        // nikmat-Ku, etc

        String match = "";

        // Pattern to find code
        String pattern = "^(.*)-(ku|mu|nya|lah|kah|tah|pun)";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match = m.group(1);
            if(match.equals("-"))
            {
                return true;
            }

        }
        try
        {
            int a = (word.indexOf("-"));
            return false;
        } catch (NumberFormatException e)
        {
            return true;
        }
    }

    protected String stemPluralWord(String plural)
    {
        String match = "";

        // Pattern to find code
        String pattern = "^/^(.*)-(.*)/";  // Sequence of 8 digits'
        ArrayList<String> check = null;
        check.add("ku");
        check.add("mu");
        check.add("nya");
        check.add("lah");
        check.add("kah");
        check.add("tah");
        check.add("pun");

        Pattern regEx = Pattern.compile(pattern);
        String temp = "";
        // Find instance of pattern matches
        Matcher m = regEx.matcher(plural);
        if (m.find()) {
            if(m.group(1).isEmpty() || m.group(2).isEmpty())
            {
                return plural;
            }

            String suffix = m.group(2);

            m = regEx.matcher(m.group(1));
            if(check.contains(suffix) && m.find())
            {
                temp = m.group(2) + "-" + suffix;
            }
            String rootWord1 = stemSingularWord(m.group(1));
            String rootWord2 = stemSingularWord(m.group(2));

            if(!myCContext.cekKata(m.group(2)) && rootWord2.equals(m.group(2)))
            {
                rootWord2 = this.stemSingularWord("me" + m.group(2));
            }
            if(rootWord1 == rootWord2)
                return rootWord1;


        }

        return plural;
    }

    protected String stemSingularWord(String word)
    {
        CContext mulai = new CContext(word, this.visitorProvider, myActivity);
        mulai.execute();

        return mulai.getResult();
    }
}
