package com.febriaroosita.swt;

/**
 * Created by febria on 11/4/15.
 */
public class RemoveInflectionalParticle implements VisitorInterface
{
    public void visit(CContext context)
    {
        String result = this.remove(context.currentWord.toLowerCase());

        if (result != context.currentWord && !result.equals("")) {
            String removedPart = context.currentWord.replaceAll(result, "");
            if (!removedPart.equals("")) {
                removalClass removal = new removalClass(
                        this,
                        context.currentWord,
                        result,
                        removedPart,
                        "P"
                );

                context.addRemoval(removal);
                context.currentWord = result;
            }
        }
    }
    public String remove(String word)
    {
        return word.replaceAll("-*(lah|kah|tah|pun)$", "");
    }
}

