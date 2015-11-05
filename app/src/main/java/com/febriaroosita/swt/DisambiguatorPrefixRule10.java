package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule10 implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule 10
     * Rule 10 : me{l|r|w|y}V -> me-{l|r|w|y}V
     */
    public String disambiguate(String word)
    {String match = "";

        // Pattern to find code
        String pattern = "^me([lrwy])([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            if(m.group(1)!="r")
            {
                match=m.group(1)+m.group(2)+m.group(3);
            }
        }
        return match;

    }
}
