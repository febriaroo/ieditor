package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by febria on 11/4/15.
 */
public class InvalidAffixPairSpecification implements SpecificationInterface
{
    public boolean isSatisfiedBy(String word)
    {
        String myPatt ="^me(.*)kan$";
        String invalidAffixes[] = {"^ber(.*)i$",
                "^di(.*)an$",
                "^ke(.*)i$",
                "^ke(.*)an$",
                "^me(.*)an$",
                "^me(.*)an$",
                "^ter(.*)an$",
                "^per(.*)an$"};

        boolean statusCheck = true;
        int i=0;
        int count=0;
        boolean contains = false;
        String match = "";

        // Pattern to find code

        Pattern regEx = Pattern.compile(myPatt);
        Matcher m = regEx.matcher(word);
        if (m.find()) {
            return false;
        }
        else if(word.equals("ketahui"))
        {
            return false;
        }

        else {
            for(String invalidAffix: invalidAffixes) {
                regEx = Pattern.compile(invalidAffix);
                m = regEx.matcher(word);
                if (m.find()) {
                    contains = contains || m.find();
                }
                //contains = contains || preg_match($invalidAffix, $word) === 1;
            }
            return contains;
        }


    }
}
