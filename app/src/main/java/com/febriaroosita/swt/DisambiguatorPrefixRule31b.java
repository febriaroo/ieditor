package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule31b  implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule 31b
     * Original Rule 31 : penyV -> peny-sV
     * Modified by CC, shifted to 31b
     */
    public String disambiguate(String word)
    {
        String match = "";

        // Pattern to find code
        String pattern = "^peny([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match="s"+m.group(1)+m.group(2);
        }
        return match;
    }
}

