package com.febriaroosita.swt;

/**
 * Created by febria on 11/4/15.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class DisambiguatorPrefixRule14 implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule no 14
     *
     * Rule 14 modified by Andy Librian : men{c|d|j|s|t|z} -> men-{c|d|j|s|t|z}
     * in order to stem mentaati
     *
     * Rule 14 modified by ECS: men{c|d|j|s|z} -> men-{c|d|j|s|z}
     * in order to stem mensyaratkan, mensyukuri
     *
     * Original CS Rule no 14 was : men{c|d|j|z} -> men-{c|d|j|z}
     */
    public String disambiguate(String word)
    {
        String match = "";

        // Pattern to find code
        String pattern = "^men([cdjstz])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match=m.group(1)+m.group(2);
        }
        return match;
    }
}
