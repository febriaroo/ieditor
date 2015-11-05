package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule29 implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule 29
     * Original Rule 29 : peng{g|h|q} -> peng-{g|h|q}
     * Modified Rule 29 by ECS : pengC -> peng-C
     */
    public String disambiguate(String word){
        String match = "";

        // Pattern to find code
        String pattern = "^peng([bcdfghjklmnpqrstvwxyz])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match=m.group(1)+m.group(2);
        }
        return match;
    }
}
