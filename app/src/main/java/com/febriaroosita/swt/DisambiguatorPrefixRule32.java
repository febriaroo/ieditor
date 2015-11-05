package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule32  implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule 32
     * Rule 32 : pelV -> pe-lV except pelajar -> ajar
     */
    public String disambiguate(String word)
    {
        String match = "";
        if(word.equals("pelajar"))
        {
            match="ajar";
        }
        else {
            // Pattern to find code
            String pattern = "^pe(l[aiueo])(.*)$";  // Sequence of 8 digits'

            Pattern regEx = Pattern.compile(pattern);
            Matcher m = regEx.matcher(word);
            if (m.find()) {
                match = m.group(1) + m.group(2);
            }
        }
        return match;
    }
}
