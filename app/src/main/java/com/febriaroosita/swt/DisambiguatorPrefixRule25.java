package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule25 implements DisambiguatorInterface
{
    /**
     *  25 : pem{b|f|v} -> pem-{b|f|v}
     */
    public String disambiguate(String word){
        String match = "";

        String pattern = "^pem([bfv])(.*)$";
        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match=m.group(1)+m.group(2);

        }
        return match;

    }
}
