package com.febriaroosita.swt;

/**
 * Created by febria on 11/4/15.
 */
public class RemoveDerivationalSuffix implements VisitorInterface {
    public void visit(CContext context)
    {
        String result = this.removeSuffix(context.currentWord.toLowerCase());

        if (result != context.currentWord && !result.equals("")) {
            String removedPart = context.currentWord.replaceAll(result, "");
            if(!removedPart.equals("")) {
                removalClass removal = new removalClass(
                        this,
                        context.currentWord,
                        result,
                        removedPart,
                        "DS"
                );


                context.addRemoval(removal);
                context.currentWord = result;
            }
        }
    }

    /**
     * Remove derivational suffix
     * Added the adopted foreign suffix rule : is|isme|isasi
     */
    public String removeSuffix(String word)
    {
        return word.replaceAll("(is|isme|isasi|i|kan|an)$", "");
    }
}
