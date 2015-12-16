package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule23 implements DisambiguatorInterface
{
    /**
     *  23 : perCAP -> per-CAP, C != 'r' AND P != 'er'
     */
    public String disambiguate(String word){
        String match = "";

        String pattern = "^per([bcdfghjklmnpqrstvwxyz])([a-z])(.*)$";
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
