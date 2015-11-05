package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule12 implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule 12
     * Nazief and Adriani Original Rule 12 : mempe{r|l} -> mem-pe{r|l}
     * Modified by Jelita Asian's CS Rule 12 : mempe -> mem-pe to stem mempengaruhi
     */
    public String disambiguate(String word)
    {
        String match = "";

        // Pattern to find code
        String pattern = "^mempe(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match="pe"+m.group(1);
        }
        return match;

    }
}
