package com.febriaroosita.swt;

/**
 * Created by febria on 11/4/15.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class DisambiguatorPrefixRule14 implements DisambiguatorInterface
{

    public String disambiguate(String word)
    {
        String match = "";

        String pattern = "^men([cdjstz])(.*)$";
        Pattern regEx = Pattern.compile(pattern);

        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match=m.group(1)+m.group(2);
        }
        return match;
    }
}
