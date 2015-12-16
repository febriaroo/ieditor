package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule5 implements DisambiguatorInterface
{
    /**
     * 5 : beC1erC2 -> be-C1erC2 where C1 != 'r'
     */
    public String disambiguate(String word)
    {
        String match = "";
        String pattern = "^be([bcdfghjklmnpqstvwxyz])(er[bcdfghjklmnpqrstvwxyz])(.*)$";
        Pattern regEx = Pattern.compile(pattern);

        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match=m.group(1)+m.group(2)+m.group(3);
        }

        return match;

    }
}
