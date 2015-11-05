package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by febria on 11/4/15.
 */
public class DisambiguatorPrefixRule19 implements DisambiguatorInterface
{
    /**
     * Disambiguate Prefix Rule 19
     * Original Rule 19 : mempV -> mem-pV where V != 'e'
     * Modified Rule 19 by ECS : mempA -> mem-pA where A != 'e' in order to stem memproteksi
     */
    public String disambiguate(String word){
        String match = "";

        // Pattern to find code
        String pattern = "^memp([abcdfghijklmopqrstuvwxyz])(.*)$";  // Sequence of 8 digits'

        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            match="p"+m.group(1)+m.group(2);
        }
        return match;
    }
}
