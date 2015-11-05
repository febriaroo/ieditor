package com.febriaroosita.swt;

/**
 * Created by febria on 11/4/15.
 */
public class StemShortWord implements VisitorInterface {
    public void visit(CContext context)
    {
        if (this.isShortWord(context.currentWord)) {
            context.stopProcess();
        }
    }

    protected boolean isShortWord(String word)
    {
        return (word.length() <= 3);
    }


}
