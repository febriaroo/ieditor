package com.febriaroosita.swt;

import java.util.ArrayList;

/**
 * Created by febria on 11/4/15.
 */
public class PrefixDisambiguator extends AbstractDisambiguatePrefixRule implements VisitorInterface
{
    PrefixDisambiguator(ArrayList<DisambiguatorInterface> disambiguator){
        this.addDisambiguators(disambiguator);
    }
}
