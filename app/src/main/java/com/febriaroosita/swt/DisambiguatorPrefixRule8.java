package com.febriaroosita.swt;

/**
 * Created by febria on 11/4/15.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class DisambiguatorPrefixRule8 implements DisambiguatorInterface
{
    /**
     * 8 : terCP -> ter-CP where C != 'r' and P != 'er'
     */
    public String disambiguate(String word)
    {
        String match = "";

        String pattern = "^ter([bcdfghjklmnpqrstvwxyz])([a-z])(.*)$";
        Pattern regEx = Pattern.compile(pattern);

        Matcher m = regEx.matcher(word);
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
}
