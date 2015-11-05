package com.febriaroosita.swt;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by febria on 11/4/15.
 */
public class AbstractDisambiguatePrefixRule implements VisitorInterface {
    protected ArrayList<DisambiguatorInterface> disambiguators = new ArrayList<DisambiguatorInterface>();

    public void visit(CContext context)
    {
        String result = null;
        int i=0;
        for(DisambiguatorInterface disambiguator : this.disambiguators) {
        result = disambiguator.disambiguate(context.currentWord);
        i++;
        if (context.cekKata(result)) {
            break;
        }
    }

        if (result == null) {
            return;
        }

        String removedPart = result.replaceAll(context.currentWord, "");
        if(!removedPart.equals("")) {
            removalClass removal = new removalClass(
                    this,
                    context.currentWord,
                    result,
                    removedPart,
                    "DP"
            );

            context.addRemoval(removal);
            context.currentWord = result;
        }
    }

    public void addDisambiguators(ArrayList<DisambiguatorInterface> disambiguators)
    {

        for(DisambiguatorInterface disambiguator : disambiguators) {
            this.addDisambiguator(disambiguator);
        }
    }

    public void addDisambiguator(DisambiguatorInterface disambiguator)
    {
        this.disambiguators.add(disambiguator);
    }

}
