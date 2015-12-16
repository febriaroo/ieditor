package com.febriaroosita.swt;

/**
 * Created by febria on 11/4/15.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class  DisambiguatorPrefixRule13a implements DisambiguatorInterface
{
    /**
     *  13a : mem{rV|V} -> me-m{rV|V}
     */
    public String disambiguate(String word)
    {
        String match = "";
        String pattern = "mem([aiueo])(.*)$";
        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match="m"+m.group(1)+m.group(2);
        }
        return match;

    }
}
