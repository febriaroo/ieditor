package com.febriaroosita.swt;

/**
 * Created by febria on 11/4/15.
 */
public class RemovalInterface {

    protected VisitorInterface visitor;

    protected String subject;

    protected String result;

    protected String removedPart;

    protected String affixType;

    public RemovalInterface(VisitorInterface $visitor, String subject, String result, String removedPart, String affixType) {
        this.visitor = visitor;
        this.subject = subject;
        this.result  = result;
        this.removedPart = removedPart;
        this.affixType = affixType;
    }

    public VisitorInterface getVisitor()
    {
        return this.visitor;
    }

    public String getSubject()
    {
        return this.subject;
    }

    public String getResult()
    {
        return this.result;
    }

    public String getRemovedPart()
    {
        return this.removedPart;
    }

    public String getAffixType()
    {
        return this.affixType;
    }
}
