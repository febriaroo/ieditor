package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule26a implements DisambiguatorInterface
{
    /**
     *  26a : pem{rV|V} -> pe-m{rV|V}
     */
    public String disambiguate(String word){
        String match = "";
        String pattern = "^pem([aiueo])(.*)$";

        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match="m"+m.group(1)+m.group(2);
        }
        return match;
    }
}
