package com.febriaroosita.swt;

import java.util.ArrayList;

/**
 * Created by febria on 11/4/15.
 */
public class VisitorProvider {
    protected ArrayList<VisitorInterface> visitors;

    protected ArrayList<VisitorInterface> suffixVisitors;

    protected ArrayList<VisitorInterface> prefixVisitors;

    public VisitorProvider()
    {
        this.initVisitors();
    }

    protected void initVisitors()
    {
        visitors = new ArrayList<VisitorInterface>();
        suffixVisitors = new ArrayList<VisitorInterface>();
        prefixVisitors = new ArrayList<VisitorInterface>();

        this.visitors.add(new StemShortWord());
        ArrayList<VisitorInterface> temp;

        this.suffixVisitors.add(new RemoveInflectionalParticle()); // {lah|kah|tah|pun}
        this.suffixVisitors.add(new RemoveInflectionalPossessivePronoun()); // {ku|mu|nya}
        this.suffixVisitors.add(new RemoveDerivationalSuffix()); // {i|kan|an}

        this.prefixVisitors.add(new RemovePlainPrefix()); // {di|ke|se}
        ArrayList<DisambiguatorInterface> disambiguator = new ArrayList<DisambiguatorInterface>();

        disambiguator.add(new DisambiguatorPrefixRule1a());
        disambiguator.add(new DisambiguatorPrefixRule1b());
        this.prefixVisitors.add(new PrefixDisambiguator(
                disambiguator
        ));
        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule2());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));
        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule3());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));
        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule4());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));
        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule5());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));
        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule6a());
        disambiguator.add(new DisambiguatorPrefixRule6b());
        this.prefixVisitors.add(new PrefixDisambiguator(
                disambiguator
        ));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule7());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule8());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule9());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule10());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule11());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule12());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule13a());
        disambiguator.add(new DisambiguatorPrefixRule13b());
        this.prefixVisitors.add(new PrefixDisambiguator(
                disambiguator
        ));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule14());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule15a());
        disambiguator.add(new DisambiguatorPrefixRule15b());
        this.prefixVisitors.add(new PrefixDisambiguator(
                disambiguator
        ));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule16());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule17a());
        disambiguator.add(new DisambiguatorPrefixRule17b());
        disambiguator.add(new DisambiguatorPrefixRule17c());
        disambiguator.add(new DisambiguatorPrefixRule17d());
        this.prefixVisitors.add(new PrefixDisambiguator(
                disambiguator
        ));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule18a());
        disambiguator.add(new DisambiguatorPrefixRule18b());
        this.prefixVisitors.add(new PrefixDisambiguator(
                disambiguator
        ));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule19());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule20());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule21a());
        disambiguator.add(new DisambiguatorPrefixRule21b());
        this.prefixVisitors.add(new PrefixDisambiguator(
                disambiguator
        ));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule23());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule24());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule25());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule26a());
        disambiguator.add(new DisambiguatorPrefixRule26b());
        this.prefixVisitors.add(new PrefixDisambiguator(
                disambiguator
        ));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule27());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule28a());
        disambiguator.add(new DisambiguatorPrefixRule28b());
        this.prefixVisitors.add(new PrefixDisambiguator(
                disambiguator
        ));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule29());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule30a());
        disambiguator.add(new DisambiguatorPrefixRule30b());
        disambiguator.add(new DisambiguatorPrefixRule30c());
        this.prefixVisitors.add(new PrefixDisambiguator(
                disambiguator
        ));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule31a());
        disambiguator.add(new DisambiguatorPrefixRule31b());
        this.prefixVisitors.add(new PrefixDisambiguator(
                disambiguator
        ));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule32());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule34());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule35());
        // CS additional rules
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule36());
        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));


        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule37a());
        disambiguator.add(new DisambiguatorPrefixRule37b());
        // CC infix rules
        this.prefixVisitors.add(new PrefixDisambiguator(
                disambiguator
        ));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule38a());
        disambiguator.add(new DisambiguatorPrefixRule39b());
        this.prefixVisitors.add(new PrefixDisambiguator(
                disambiguator
        ));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule40a());
        disambiguator.add(new DisambiguatorPrefixRule40b());
        this.prefixVisitors.add(new PrefixDisambiguator(
                disambiguator
        ));

        // Sastrawi rules
        // ku-A, kau-A
        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule41());
        this.prefixVisitors.add(new PrefixDisambiguator(
                disambiguator
        ));

        disambiguator.clear();
        disambiguator.add(new DisambiguatorPrefixRule42());

        this.prefixVisitors.add(new PrefixDisambiguator(disambiguator));

    }


    public  ArrayList<VisitorInterface>  getVisitors()
    {
        return this.visitors;
    }

    public  ArrayList<VisitorInterface>  getSuffixVisitors()
    {
        return this.suffixVisitors;
    }

    public ArrayList<VisitorInterface> getPrefixVisitors()
    {
        return this.prefixVisitors;
    }

}
