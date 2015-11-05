package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule30b implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule 30b
     * Rule 30b : pengV -> peng-kV
     */
    public String disambiguate(String word){
        String match = "";

        // Pattern to find code
        String pattern = "^peng([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match="k"+m.group(1)+m.group(2);
        }
        return match;
    }
}
