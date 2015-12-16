package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule27 implements DisambiguatorInterface
{
    /**
     *  27 : pen{c|d|j|z} -> pen-{c|d|j|z}
     */
    public String disambiguate(String word){
        String match = "";

        String pattern = "^pen([cdjz])(.*)$";
        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match=m.group(1)+m.group(2);
        }
        return match;
    }
}
