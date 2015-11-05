package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule15b implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule 15b
     * Rule 15 : men{V} -> me-t{V}
     */
    public String disambiguate(String word){
        String match = "";

        // Pattern to find code
        String pattern = "^men([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match="t"+m.group(1)+m.group(2);
        }
        return match;
    }
}
