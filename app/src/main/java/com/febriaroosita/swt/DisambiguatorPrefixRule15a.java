package com.febriaroosita.swt;

/**
 * Created by febria on 11/4/15.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class DisambiguatorPrefixRule15a implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule 15a
     * Rule 15a : men{V} -> me-n{V}
     */
    public String disambiguate(String word)
    {
        String match = "";

        // Pattern to find code
        String pattern = "^men([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match="n"+m.group(1)+m.group(2);
        }
        return match;
    }
}
