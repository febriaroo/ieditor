package com.febriaroosita.swt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by febria on 11/4/15.
 */
public class CPrecedenceAdjustmentSpecification {

    /*public function isSatisfiedBy($value)
    {
        $regexRules = array(
            '/^be(.*)lah$/',
            '/^be(.*)an$/',
            '/^me(.*)i$/',
            '/^di(.*)i$/',
            '/^pe(.*)i$/',
            '/^ter(.*)i$/',
        );

        foreach ($regexRules as $rule) {
            if (preg_match($rule, $value)) {
                return true;
            }
        }

        return false;
    }
*/
    public boolean isSatisfiedBy(String kata)
    {
        String pattern2[] = {"^be(.*)lah$",
                "^be(.*)an$",
                "^me(.*)i$",
                "^di(.*)i$",
                "^pe(.*)i$",
                "^ter(.*)i$"};
        String pattern1 = "^me(.*)kan$";
        String patterns[] = {"^be(.*)lah$",
                "^be(.*)an$",
                "^me(.*)i$",
                "^di(.*)i$",
                "^pe(.*)i$",
                "^ter(.*)i$"};

//        String pattern[] = {"^ber(.*)i$",
//                "^di(.*)an$",
//                "^ke(.*)i$",
//                "^ke(.*)kan$",
//                "^me(.*)an$",
//                "^me(.*)an$",
//                "^ter(.*)an$",
//                "^per(.*)an$",};
        boolean statusCheck = true;
        int i=0;
        int count=0;

        String match = "";
        for(String pattern:patterns)
        {
            match = "";

            Pattern regEx = Pattern.compile(pattern);
            Matcher m = regEx.matcher(kata);
            if (m.find()) {
                return true;

            }
        }
        return false;

    }
}
