package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule17c implements DisambiguatorInterface
{
    /**
     *  17c : mengV -> mengV- where V = 'e'
     */
    public String disambiguate(String word){
        String match = "";

        String pattern = "^menge(.*)$";
        Pattern regEx = Pattern.compile(pattern);

        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match=m.group(1);
        }
        return match;
    }
}
