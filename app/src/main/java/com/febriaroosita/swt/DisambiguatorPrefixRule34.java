package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule34 implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule 34
     * Rule 34 : peCP -> pe-CP where C != {r|w|y|l|m|n} and P != 'er'
     */
    public String disambiguate(String word)
    {
        String match = "";

        // Pattern to find code
        String pattern = "^pe([bcdfghjklmnpqrstvwxyz])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(word);
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
}
