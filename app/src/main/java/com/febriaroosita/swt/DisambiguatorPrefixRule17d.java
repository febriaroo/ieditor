package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule17d implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule 17d
     * Rule 17d : mengV -> me-ngV
     */
    public String disambiguate(String word){
        String match = "";

        // Pattern to find code
        String pattern = "^meng([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match="ng"+m.group(1)+m.group(2);
        }
        return match;

    }
}
