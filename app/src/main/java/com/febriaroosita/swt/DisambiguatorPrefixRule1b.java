package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule1b implements DisambiguatorInterface
{
    /**
     *  1b : berV -> be-rV
     *
     */
    public String disambiguate(String word)
    {
        String match = "";

        String pattern = "^ber([aiueo].*)$";

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match = "r" + m.group(1);
        }
        return match;

    }
}
