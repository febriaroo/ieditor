package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule17a implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule 17a
     * Rule 17a : mengV -> meng-V
     */
    public String disambiguate(String word){
        String match = "";

        // Pattern to find code
        String pattern = "^meng([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match=m.group(1)+m.group(2);
        }
        return match;

    }
}
