package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule16 implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule 16
     * Original Nazief and Adriani's Rule 16 : meng{g|h|q} -> meng-{g|h|q}
     * Modified Jelita Asian's CS Rule 16 : meng{g|h|q|k} -> meng-{g|h|q|k} to stem mengkritik
     */
    public String disambiguate(String word){
        String match = "";

        // Pattern to find code
        String pattern = "^meng([g|h|q|k])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match=m.group(1)+m.group(2);
        }
        return match;
    }
}
