package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule10 implements DisambiguatorInterface
{
    /**
     *  10 : me{l|r|w|y}V -> me-{l|r|w|y}V
     */
    public String disambiguate(String word)
    {String match = "";

        String pattern = "^me([lrwy])([aiueo])(.*)$";
        Pattern regEx = Pattern.compile(pattern);

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
