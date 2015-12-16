package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule42 implements DisambiguatorInterface
{
    /**
     * Rule 42 : kauA -> kau-A
     */
    public String disambiguate(String word)
    {
        String match = "";

        String pattern = "^kau(.*)$";
        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match=m.group(1);
        }
        return match;
    }
}
