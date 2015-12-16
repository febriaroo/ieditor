package com.febriaroosita.swt;

/**
 * Created by febria on 11/4/15.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class DisambiguatorPrefixRule13b implements DisambiguatorInterface
{
    /**
     *  13b : mem{rV|V} -> me-p{rV|V}
     */
    public String disambiguate(String word)
    {

        String match = "";
        String pattern = "^mem([aiueo])(.*)$";
        Pattern regEx = Pattern.compile(pattern);

        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match="p"+m.group(1)+m.group(2);
        }
        return match;

    }
}
