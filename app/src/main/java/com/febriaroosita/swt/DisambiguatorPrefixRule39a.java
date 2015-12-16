package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule39a implements DisambiguatorInterface
{
    /**
     * Rule 39a : CemV -> CemV
     */
    public String disambiguate(String word)
    {
        String match = "";

        String pattern = "^([bcdfghjklmnpqrstvwxyz])(em[aiueo])(.*)$";
        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match=m.group(1)+m.group(2)+m.group(3);
        }
        return match;
    }
}
