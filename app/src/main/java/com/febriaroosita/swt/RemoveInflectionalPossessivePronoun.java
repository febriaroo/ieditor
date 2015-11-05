package com.febriaroosita.swt;

/**
 * Created by febria on 11/4/15.
 */
public class RemoveInflectionalPossessivePronoun implements VisitorInterface
{
    public void visit(CContext context)
    {
        String result = this.remove(context.currentWord.toLowerCase());

        if (result != context.currentWord && !result.equals("")) {
            String removedPart = context.currentWord.replaceAll(result, "");
            if(!removedPart.equals("")) {
                removalClass removal = new removalClass(
                        this,
                        context.currentWord,
                        result,
                        removedPart,
                        "PP"
                );

                context.addRemoval(removal);
                context.currentWord = result;
            }
        }
    }

    /**
     * Remove inflectional possessive pronoun : ku|mu|nya|-ku|-mu|-nya
     *
     */
    public String remove(String word)
    {
        return word.replaceAll("-*(ku|mu|nya)$", "");
    }
}
