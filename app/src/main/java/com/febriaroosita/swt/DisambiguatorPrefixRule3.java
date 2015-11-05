package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule3  implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule 3
     * Rule 3 : berCAerV -> ber-CAerV where C != 'r'
     * return string|null
     */
    public String disambiguate(String word)
    {
        String match = "";

        // Pattern to find code
        String pattern = "^ber([bcdfghjklmnpqrstvwxyz])([a-z])er([aiueo])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
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
