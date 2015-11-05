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

        // Pattern to find code
        String pattern = "^ber([bcdfghjklmnpqrstvwxyz])([a-z])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
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
        return match;
    }
}
