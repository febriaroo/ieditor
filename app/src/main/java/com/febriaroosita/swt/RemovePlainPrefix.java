package com.febriaroosita.swt;

/**
 * Created by febria on 11/4/15.
 */
public class RemovePlainPrefix  implements VisitorInterface {
    public void visit(CContext context) {
        String result = this.remove(context.currentWord.toLowerCase());

        if (result != context.currentWord && !result.equals("")) {
            String removedPart = context.currentWord.replaceAll(result, "");
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
    }

    /**
     * Remove plain prefix : di|ke|se
     *
     */
    public String remove(String word) {
        return word.replaceAll("^(di|ke|se)", "");
    }
}

