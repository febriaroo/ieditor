package com.febriaroosita.swt;

import java.util.ArrayList;

/**
 * Created by febria on 11/2/15.
 */
public class removalClass extends RemovalInterface {
    VisitorInterface visitor;
    String subject;
    String result;
    String removedPart;
    String affixType;

    public removalClass(VisitorInterface visitor, String subject, String result, String removedPart, String affixType) {
        super(visitor, subject, result, removedPart, affixType);
        this.visitor = visitor;
        this.subject = subject;
        this.result = result;
        this.removedPart = removedPart;
        this.affixType = affixType;
    }

}
