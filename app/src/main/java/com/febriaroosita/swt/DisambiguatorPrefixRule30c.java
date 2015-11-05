package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule30c  implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule 30c
     * Rule 30a : peng-V -> pengV- where V = 'e'
     */
    public String disambiguate(String word){
        String match = "";

        // Pattern to find code
        String pattern = "^penge(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match=m.group(1);
        }
        return match;

    }
}
