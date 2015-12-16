package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule18a implements DisambiguatorInterface
{
    /**
     *  18a : menyV -> me-nyV to stem menyala -> nyala
     */
    public String disambiguate(String word){
        String match = "";

        String pattern = "^meny([aiueo])(.*)$";
        Pattern regEx = Pattern.compile(pattern);

        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match="ny"+m.group(1)+m.group(2);
        }
        return match;
    }
}
