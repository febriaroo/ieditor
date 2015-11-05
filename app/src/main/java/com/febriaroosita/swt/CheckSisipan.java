package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by febria on 11/5/15.
 */
public class CheckSisipan {
    /*
    *  $kataAsal = $kata;
           If (preg_match(‘/^( g | j | I | s) (el)\S{1,}/’,$kata,$match)){
                    $_kata=preg_replace(‘/el/’,”,$match[0]);
                             {
                             Return $_kata;
                     }
            }
           Return $kataAsal;
    * */
    public String cekSisipan(String word)
    {
        String match = "";

        // Pattern to find code
        String pattern = "^(g|j|i|s)(el)\\S{1,}";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match= m.group(0).replaceAll("el","");
            return match;
        }
        pattern = "^(c|j|k|g)(em)\\S{1,}";  // Sequence of 8 digits'
        regEx = Pattern.compile(pattern);
        m = regEx.matcher(word);
        if(m.find())
        {
            match = m.group(0).replaceAll("em","");
            return match;
        }

        pattern = "^(s|g|k)(er)\\S{1,}";  // Sequence of 8 digits'
        regEx = Pattern.compile(pattern);
        m = regEx.matcher(word);
        if(m.find())
        {
            match = m.group(0).replaceAll("er","");
            return match;
        }

        pattern = "^(k|s|t)(in)\\S{1,}";  // Sequence of 8 digits'
        regEx = Pattern.compile(pattern);
        m = regEx.matcher(word);
        if(m.find())
        {
            match = m.group(0).replaceAll("in","");
            return match;
        }

        return match;

    }
}
