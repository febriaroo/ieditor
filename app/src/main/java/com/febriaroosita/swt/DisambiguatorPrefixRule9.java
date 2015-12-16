package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule9 implements DisambiguatorInterface
{
    /**
     *  9 : te-C1erC2 -> te-C1erC2 where C1 != 'r'
     */
    public String disambiguate(String word)
    {
        String match = "";

        String pattern = "^te([bcdfghjklmnpqrstvwxyz])er([bcdfghjklmnpqrstvwxyz])(.*)$";
        Pattern regEx = Pattern.compile(pattern);
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            if(m.group(1)!="r")
            {
                match=m.group(1)+"er"+m.group(2)+m.group(3);
            }
        }
        return match;
    }
}
