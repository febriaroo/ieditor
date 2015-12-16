package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule38a implements DisambiguatorInterface
{
    /**
     *  38a : CelV -> CelV
     */
    public String disambiguate(String word)
    {
        String match = "";

        String pattern = "^([bcdfghjklmnpqrstvwxyz])(el[aiueo])(.*)$";
        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match=m.group(1)+m.group(2)+m.group(3);
        }
        return match;
    }
}
