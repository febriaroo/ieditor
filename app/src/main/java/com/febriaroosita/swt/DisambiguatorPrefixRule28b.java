package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule28b  implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule 28b
     * Rule 28b : pen{V} -> pe-t{V}
     */
    public String disambiguate(String word){
        String match = "";

        // Pattern to find code
        String pattern = "^pen([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match="t"+m.group(1)+m.group(2);
        }
        return match;
    }
}
