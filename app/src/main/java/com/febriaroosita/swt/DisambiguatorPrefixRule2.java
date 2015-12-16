package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule2 implements DisambiguatorInterface
{
    public String disambiguate(String word)
    {
        String match = "";
        String pattern = "^ber([bcdfghjklmnpqrstvwxyz])([a-z])(.*)$";
        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(word);

        if (m.find()) {
            String pattern2 = "^er(.*)$";
            regEx = Pattern.compile(pattern2);
            Matcher m1 =regEx.matcher(m.group(3));
            if(!m1.find())
            {
                match=m.group(1)+m.group(2)+m.group(3);

            }
        }
        if(word.contains("berkerja")) {
            match = "";
        }
        return match;
    }
}
