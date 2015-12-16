package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule24 implements DisambiguatorInterface
{
    /**
     * Rule 24 : perCAerV -> per-CAerV where C != 'r'
     */
    public String disambiguate(String word){
        String match = "";

        String pattern = "^per([bcdfghjklmnpqrstvwxyz])([a-z])er([aiueo])(.*)$"; 
        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(word);
        if (m.find()) {

            if(m.group(1)!="r")
            {
                match=m.group(1)+m.group(2)+"er"+m.group(3)+m.group(4);
            }
        }
        return match;

    }
}
