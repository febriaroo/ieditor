package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule37b implements DisambiguatorInterface
{
    /**
     * Rule 37b : CerV -> CV
     */
    public String disambiguate(String word)
    {

        String match = "";

        // Pattern to find code
        String pattern = "^([bcdfghjklmnpqrstvwxyz])er([aiueo])(.*)$";
        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match=m.group(1)+m.group(2)+m.group(3);
        }
        return match;
    }
}
