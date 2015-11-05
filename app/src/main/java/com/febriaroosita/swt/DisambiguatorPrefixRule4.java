package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule4 implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule 4
     * Rule 4 : belajar -> bel-ajar
     */
    public String disambiguate(String word)
    {
        String match = "";

        if (word.equals("belajar")) {
            return "ajar";
        }
        return match;
    }

}
