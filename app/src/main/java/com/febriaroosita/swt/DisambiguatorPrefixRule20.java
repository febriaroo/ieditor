package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule20 implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule 20
     * Rule 20 : pe{w|y}V -> pe-{w|y}V
     */
    public String disambiguate(String word){
        String match = "";

        // Pattern to find code
        String pattern = "^pe([wy])([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match=m.group(1)+m.group(2)+m.group(3);
        }
        return match;

    }
}
