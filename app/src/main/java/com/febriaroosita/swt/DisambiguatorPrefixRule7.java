package com.febriaroosita.swt;

/**
 * Created by febria on 11/4/15.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class DisambiguatorPrefixRule7 implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule 7
     * Rule 7 : terCerv -> ter-CerV where C != 'r'
     */
    public String disambiguate(String word)
    {
        String match = "";

        // Pattern to find code
        String pattern = "^ter([bcdfghjklmnpqrstvwxyz])er([aiueo].*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            if(m.group(1)!="r")
            {
                match=m.group(1)+"er"+m.group(2);
            }
        }
        return match;
    }
}
