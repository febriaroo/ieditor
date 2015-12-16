package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule15b implements DisambiguatorInterface
{
    /**
     *  15 : men{V} -> me-t{V}
     */
    public String disambiguate(String word){
        String match = "";

        String pattern = "^men([aiueo])(.*)$";
        Pattern regEx = Pattern.compile(pattern);

        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match="t"+m.group(1)+m.group(2);
        }
        return match;
    }
}
