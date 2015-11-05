package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule18b implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule 18b
     * Original Rule 18 : menyV -> meny-sV
     * Modified by CC (shifted into 18b, see also 18a)
     */
    public String disambiguate(String word){
        String match = "";

        // Pattern to find code
        String pattern = "^meny([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match="s"+m.group(1)+m.group(2);
        }
        return match;

    }
}
